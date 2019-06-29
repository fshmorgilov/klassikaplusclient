package com.themaker.fshmo.klassikaplus.data.repositories;

import android.util.Log;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.data.domain.ItemCategory;
import com.themaker.fshmo.klassikaplus.data.mappers.*;
import com.themaker.fshmo.klassikaplus.data.persistence.AppDatabase;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbCategory;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;
import com.themaker.fshmo.klassikaplus.data.web.catalog.CatalogApi;
import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.items.DataDto;
import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.items.ItemDto;
import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.items.ResponseDto;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executor;

public class CatalogRepository extends BaseRepository {

    private static final String TAG = CatalogRepository.class.getName();

    @Inject
    AppDatabase db;
    @Inject
    CatalogApi api;
    @Inject
    Executor executor;

    private static CatalogRepository INSTANCE;

    public CatalogRepository() {
        INSTANCE = this;
        App.getInstance().getComponent().inject(this);
    }

    public static CatalogRepository getInstance() {
        synchronized (App.class) {
            if (INSTANCE == null)
                INSTANCE = new CatalogRepository();
            return INSTANCE;
        }
    }

    public Flowable<List<Item>> provideNoveltyData() {
        ListMapping<DbItem, Item> dbItemDomainMapper = new ListMapping<>(new DbToDomainItemMapper());
        ListMapping<ItemDto, DbItem> itemDtoDbItemMapper = new ListMapping<>(new DtoToDbItemMapper());
        return Flowable.concat(
                getNoveltyItemsFromApi()
                        .map(itemDtoDbItemMapper::map)
                        .map(dbItemDomainMapper::map),
                getItemsFromDbByNovelty())
                .debounce(400, TimeUnit.MILLISECONDS);
    }

    public Flowable<List<Item>> provideByCategoryData(Integer category) {
        ListMapping<DbItem, Item> dbItemDomainMapper = new ListMapping<>(new DbToDomainItemMapper());
        ListMapping<ItemDto, DbItem> itemDtoDbItemMapper = new ListMapping<>(new DtoToDbItemMapper());
//        return Flowable.concat(
//                getItemsByCategory(category)
//                        .map(itemDtoDbItemMapper::map)
//                        .map(dbItemDomainMapper::map),
//                getItemsFromDbByCategory(category))
//                .debounce(400, TimeUnit.MILLISECONDS);
        return getItemsByCategory(category)
                .map(itemDtoDbItemMapper::map)
                .map(dbItemDomainMapper::map);
    }

    public Flowable<List<ItemCategory>> provideCategories() {
        DtoToDbCategoryMapper dtoToDbCategoryMapper = new DtoToDbCategoryMapper();
        DbToDomainCategoryMapper dbToDomainCategoryMapper = new DbToDomainCategoryMapper();
        ListMapping<DbCategory, ItemCategory> dbToDomainCategoryListMapping = new ListMapping<>(dbToDomainCategoryMapper);
        //TODO set Default or from database
        return getCategoriesFromApi()
                .map(dtoToDbCategoryMapper::map)
                .map(dbToDomainCategoryListMapping::map);
    }

    private Flowable<List<ItemDto>> getNoveltyItemsFromApi() {
        Log.i(TAG, "getNoveltyItemsFromApi: Requested holder_item");
        return api.catalog()
                .getNovelty()
                .map(ResponseDto::getData)
                .map(DataDto::getItems)
                .doOnSuccess(this::storeItemsInDb)
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }

    private Flowable<List<ItemDto>> getItemsByCategory(Integer category) {
        Log.i(TAG, "getItemsByCategory: Requested items by category: " + category);
        return api.catalog()
                .getItemsByCategory(category)
                .map(ResponseDto::getData)
                .map(DataDto::getItems)
                .doOnSuccess(this::storeItemsInDb)
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }

    private Flowable<Map<Integer, String>> getCategoriesFromApi() {
        return api.catalog()
                .getCategories()
                .map(com.themaker.fshmo.klassikaplus.data.web.dto.catalog.categories.ResponseDto::getData)
                .map(com.themaker.fshmo.klassikaplus.data.web.dto.catalog.categories.DataDto::getItems)
                .doOnSuccess(this::storeCategoriesInDb)
                .toFlowable()
                .subscribeOn(Schedulers.io());
    }

    private void storeItemsInDb(List<ItemDto> items) {
        ListMapping<ItemDto, DbItem> itemDtoDbItemMapper = new ListMapping<>(new DtoToDbItemMapper());
        List<DbItem> dbItems = itemDtoDbItemMapper.map(items);
        db.itemDao().insertAll(dbItems);
        Log.i(TAG, "storeItemsInDb: items stored " + items.size());
    }

    private void storeCategoriesInDb(Map<Integer, String> categories) {
        DtoToDbCategoryMapper mapper = new DtoToDbCategoryMapper();
        db.categoryDao().insertAll(mapper.map(categories));
        Log.i(TAG, "storeCategoriesInDb: Number of categories stored: " + categories.keySet().size());
    }

    private Flowable<List<Item>> getItemsFromDbByNovelty() {
        ListMapping<DbItem, Item> dbItemDomainMapper = new ListMapping<>(new DbToDomainItemMapper());
        return db.itemDao().getByNovelty(true)
                .map(dbItemDomainMapper::map)
                .subscribeOn(Schedulers.io());
    }

    private Flowable<List<Item>> getItemsFromDbByCategory(Integer categoryId) {
        ListMapping<DbItem, Item> dbItemDomainMapper = new ListMapping<>(new DbToDomainItemMapper());
        return db.itemDao().getByCategory(categoryId)
                .map(dbItemDomainMapper::map)
                .subscribeOn(Schedulers.io());
    }

    public void makeItemFavorite(String itemId, boolean favorite) {
        Disposable disposable = db.itemDao().findById(itemId)
                .subscribeOn(Schedulers.io())
                .subscribe(dbItem -> {
                            dbItem.setFavorite(favorite);
                            db.itemDao().update(dbItem);
                            Log.i(TAG, "makeItemFavorite: item with id " + itemId + " marked as favorite");
                        },
                        throwable -> {
                            if (throwable instanceof Exception)
                                Log.e(TAG, "makeItemFavorite: " + ((Exception) throwable).getMessage());
                        }
                );
    }
}

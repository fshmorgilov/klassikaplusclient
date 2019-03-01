package com.themaker.fshmo.klassikaplus.data.repositories;

import android.util.Log;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.data.mappers.DbToDomainMapper;
import com.themaker.fshmo.klassikaplus.data.mappers.DtoToDbItemMapper;
import com.themaker.fshmo.klassikaplus.data.mappers.ListMapping;
import com.themaker.fshmo.klassikaplus.data.persistence.AppDatabase;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;
import com.themaker.fshmo.klassikaplus.data.web.catalog.CatalogApi;
import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.DataDto;
import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.ItemDto;
import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.ResponseDto;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Executor;

public class CatalogRepository extends BaseRepository {

    private static final String TAG = CatalogRepository.class.getName();

    @Inject
    AppDatabase db;
    @Inject
    CatalogApi api;
    @Inject
    Executor executor;

    private ListMapping<DbItem, Item> dbItemDomainMapper = new ListMapping<>(new DbToDomainMapper());
    private ListMapping<ItemDto, DbItem> itemDtoDbItemMapper = new ListMapping<>(new DtoToDbItemMapper());

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

    public Single<List<Item>> provideNoveltyData() {
        return getItemsFromApi()
                // TODO: 2/22/2019 Dto to domain mapper
                .map(itemDtoDbItemMapper::map)
                .map(dbItemDomainMapper::map);
    }

    private Single<List<ItemDto>> getItemsFromApi() {
        return api.catalog()
                .getNovelty()
                .map(ResponseDto::getData)
                .map(DataDto::getItems)
                .doOnSuccess(itemsFromApi -> {
                    Log.i(TAG, "getItemsFromApi: storing users in DB");
                    storeItemsInDb(itemsFromApi);
                })
                .subscribeOn(Schedulers.io());
    }

    private void storeItemsInDb(List<ItemDto> items) {
        List<DbItem> dbItems = itemDtoDbItemMapper.map(items);
        db.itemDao().insertAll(dbItems);
        Log.i(TAG, "storeItemsInDb: items store");
    }

    // FIXME: 2/22/2019 Использовать сначала данные из кэша перед загрузкой с сервера
    private Single<List<Item>> getItemsFromDb() {
        return db.itemDao().getAll()
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

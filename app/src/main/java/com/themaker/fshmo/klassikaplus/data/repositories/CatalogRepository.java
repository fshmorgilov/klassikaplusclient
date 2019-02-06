package com.themaker.fshmo.klassikaplus.data.repositories;

import android.util.Log;
import com.themaker.fshmo.klassikaplus.App;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.data.mappers.DbToDomainMapper;
import com.themaker.fshmo.klassikaplus.data.mappers.DtoToDbItemMapper;
import com.themaker.fshmo.klassikaplus.data.mappers.ListMapping;
import com.themaker.fshmo.klassikaplus.data.persistence.AppDatabase;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;
import com.themaker.fshmo.klassikaplus.data.repositories.filters.NoveltinessFilter;
import com.themaker.fshmo.klassikaplus.data.web.catalog.CatalogApi;
import com.themaker.fshmo.klassikaplus.data.web.dto.DataDto;
import com.themaker.fshmo.klassikaplus.data.web.dto.ItemDto;
import com.themaker.fshmo.klassikaplus.data.web.dto.ResponseDto;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class CatalogRepository extends BaseRepository {

    private static final String TAG = CatalogRepository.class.getName();

    final AppDatabase db = AppDatabase.provideRoomDatabase(App.getInstance());
    final CatalogApi api = CatalogApi.getInstance();

    private ListMapping<DbItem, Item> dbItemDomainMapper = new ListMapping<>(new DbToDomainMapper());
    private ListMapping<ItemDto, DbItem> itemDtoDbItemMapper = new ListMapping<>(new DtoToDbItemMapper());

    private NoveltinessFilter noveltinessFilter = new NoveltinessFilter();
    // TODO: 1/31/2019 refactor to dagger
    private static CatalogRepository INSTANCE;

    public CatalogRepository() {
        INSTANCE = this;
    }

    public static CatalogRepository getInstance() {
        synchronized (App.class) {
            if (INSTANCE == null)
                INSTANCE = new CatalogRepository();
            return INSTANCE;
        }
    }

    // TODO: 1/30/2019 Переделать если будет более 100 элементов
    public Single<List<Item>> provideNoveltyData() {
        return getItemsFromApi()
                .map(itemDtoDbItemMapper::map)
                .map(dbItemDomainMapper::map);
//        return getItemsFromDb();
//        Observable.concatArray(
//                getItemsFromDb(),
//                getItemsFromApi()
//         TODO: 1/13/2019 concat array
//        ).debounce(400, TimeUnit.MILLISECONDS);
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
                // TODO: 1/30/2019 refactor to Flowable
                .subscribeOn(Schedulers.io());
    }

    private void storeItemsInDb(List<ItemDto> items) {
        List<DbItem> dbItems = itemDtoDbItemMapper.map(items);
        db.itemDao().insertAll(dbItems);
        Log.i(TAG, "storeItemsInDb: items store");
    }

    private Single<List<Item>> getItemsFromDb() {
        return db.itemDao().getAll()
                .map(dbItemDomainMapper::map)
                .subscribeOn(Schedulers.io());
    }

}

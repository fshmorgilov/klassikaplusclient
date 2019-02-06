package com.themaker.fshmo.klassikaplus.data.mappers;

import android.util.Log;
import com.themaker.fshmo.klassikaplus.data.domain.Item;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;

public class DbToDomainMapper extends Mapping<DbItem, Item> {

    private static final String TAG = DbToDomainMapper.class.getName();

    @Override
    public Item map(DbItem dbItem) {
        Item item = new Item();
        item.setId(dbItem.getId());
        item.setIcon(dbItem.getIcon());
        item.setPrice(dbItem.getPrice());
        item.setName(dbItem.getName());
        item.setNovelty(dbItem.getNovelty());
        Log.d(TAG, "map: Item parsed: " + item.toString());
        return item;
    }
}

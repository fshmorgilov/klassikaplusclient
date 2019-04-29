package com.themaker.fshmo.klassikaplus.data.mappers;

import androidx.annotation.NonNull;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;
import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.items.ItemDto;

public class DtoToDbItemMapper extends Mapping<ItemDto, DbItem> {
    @Override
    public DbItem map(@NonNull ItemDto itemDto) {
        DbItem dbItem = new DbItem();
        dbItem.setId(itemDto.getId());
        dbItem.setExtId(itemDto.getExtId());
        dbItem.setName(itemDto.getName());
        dbItem.setDescription(itemDto.getDescription());
        dbItem.setVendorCode(itemDto.getVendorCode());
        dbItem.setNovelty(itemDto.isNovelty());
        dbItem.setPageAlias(itemDto.getPageAlias());
        dbItem.setIcon(itemDto.getIcon());
        dbItem.setBasePrice(itemDto.getBasePrice());
        dbItem.setDiscount(itemDto.getDiscount());
        dbItem.setPrice(itemDto.getPrice());
        dbItem.setCategory(itemDto.getCategory());
        dbItem.setCategoryId(itemDto.getCategoryId());
        return dbItem;
    }
}

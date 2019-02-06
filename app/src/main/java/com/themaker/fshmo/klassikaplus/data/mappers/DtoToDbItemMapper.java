package com.themaker.fshmo.klassikaplus.data.mappers;

import androidx.annotation.NonNull;
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbItem;
import com.themaker.fshmo.klassikaplus.data.web.dto.ItemDto;

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
//        dbItem.setPhotos
        // TODO: 1/30/2019 Маппинг для галлереи
//        dbItem.Discountable
//        dbItem.setPublished(itemDto.getP);
//        dbItem.setAnnotation(itemDto.geta);
//        dbItem.setTitle(itemDto.getTit);
        return dbItem;
    }
}

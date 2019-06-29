package com.themaker.fshmo.klassikaplus.data.mappers

import com.themaker.fshmo.klassikaplus.data.domain.Item
import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.items.ItemDto

class DtoToDomainItemMapper : Mapping<ItemDto, Item>() {
    override fun map(itemDto: ItemDto?): Item {
        return Item().apply {
            id = itemDto?.id
            setExtId(itemDto?.extId)
            icon = itemDto?.icon
            basePrice = itemDto?.basePrice
            description = itemDto?.description
            name = itemDto?.name
            vendorCode = itemDto?.vendorCode
            novelty = itemDto?.isNovelty
            pageAlias = itemDto?.pageAlias
            discount = itemDto?.discount?.toDouble()
            price = itemDto?.price
            category = itemDto?.category
            categoryId = itemDto?.categoryId
        }
    }
}
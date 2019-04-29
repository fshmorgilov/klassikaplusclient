package com.themaker.fshmo.klassikaplus.data.mappers

import com.themaker.fshmo.klassikaplus.data.domain.ItemCategory
import com.themaker.fshmo.klassikaplus.data.persistence.model.DbCategory

class DbToDomainCategoryMapper : Mapping<DbCategory, ItemCategory>() {
    override fun map(from: DbCategory?): ItemCategory {
        return ItemCategory(id = from!!.id, name = from.name)
    }

}
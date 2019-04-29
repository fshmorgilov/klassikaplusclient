package com.themaker.fshmo.klassikaplus.data.persistence.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "photos",
        foreignKeys =
        @ForeignKey(entity = DbItem.class,
                parentColumns = "id",
                childColumns = "item_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE))
public class DbPhoto {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "item_id")
    private String itemId;
    @ColumnInfo(name = "ext_id")
    private String extId;
    @ColumnInfo(name = "link")
    private String link;

    public int getId() {
        return id;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setId(int id) {
        this.id = id;
    }
}

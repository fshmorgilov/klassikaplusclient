package com.themaker.fshmo.klassikaplus.data.web.dto.catalog.items;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataDto implements Serializable {

    @SerializedName("items")
    @Expose
    private List<ItemDto> items = null;
    private final static long serialVersionUID = 6985802591318078436L;

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

}
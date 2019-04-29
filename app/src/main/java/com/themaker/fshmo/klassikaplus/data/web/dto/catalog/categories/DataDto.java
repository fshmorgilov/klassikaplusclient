package com.themaker.fshmo.klassikaplus.data.web.dto.catalog.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class DataDto implements Serializable {

    @SerializedName("categories")
    @Expose
    private Map<Integer, String> items = null;
    private final static long serialVersionUID = 6985812391318078436L;

    public Map<Integer, String> getItems() {
        return items;
    }

    public void setItems(Map<Integer, String> items) {
        this.items = items;
    }

}
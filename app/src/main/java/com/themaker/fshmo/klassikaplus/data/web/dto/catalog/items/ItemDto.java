package com.themaker.fshmo.klassikaplus.data.web.dto.catalog.items;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDto implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ext_id")
    @Expose
    private String extId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("vendorCode")
    @Expose
    private String vendorCode;
    @SerializedName("novelty")
    @Expose
    private boolean novelty;
    @SerializedName("pageAlias")
    @Expose
    private String pageAlias;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("base_price")
    @Expose
    private double basePrice;
    @SerializedName("discount")
    @Expose
    private int discount;
    @SerializedName("discountable")
    @Expose
    private boolean discountable;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("photos")
    @Expose
    private List<PhotoDto> photos = null;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;

    private final static long serialVersionUID = 4022723505261094500L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public boolean isNovelty() {
        return novelty;
    }

    public void setNovelty(boolean novelty) {
        this.novelty = novelty;
    }

    public String getPageAlias() {
        return pageAlias;
    }

    public void setPageAlias(String pageAlias) {
        this.pageAlias = pageAlias;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isDiscountable() {
        return discountable;
    }

    public void setDiscountable(boolean discountable) {
        this.discountable = discountable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<PhotoDto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDto> photos) {
        this.photos = photos;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
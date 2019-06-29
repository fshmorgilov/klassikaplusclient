package com.themaker.fshmo.klassikaplus.data.persistence.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.List;

@Entity(tableName = "items")
public class DbItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    @ColumnInfo(name = "extId")
    private String extId;

    @ColumnInfo(name = "name")
    private String name; //h1

    @ColumnInfo(name = "title")
    private String title;//title

    @ColumnInfo(name = "description")
    private String description; //ключевое описание

    @ColumnInfo(name = "annotation")
    private String annotation;

    @ColumnInfo(name = "vendor_code") //артикул
    private String vendorCode;

    @ColumnInfo(name = "shown_on_main") //показать на главной
    private Boolean shownOnMain;

    @ColumnInfo(name = "novelty")
    private Boolean novelty;

    @ColumnInfo(name = "description_long") //содержимое
    private String descriptionLong;

    @ColumnInfo(name = "page_alias")
    private String pageAlias;

    @ColumnInfo(name = "template")
    private String template;

    @ColumnInfo(name = "menu_item_name")
    private String menuItemName; //Пункт Меню

    @ColumnInfo(name = "related_link")
    private String relatedLink; // Сторонняя ссылка

    @ColumnInfo(name = "dont_show")
    private Boolean dontShowInMenu; //Не показывать в меню

    @ColumnInfo(name = "published")
    private Boolean published;

    @ColumnInfo(name = "keywords")
    private String keywords;

    @ColumnInfo(name = "icon")
    private String icon;

    @ColumnInfo(name = "favorite")
    private boolean favorite;

    ////------additional_fields-----------////
    @ColumnInfo (name = "category")
    private String category;

    @ColumnInfo (name = "category_id")
    private Integer categoryId;
    //Characteristics
    @Ignore
    private String countryManufacturer; //Страна производитель

    @Ignore
    private String composition; //хз че с этим делать

    @Ignore
    private String manufacturer; //производитель

    @Ignore
    private List<String> styleCollection;

    //Pricing
    @ColumnInfo(name = "base_price")
    private double basePrice;

    @ColumnInfo(name = "discount")
    private double discount;

    @ColumnInfo(name = "price")
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Boolean getShownOnMain() {
        return shownOnMain;
    }

    public void setShownOnMain(Boolean shownOnMain) {
        this.shownOnMain = shownOnMain;
    }

    public Boolean getNovelty() {
        return novelty;
    }

    public void setNovelty(Boolean novelty) {
        this.novelty = novelty;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    public String getIcon() {
        return icon;
    }

    public double getPrice() {
        return price;
    }

    public String getPageAlias() {
        return pageAlias;
    }

    public void setPageAlias(String pageAlias) {
        this.pageAlias = pageAlias;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getRelatedLink() {
        return relatedLink;
    }

    public void setRelatedLink(String relatedLink) {
        this.relatedLink = relatedLink;
    }

    public Boolean getDontShowInMenu() {
        return dontShowInMenu;
    }

    public void setDontShowInMenu(Boolean dontShowInMenu) {
        this.dontShowInMenu = dontShowInMenu;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setCountryManufacturer(String countryManufacturer) {
        this.countryManufacturer = countryManufacturer;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<String> getStyleCollection() {
        return styleCollection;
    }

    public void setStyleCollection(List<String> styleCollection) {
        this.styleCollection = styleCollection;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NonNull
    public String getExtId() {
        return extId;
    }

    public void setExtId(@NonNull String extId) {
        this.extId = extId;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}

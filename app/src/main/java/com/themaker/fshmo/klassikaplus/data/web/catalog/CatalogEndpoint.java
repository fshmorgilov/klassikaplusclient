package com.themaker.fshmo.klassikaplus.data.web.catalog;


import com.themaker.fshmo.klassikaplus.data.web.dto.catalog.items.ResponseDto;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CatalogEndpoint {

    @GET("catalog/novelties/")
    Single<ResponseDto> getNovelty();

    @GET("catalog/{categoryId}")
    Single<ResponseDto> getItemsByCategory(@Path("categoryId") Integer category);

    @GET("catalog/get_categories")
    Single<com.themaker.fshmo.klassikaplus.data.web.dto.catalog.categories.ResponseDto> getCategories();

}
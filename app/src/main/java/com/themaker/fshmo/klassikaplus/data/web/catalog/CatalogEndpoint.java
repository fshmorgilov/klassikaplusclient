package com.themaker.fshmo.klassikaplus.data.web.catalog;


import com.themaker.fshmo.klassikaplus.data.web.dto.ResponseDto;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface CatalogEndpoint {

    @GET("novelties")
    Single<ResponseDto> getNovelty();

}
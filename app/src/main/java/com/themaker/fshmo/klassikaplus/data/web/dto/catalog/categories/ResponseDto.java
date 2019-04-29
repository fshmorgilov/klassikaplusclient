package com.themaker.fshmo.klassikaplus.data.web.dto.catalog.categories;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.themaker.fshmo.klassikaplus.data.web.dto.common.ErrorDto;

public class ResponseDto implements Serializable {

    @SerializedName("data")
    @Expose
    private DataDto data;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errors")
    @Expose
    private List<ErrorDto> errors = null;
    private final static long serialVersionUID = -2518952915370601330L;

    public DataDto getData() {
        return data;
    }

    public void setData(DataDto data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }

}
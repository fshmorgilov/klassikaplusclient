package com.themaker.fshmo.klassikaplus.data.web.dto;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorDto implements Serializable
{

@SerializedName("code")
@Expose
private int code;
@SerializedName("description")
@Expose
private String description;
private final static long serialVersionUID = 5865768659366165626L;

public int getCode() {
return code;
}

public void setCode(int code) {
this.code = code;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

}
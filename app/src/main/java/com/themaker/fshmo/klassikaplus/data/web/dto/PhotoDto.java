package com.themaker.fshmo.klassikaplus.data.web.dto;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoDto implements Serializable
{

@SerializedName("id")
@Expose
private String id;
@SerializedName("image")
@Expose
private String image;
private final static long serialVersionUID = -9032062017948970785L;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

}
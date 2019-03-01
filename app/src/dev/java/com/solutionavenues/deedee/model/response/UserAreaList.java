package com.solutionavenues.deedee.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by imran khan 9829151320 .
 */
public class UserAreaList implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("area")
    @Expose
    private String area;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

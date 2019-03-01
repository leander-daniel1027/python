package com.solutionavenues.deedee.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by imran khan 9829151320 .
 */
public class CompanyList {

    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CompanyName> data = null;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CompanyName> getData() {
        return data;
    }

    public void setData(List<CompanyName> data) {
        this.data = data;
    }
}

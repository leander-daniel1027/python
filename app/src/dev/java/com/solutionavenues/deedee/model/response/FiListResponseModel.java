package com.solutionavenues.deedee.model.response;

import com.solutionavenues.deedee.model.request.AddFiRequestModel;

import java.util.ArrayList;

/**
 * Created by Azher on 19/7/18.
 */
public class FiListResponseModel extends BaseWebResponseModel {

    public ArrayList<AddFiRequestModel> getData() {
        return data;
    }

    public void setData(ArrayList<AddFiRequestModel> data) {
        this.data = data;
    }

    public ArrayList<AddFiRequestModel> data;
}

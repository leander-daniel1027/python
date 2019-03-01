package com.solutionavenues.deedee.model.response;

import com.solutionavenues.deedee.model.request.AddLeadRequestModel;

import java.util.ArrayList;

/**
 * Created by Azher on 7/7/18.
 */
public class LeadListResponseModel extends BaseWebResponseModel {

    public ArrayList<AddLeadRequestModel> getData() {
        return data;
    }

    public void setData(ArrayList<AddLeadRequestModel> data) {
        this.data = data;
    }

    public ArrayList<AddLeadRequestModel> data;
}

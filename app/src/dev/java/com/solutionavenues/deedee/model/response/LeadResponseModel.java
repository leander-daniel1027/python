package com.solutionavenues.deedee.model.response;

import com.solutionavenues.deedee.model.request.AddLeadRequestModel;

/**
 * Created by Azher on 7/7/18.
 */
public class LeadResponseModel extends BaseWebResponseModel {

    public AddLeadRequestModel getData() {
        return data;
    }

    public void setData(AddLeadRequestModel data) {
        this.data = data;
    }

    public AddLeadRequestModel data;
}

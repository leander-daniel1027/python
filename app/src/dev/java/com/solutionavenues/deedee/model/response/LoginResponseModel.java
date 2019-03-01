package com.solutionavenues.deedee.model.response;

import com.solutionavenues.deedee.model.UserModel;

/**
 * Created by br on 27/3/18.
 */

public class LoginResponseModel extends BaseWebResponseModel {

    private UserModel data;


    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }


}

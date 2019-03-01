package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 30/6/18.
 */
public class CenterListResponseModel extends BaseWebResponseModel {


    private ArrayList<DataBean> data;

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * name : Bagru
         * center_code : BagMan
         * center_location : Jaipur, Rajasthan, India
         * center_owner : Manish
         * center_owner_contact : 9878888
         * branche_id : 3
         * latitude : 26.9124336
         * longitude : 75.78727090000007
         * status : 2
         * user_id : 1
         * created : 2018-06-30T09:10:10+00:00
         */

        private int id;
        private String name;
        private String center_code;
        private String center_location;
        private String center_owner;
        private String center_owner_contact;
        private int branche_id;
        private String latitude;
        private String longitude;
        private int status;
        private int user_id;
        private String created;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCenter_code() {
            return getValidString(center_code);
        }

        public void setCenter_code(String center_code) {
            this.center_code = center_code;
        }

        public String getCenter_location() {
            return getValidString(center_location);
        }

        public void setCenter_location(String center_location) {
            this.center_location = center_location;
        }

        public String getCenter_owner() {
            return getValidString(center_owner);
        }

        public void setCenter_owner(String center_owner) {
            this.center_owner = center_owner;
        }

        public String getCenter_owner_contact() {
            return getValidString(center_owner_contact);
        }

        public void setCenter_owner_contact(String center_owner_contact) {
            this.center_owner_contact = center_owner_contact;
        }

        public int getBranche_id() {
            return branche_id;
        }

        public void setBranche_id(int branche_id) {
            this.branche_id = branche_id;
        }

        public String getLatitude() {
            return getValidString(latitude);
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return getValidString(longitude);
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}

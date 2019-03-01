package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 3/7/18.
 */
public class AddCenterResponseModel {
    /**
     * code : 2
     * error : true
     * data : [{"id":"15","name":"divya mall","center_code":"divsau","center_location":"Divya Mall, Tonk Road, Satya Vihar, Everest Colony, Vidhayak Nagar, Lalkothi, Jaipur, Rajasthan, India","center_owner":"saurabh","center_owner_contact":"99999","branche_id":"2","latitude":"26.8894924","longitude":"75.80377520000002","status":"2","user_id":"1","created":"2018-07-02 17:03:28","distance":"38.048582840451"},{"id":"3","name":"Bagru","center_code":"BagMan","center_location":"Jaipur, Rajasthan, India","center_owner":"Manish","center_owner_contact":"9878888","branche_id":"3","latitude":"26.9124336","longitude":"75.78727090000007","status":"2","user_id":"1","created":"2018-06-30 09:10:10","distance":"37.74141099549851"}]
     * message : The center could not be saved. Because other center found near at you location
     */

    private int code;
    private boolean error;
    private String message;
    private ArrayList<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 15
         * name : divya mall
         * center_code : divsau
         * center_location : Divya Mall, Tonk Road, Satya Vihar, Everest Colony, Vidhayak Nagar, Lalkothi, Jaipur, Rajasthan, India
         * center_owner : saurabh
         * center_owner_contact : 99999
         * branche_id : 2
         * latitude : 26.8894924
         * longitude : 75.80377520000002
         * status : 2
         * user_id : 1
         * created : 2018-07-02 17:03:28
         * distance : 38.048582840451
         */

        private String id;
        private String name;
        private String center_code;
        private String center_location;
        private String center_owner;
        private String center_owner_contact;
        private String branche_id;
        private String latitude;
        private String longitude;
        private String status;
        private String user_id;
        private String created;
        private String distance;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCenter_code() {
            return center_code;
        }

        public void setCenter_code(String center_code) {
            this.center_code = center_code;
        }

        public String getCenter_location() {
            return center_location;
        }

        public void setCenter_location(String center_location) {
            this.center_location = center_location;
        }

        public String getCenter_owner() {
            return center_owner;
        }

        public void setCenter_owner(String center_owner) {
            this.center_owner = center_owner;
        }

        public String getCenter_owner_contact() {
            return center_owner_contact;
        }

        public void setCenter_owner_contact(String center_owner_contact) {
            this.center_owner_contact = center_owner_contact;
        }

        public String getBranche_id() {
            return branche_id;
        }

        public void setBranche_id(String branche_id) {
            this.branche_id = branche_id;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}

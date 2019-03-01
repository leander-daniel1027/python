package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 6/7/18.
 */
public class GroupListResponseModel {

    /**
     * code : 1
     * error : false
     * data : [{"id":9,"center_id":3,"name":"suarabhj","code":"sua702","leader_name":"","leader_contact":"","number_of_member":1,"user_id":1,"status":2,"created":"2018-07-02T15:33:25+00:00","modified":"2018-07-04T14:51:04+00:00"}]
     * message : Success
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
         * id : 9
         * center_id : 3
         * name : suarabhj
         * code : sua702
         * leader_name :
         * leader_contact :
         * number_of_member : 1
         * user_id : 1
         * status : 2
         * created : 2018-07-02T15:33:25+00:00
         * modified : 2018-07-04T14:51:04+00:00
         */

        private int id;
        private int center_id;
        private String name;
        private String code;
        private String leader_name;
        private String leader_contact,lat,lng;
        private int number_of_member;
        private int user_id;
        private int status;
        private String created;
        private String modified;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCenter_id() {
            return center_id;
        }

        public void setCenter_id(int center_id) {
            this.center_id = center_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLeader_name() {
            return leader_name;
        }

        public void setLeader_name(String leader_name) {
            this.leader_name = leader_name;
        }

        public String getLeader_contact() {
            return leader_contact;
        }

        public void setLeader_contact(String leader_contact) {
            this.leader_contact = leader_contact;
        }

        public int getNumber_of_member() {
            return number_of_member;
        }

        public void setNumber_of_member(int number_of_member) {
            this.number_of_member = number_of_member;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }
    }
}

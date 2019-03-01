package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;


public class RecoveryGroupModel {
    /**
     * data : [{"group_id":43,"group_code":"man341","group_name":"manish.lo.group","leader_name":"bsbshs","leader_contact":"464","inst_day":"First Wednesday  5:45 PM","inst_time":"5:45 PM","latitude":"26.889493","longitude":"75.803795","center_name":"manju devi center","center_code":"manman","center_location":"5, Sawai Mansingh Rd, Satya Vihar, Everest Colony, Vidhayak Nagar, Lalkothi, Jaipur, Rajasthan 302015, India","nextRecoveryDate":"2019-02-05"}]
     * isSuccess : true
     * message : No Group Found For Recovery
     */

    private boolean isSuccess;
    private String message;
    private ArrayList<DataBean> data;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
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
         * group_id : 43
         * group_code : man341
         * group_name : manish.lo.group
         * leader_name : bsbshs
         * leader_contact : 464
         * inst_day : First Wednesday  5:45 PM
         * inst_time : 5:45 PM
         * latitude : 26.889493
         * longitude : 75.803795
         * center_name : manju devi center
         * center_code : manman
         * center_location : 5, Sawai Mansingh Rd, Satya Vihar, Everest Colony, Vidhayak Nagar, Lalkothi, Jaipur, Rajasthan 302015, India
         * nextRecoveryDate : 2019-02-05
         */

        private int group_id;
        private String group_code;
        private String group_name;
        private String leader_name;
        private String leader_contact;
        private String inst_day;
        private String inst_time;
        private String latitude;
        private String longitude;
        private String center_name;
        private String center_code;
        private String center_location;
        private String nextRecoveryDate;

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getGroup_code() {
            return group_code;
        }

        public void setGroup_code(String group_code) {
            this.group_code = group_code;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
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

        public String getInst_day() {
            return inst_day;
        }

        public void setInst_day(String inst_day) {
            this.inst_day = inst_day;
        }

        public String getInst_time() {
            return inst_time;
        }

        public void setInst_time(String inst_time) {
            this.inst_time = inst_time;
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

        public String getCenter_name() {
            return center_name;
        }

        public void setCenter_name(String center_name) {
            this.center_name = center_name;
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

        public String getNextRecoveryDate() {
            return nextRecoveryDate;
        }

        public void setNextRecoveryDate(String nextRecoveryDate) {
            this.nextRecoveryDate = nextRecoveryDate;
        }
    }
}

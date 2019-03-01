package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 13/7/18.
 */
public class AppointmentListResponseModel {
    /**
     * code : 1
     * error : false
     * data : [{"id":7,"tele_date":"17/07/2018","place":"asdf","remark":"asdf","user_id":1,"lead_id":1,"deleted_by":1,"notify":0,"status":1,"created":"2018-07-13T07:23:57+00:00"},{"id":8,"tele_date":"17/07/2018","place":"asdf","remark":"asdf","user_id":1,"lead_id":1,"deleted_by":0,"notify":0,"status":1,"created":"2018-07-13T08:00:07+00:00"}]
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
         * id : 7
         * tele_date : 17/07/2018
         * place : asdf
         * remark : asdf
         * user_id : 1
         * lead_id : 1
         * deleted_by : 1
         * notify : 0
         * status : 1
         * created : 2018-07-13T07:23:57+00:00
         */

        private int id;
        private String tele_date;
        private String place;
        private String remark;
        private int user_id;
        private int lead_id;
        private int deleted_by;
        private int notify;
        private int status;
        private String created;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTele_date() {
            return tele_date;
        }

        public void setTele_date(String tele_date) {
            this.tele_date = tele_date;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getLead_id() {
            return lead_id;
        }

        public void setLead_id(int lead_id) {
            this.lead_id = lead_id;
        }

        public int getDeleted_by() {
            return deleted_by;
        }

        public void setDeleted_by(int deleted_by) {
            this.deleted_by = deleted_by;
        }

        public int getNotify() {
            return notify;
        }

        public void setNotify(int notify) {
            this.notify = notify;
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
    }
}

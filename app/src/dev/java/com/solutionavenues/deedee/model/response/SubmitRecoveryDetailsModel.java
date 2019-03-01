package com.solutionavenues.deedee.model.response;

import java.util.List;

/**
 * Created by Azher on 3/11/18.
 */
public class SubmitRecoveryDetailsModel {
    /**
     * isSuccess : true
     * message : Your details successfully fetched.
     * data : [{"id":1,"Singnature":"No","collect_from":"Home","collect_from_self":"Yes","collected_date":"2018-11-03T00:00:00","recovery_by":2394,"present_on_center":"No","part_payment":"500","delay_charge":"320","is_completed":2,"modified":"2018-11-03T21:53:30"}]
     */

    private boolean isSuccess;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * Singnature : No
         * collect_from : Home
         * collect_from_self : Yes
         * collected_date : 2018-11-03T00:00:00
         * recovery_by : 2394
         * present_on_center : No
         * part_payment : 500
         * delay_charge : 320
         * is_completed : 2
         * modified : 2018-11-03T21:53:30
         */

        private int id;
        private String Singnature;
        private String collect_from;
        private String collect_from_self;
        private String collected_date;
        private int recovery_by;
        private String present_on_center;
        private String part_payment;
        private String delay_charge;
        private int is_completed;
        private String modified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSingnature() {
            return Singnature;
        }

        public void setSingnature(String Singnature) {
            this.Singnature = Singnature;
        }

        public String getCollect_from() {
            return collect_from;
        }

        public void setCollect_from(String collect_from) {
            this.collect_from = collect_from;
        }

        public String getCollect_from_self() {
            return collect_from_self;
        }

        public void setCollect_from_self(String collect_from_self) {
            this.collect_from_self = collect_from_self;
        }

        public String getCollected_date() {
            return collected_date;
        }

        public void setCollected_date(String collected_date) {
            this.collected_date = collected_date;
        }

        public int getRecovery_by() {
            return recovery_by;
        }

        public void setRecovery_by(int recovery_by) {
            this.recovery_by = recovery_by;
        }

        public String getPresent_on_center() {
            return present_on_center;
        }

        public void setPresent_on_center(String present_on_center) {
            this.present_on_center = present_on_center;
        }

        public String getPart_payment() {
            return part_payment;
        }

        public void setPart_payment(String part_payment) {
            this.part_payment = part_payment;
        }

        public String getDelay_charge() {
            return delay_charge;
        }

        public void setDelay_charge(String delay_charge) {
            this.delay_charge = delay_charge;
        }

        public int getIs_completed() {
            return is_completed;
        }

        public void setIs_completed(int is_completed) {
            this.is_completed = is_completed;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }
    }
}

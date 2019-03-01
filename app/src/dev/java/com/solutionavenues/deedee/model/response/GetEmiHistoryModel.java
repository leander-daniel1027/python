package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 2/11/18.
 */
public class GetEmiHistoryModel {
    /**
     * data : {"user_data":{"customer_name":"bsbshs"},"emi_data":[{"emi_date":"2018-10-02","application_area_id":"41","recovery_id":13,"inst_no":1,"inst_amount":"2596","part_payment":"0","amount_need_to_collect":2596,"notes":"4 Days Interest Extra Applied","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":3025,"delay_day":"121 Days"},{"emi_date":"2018-11-06","application_area_id":"41","recovery_id":14,"inst_no":2,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":2150,"delay_day":"86 Days"},{"emi_date":"2018-12-04","application_area_id":"41","recovery_id":15,"inst_no":3,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":1450,"delay_day":"58 Days"},{"emi_date":"2019-01-01","application_area_id":"41","recovery_id":16,"inst_no":4,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":750,"delay_day":"30 Days"},{"emi_date":"2019-02-05","application_area_id":"41","recovery_id":17,"inst_no":5,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-03-05","application_area_id":"41","recovery_id":18,"inst_no":6,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-04-02","application_area_id":"41","recovery_id":19,"inst_no":7,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-05-07","application_area_id":"41","recovery_id":20,"inst_no":8,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-06-04","application_area_id":"41","recovery_id":21,"inst_no":9,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-07-02","application_area_id":"41","recovery_id":22,"inst_no":10,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-08-06","application_area_id":"41","recovery_id":23,"inst_no":11,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-09-03","application_area_id":"41","recovery_id":24,"inst_no":12,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"}]}
     * isSuccess : true
     * message : Emi Found For Recovery
     */

    private DataBean data;
    private boolean isSuccess;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * user_data : {"customer_name":"bsbshs"}
         * emi_data : [{"emi_date":"2018-10-02","application_area_id":"41","recovery_id":13,"inst_no":1,"inst_amount":"2596","part_payment":"0","amount_need_to_collect":2596,"notes":"4 Days Interest Extra Applied","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":3025,"delay_day":"121 Days"},{"emi_date":"2018-11-06","application_area_id":"41","recovery_id":14,"inst_no":2,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":2150,"delay_day":"86 Days"},{"emi_date":"2018-12-04","application_area_id":"41","recovery_id":15,"inst_no":3,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":1450,"delay_day":"58 Days"},{"emi_date":"2019-01-01","application_area_id":"41","recovery_id":16,"inst_no":4,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":750,"delay_day":"30 Days"},{"emi_date":"2019-02-05","application_area_id":"41","recovery_id":17,"inst_no":5,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-03-05","application_area_id":"41","recovery_id":18,"inst_no":6,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-04-02","application_area_id":"41","recovery_id":19,"inst_no":7,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-05-07","application_area_id":"41","recovery_id":20,"inst_no":8,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-06-04","application_area_id":"41","recovery_id":21,"inst_no":9,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-07-02","application_area_id":"41","recovery_id":22,"inst_no":10,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-08-06","application_area_id":"41","recovery_id":23,"inst_no":11,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"},{"emi_date":"2019-09-03","application_area_id":"41","recovery_id":24,"inst_no":12,"inst_amount":"2500","part_payment":"0","amount_need_to_collect":2500,"notes":"","collected_amount":"","Singnature":"","collect_from":"","collect_from_self":"","present_on_center":"","collected_date":null,"is_completed":0,"delay_charge_collected":null,"recovery_by":0,"delay_charge":0,"delay_day":"0 Days"}]
         */

        private UserDataBean user_data;
        private ArrayList<EmiDataBean> emi_data;

        public UserDataBean getUser_data() {
            return user_data;
        }

        public void setUser_data(UserDataBean user_data) {
            this.user_data = user_data;
        }

        public ArrayList<EmiDataBean> getEmi_data() {
            return emi_data;
        }

        public void setEmi_data(ArrayList<EmiDataBean> emi_data) {
            this.emi_data = emi_data;
        }

        public static class UserDataBean {
            /**
             * customer_name : bsbshs
             */

            private String customer_name;

            public String getCustomer_name() {
                return customer_name;
            }

            public void setCustomer_name(String customer_name) {
                this.customer_name = customer_name;
            }
        }

        public static class EmiDataBean {
            /**
             * emi_date : 2018-10-02
             * application_area_id : 41
             * recovery_id : 13
             * inst_no : 1
             * inst_amount : 2596
             * part_payment : 0
             * amount_need_to_collect : 2596
             * notes : 4 Days Interest Extra Applied
             * collected_amount :
             * Singnature :
             * collect_from :
             * collect_from_self :
             * present_on_center :
             * collected_date : null
             * is_completed : 0
             * delay_charge_collected : null
             * recovery_by : 0
             * delay_charge : 3025
             * delay_day : 121 Days
             */

            private String emi_date;
            private String application_area_id;
            private int recovery_id;
            private int inst_no;
            private String inst_amount;
            private String part_payment;
            private int amount_need_to_collect;
            private String notes;
            private String collected_amount;
            private String Singnature;
            private String collect_from;
            private String collect_from_self;
            private String present_on_center;
            private String collected_date;
            private int is_completed;
            private String delay_charge_collected;
            private int recovery_by;
            private int delay_charge;
            private String delay_day;

            public String getEmi_date() {
                return emi_date;
            }

            public void setEmi_date(String emi_date) {
                this.emi_date = emi_date;
            }

            public String getApplication_area_id() {
                return application_area_id;
            }

            public void setApplication_area_id(String application_area_id) {
                this.application_area_id = application_area_id;
            }

            public int getRecovery_id() {
                return recovery_id;
            }

            public void setRecovery_id(int recovery_id) {
                this.recovery_id = recovery_id;
            }

            public int getInst_no() {
                return inst_no;
            }

            public void setInst_no(int inst_no) {
                this.inst_no = inst_no;
            }

            public String getInst_amount() {
                return inst_amount;
            }

            public void setInst_amount(String inst_amount) {
                this.inst_amount = inst_amount;
            }

            public String getPart_payment() {
                return part_payment;
            }

            public void setPart_payment(String part_payment) {
                this.part_payment = part_payment;
            }

            public int getAmount_need_to_collect() {
                return amount_need_to_collect;
            }

            public void setAmount_need_to_collect(int amount_need_to_collect) {
                this.amount_need_to_collect = amount_need_to_collect;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getCollected_amount() {
                return collected_amount;
            }

            public void setCollected_amount(String collected_amount) {
                this.collected_amount = collected_amount;
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

            public String getPresent_on_center() {
                return present_on_center;
            }

            public void setPresent_on_center(String present_on_center) {
                this.present_on_center = present_on_center;
            }

            public String getCollected_date() {
                return collected_date;
            }

            public void setCollected_date(String collected_date) {
                this.collected_date = collected_date;
            }

            public int getIs_completed() {
                return is_completed;
            }

            public void setIs_completed(int is_completed) {
                this.is_completed = is_completed;
            }

            public String getDelay_charge_collected() {
                return delay_charge_collected;
            }

            public void setDelay_charge_collected(String delay_charge_collected) {
                this.delay_charge_collected = delay_charge_collected;
            }

            public int getRecovery_by() {
                return recovery_by;
            }

            public void setRecovery_by(int recovery_by) {
                this.recovery_by = recovery_by;
            }

            public int getDelay_charge() {
                return delay_charge;
            }

            public void setDelay_charge(int delay_charge) {
                this.delay_charge = delay_charge;
            }

            public String getDelay_day() {
                return delay_day;
            }

            public void setDelay_day(String delay_day) {
                this.delay_day = delay_day;
            }
        }
    }
}

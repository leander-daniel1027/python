package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 1/11/18.
 */
public class GroupMemberListModel {
    /**
     * data : {"group_data":{"group_id":43,"group_code":"man341","group_name":"manish.lo.group","inst_day":"First Wednesday  5:45 PM","inst_time":"5:45 PM"},"members":[{"group_id":"43","application_area_id":41,"name":"bsbshs","mobile_contact":"464","emiData":{"recovery_id":17,"inst_no":5,"next_emi":"2500","next_date":"2019-02-05","pending_amount":10096,"delay_charges":7375,"delay_days":295}},{"group_id":"43","application_area_id":42,"name":"zhshs","mobile_contact":"5656","emiData":{"recovery_id":5,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":43,"name":"manish","mobile_contact":"89595","emiData":{"recovery_id":29,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":52,"name":"Azher","mobile_contact":"5867372727","emiData":{"recovery_id":53,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":53,"name":"Azher","mobile_contact":"5867372727","emiData":{"recovery_id":65,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":54,"name":"Azher","mobile_contact":"5867372727","emiData":{"recovery_id":77,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":56,"name":"Sunita Devi","mobile_contact":"8656421656","emiData":{"recovery_id":89,"inst_no":5,"next_emi":"2500","next_date":"2019-02-05","pending_amount":10096,"delay_charges":7375,"delay_days":295}}]}
     * isSuccess : true
     * message : Member Found For Recovery
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
         * group_data : {"group_id":43,"group_code":"man341","group_name":"manish.lo.group","inst_day":"First Wednesday  5:45 PM","inst_time":"5:45 PM"}
         * members : [{"group_id":"43","application_area_id":41,"name":"bsbshs","mobile_contact":"464","emiData":{"recovery_id":17,"inst_no":5,"next_emi":"2500","next_date":"2019-02-05","pending_amount":10096,"delay_charges":7375,"delay_days":295}},{"group_id":"43","application_area_id":42,"name":"zhshs","mobile_contact":"5656","emiData":{"recovery_id":5,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":43,"name":"manish","mobile_contact":"89595","emiData":{"recovery_id":29,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":52,"name":"Azher","mobile_contact":"5867372727","emiData":{"recovery_id":53,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":53,"name":"Azher","mobile_contact":"5867372727","emiData":{"recovery_id":65,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":54,"name":"Azher","mobile_contact":"5867372727","emiData":{"recovery_id":77,"inst_no":5,"next_emi":"3000","next_date":"2019-02-05","pending_amount":12116,"delay_charges":2950,"delay_days":295}},{"group_id":"43","application_area_id":56,"name":"Sunita Devi","mobile_contact":"8656421656","emiData":{"recovery_id":89,"inst_no":5,"next_emi":"2500","next_date":"2019-02-05","pending_amount":10096,"delay_charges":7375,"delay_days":295}}]
         */

        private GroupDataBean group_data;
        private ArrayList<MembersBean> members;

        public GroupDataBean getGroup_data() {
            return group_data;
        }

        public void setGroup_data(GroupDataBean group_data) {
            this.group_data = group_data;
        }

        public ArrayList<MembersBean> getMembers() {
            return members;
        }

        public void setMembers(ArrayList<MembersBean> members) {
            this.members = members;
        }

        public static class GroupDataBean {
            /**
             * group_id : 43
             * group_code : man341
             * group_name : manish.lo.group
             * inst_day : First Wednesday  5:45 PM
             * inst_time : 5:45 PM
             */

            private int group_id;
            private String group_code;
            private String group_name;
            private String inst_day;
            private String inst_time;

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
        }

        public static class MembersBean {
            /**
             * group_id : 43
             * application_area_id : 41
             * name : bsbshs
             * mobile_contact : 464
             * emiData : {"recovery_id":17,"inst_no":5,"next_emi":"2500","next_date":"2019-02-05","pending_amount":10096,"delay_charges":7375,"delay_days":295}
             */

            private String group_id;
            private int application_area_id;
            private String name;
            private String mobile_contact;
            private EmiDataBean emiData;

            public String getIs_closed() {
                return is_closed;
            }

            public void setIs_closed(String is_closed) {
                this.is_closed = is_closed;
            }

            private String is_closed;

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public int getApplication_area_id() {
                return application_area_id;
            }

            public void setApplication_area_id(int application_area_id) {
                this.application_area_id = application_area_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile_contact() {
                return mobile_contact;
            }

            public void setMobile_contact(String mobile_contact) {
                this.mobile_contact = mobile_contact;
            }

            public EmiDataBean getEmiData() {
                return emiData;
            }

            public void setEmiData(EmiDataBean emiData) {
                this.emiData = emiData;
            }

            public static class EmiDataBean {
                /**
                 * recovery_id : 17
                 * inst_no : 5
                 * next_emi : 2500
                 * next_date : 2019-02-05
                 * pending_amount : 10096
                 * delay_charges : 7375
                 * delay_days : 295
                 */

                private int recovery_id;
                private int inst_no;
                private String next_emi;
                private String next_date;
                private String emi_date;
                private double emi_amount;
                private double pending_amount;
                private double delay_charges;
                private int delay_days;
                private String collect_from;
                private String collect_from_self;
                private String Singnature;

                public String getEmi_date() {
                    return emi_date;
                }
                public void setEmi_date(String emi_date) {
                    this.emi_date = emi_date;
                }

                public double getEmi_amount() {
                    return emi_amount;
                }

                public void setEmi_amount(double emi_amount) {
                    this.emi_amount = emi_amount;
                }

                public double getPending_amount() {
                    return pending_amount;
                }

                public void setPending_amount(double pending_amount) {
                    this.pending_amount = pending_amount;
                }

                public double getDelay_charges() {
                    return delay_charges;
                }

                public void setDelay_charges(double delay_charges) {
                    this.delay_charges = delay_charges;
                }

                public int getDelay_days() {
                    return delay_days;
                }

                public void setDelay_days(int delay_days) {
                    this.delay_days = delay_days;
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

                public String getSingnature() {
                    return Singnature;
                }

                public void setSingnature(String singnature) {
                    Singnature = singnature;
                }

                public int getCollected_date() {
                    return collected_date;
                }

                public void setCollected_date(int collected_date) {
                    this.collected_date = collected_date;
                }

                public int getPresent_on_center() {
                    return present_on_center;
                }

                public void setPresent_on_center(int present_on_center) {
                    this.present_on_center = present_on_center;
                }

                public int getApplication_area_id() {
                    return application_area_id;
                }

                public void setApplication_area_id(int application_area_id) {
                    this.application_area_id = application_area_id;
                }

                public double getCollected_amount() {
                    return collected_amount;
                }

                public void setCollected_amount(double collected_amount) {
                    this.collected_amount = collected_amount;
                }

                private int collected_date;
                private int present_on_center;
                private int application_area_id;
                private double collected_amount;

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

                public String getNext_emi() {
                    return next_emi;
                }

                public void setNext_emi(String next_emi) {
                    this.next_emi = next_emi;
                }

                public String getNext_date() {
                    return next_date;
                }

                public void setNext_date(String next_date) {
                    this.next_date = next_date;
                }


            }
        }
    }
}

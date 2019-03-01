package com.solutionavenues.deedee.model.request;

/**
 * Created by Azher on 23/6/18.
 */
public class AddAppointmentRequestModel extends BaseWebRequestModel {

    public String tele_date;
    public String place;
    public String remark;
    public String user_id;
    public String lead_id;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }





}

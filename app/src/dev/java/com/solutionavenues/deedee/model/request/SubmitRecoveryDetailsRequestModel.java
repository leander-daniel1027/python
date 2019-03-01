package com.solutionavenues.deedee.model.request;

/**
 * Created by Azher on 3/11/18.
 */
public class SubmitRecoveryDetailsRequestModel extends BaseWebRequestModel {


    public int recovery_id;
    public int user_id;
    public String collected_amount;
    public String Singnature;
    public String collect_from;
    public String collect_from_self;
    public String collected_date;
    public String present_on_center;

    public int getRecovery_id() {
        return recovery_id;
    }

    public void setRecovery_id(int recovery_id) {
        this.recovery_id = recovery_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public void setSingnature(String singnature) {
        Singnature = singnature;
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

    public String getPresent_on_center() {
        return present_on_center;
    }

    public void setPresent_on_center(String present_on_center) {
        this.present_on_center = present_on_center;
    }

    public int getApplication_area_id() {
        return application_area_id;
    }

    public void setApplication_area_id(int application_area_id) {
        this.application_area_id = application_area_id;
    }

    public String getDelay_charge() {
        return delay_charge;
    }

    public void setDelay_charge(String delay_charge) {
        this.delay_charge = delay_charge;
    }

    public int application_area_id;
    public String delay_charge;
}

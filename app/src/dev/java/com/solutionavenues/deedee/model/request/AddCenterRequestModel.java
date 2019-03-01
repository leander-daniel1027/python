package com.solutionavenues.deedee.model.request;

/**
 * Created by Azher on 23/6/18.
 */
public class AddCenterRequestModel extends BaseWebRequestModel {

    public String user_id;
    public String name;
    public String center_location;
    public String latitude;
    public String longitude;
    public String center_owner;
    public String center_owner_contact;
    public String branche_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCenter_location() {
        return center_location;
    }

    public void setCenter_location(String center_location) {
        this.center_location = center_location;
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


}

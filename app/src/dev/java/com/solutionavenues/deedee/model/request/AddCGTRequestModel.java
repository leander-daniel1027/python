package com.solutionavenues.deedee.model.request;

/**
 * Created by solave-design on 7/20/2018.
 */

public class AddCGTRequestModel extends BaseWebRequestModel{

    public String user_id;
    public String lead_id;
    public String day1;
    public String day2;
    public String question_a;
    public String question_b;
    public String question_c;
    public String question_d;
    public String file;

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

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getQuestion_a() {
        return question_a;
    }

    public void setQuestion_a(String question_a) {
        this.question_a = question_a;
    }

    public String getQuestion_b() {
        return question_b;
    }

    public void setQuestion_b(String question_b) {
        this.question_b = question_b;
    }

    public String getQuestion_c() {
        return question_c;
    }

    public void setQuestion_c(String question_c) {
        this.question_c = question_c;
    }

    public String getQuestion_d() {
        return question_d;
    }

    public void setQuestion_d(String question_d) {
        this.question_d = question_d;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }




}

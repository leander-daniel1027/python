package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 25/6/18.
 */
public class EnquiryListResponseModel extends BaseWebResponseModel {

    private ArrayList<DataBean> data;

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * applicant_name : vicky
         * applicant_father_name : vicky
         * applicant_mother_name : vicky
         * spouse_name : vicky
         * applicant_dob : 2018-06-20T00:00:00+00:00
         * applicant_aadhar_no : 312456798
         * applicant_aadhar_image : 271719685_Chrysanthemum.jpg
         * applicant_id_number : 8765
         * applicant_id_image : 1371732219_Jellyfish.jpg
         * applicant_mobile1 : 879546231465
         * applicant_mobile2 : 213646749
         * applicant_address : sdfsadf
         * user_id : 1
         * added_form : asdf
         * status : 1
         * created : 2018-06-20T03:17:34+00:00
         * modified : 2018-06-20T03:34:50+00:00
         */

        private String id;
        private String applicant_name;
        private String applicant_father_name;
        private String applicant_mother_name;
        private String spouse_name;
        private String applicant_dob;
        private String applicant_aadhar_no;
        private String applicant_aadhar_image;
        private String applicant_id_number;
        private String applicant_id_image;
        private String applicant_mobile1;
        private String applicant_mobile2;
        private String applicant_address;
        private String user_id;
        private String added_form;
        private String status;
        private String cebil_status;
        private String created;
        private String modified;
        public String pincode;
        public String area_id;

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getLoan_amount() {
            return loan_amount;
        }

        public void setLoan_amount(String loan_amount) {
            this.loan_amount = loan_amount;
        }

        public String getApplicant_aadhar_type() {
            return applicant_aadhar_type;
        }

        public void setApplicant_aadhar_type(String applicant_aadhar_type) {
            this.applicant_aadhar_type = applicant_aadhar_type;
        }

        public String getApplicant_id_type() {
            return applicant_id_type;
        }

        public void setApplicant_id_type(String applicant_id_type) {
            this.applicant_id_type = applicant_id_type;
        }

        public String loan_amount;
        public String applicant_aadhar_type;
        public String applicant_id_type;


        public String getCebil_status() {
            return cebil_status;
        }

        public void setCebil_status(String cebil_status) {
            this.cebil_status = cebil_status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getApplicant_name() {
            return getValidString(applicant_name);
        }

        public void setApplicant_name(String applicant_name) {
            this.applicant_name = applicant_name;
        }

        public String getApplicant_father_name() {
            return getValidString(applicant_father_name);
        }

        public void setApplicant_father_name(String applicant_father_name) {
            this.applicant_father_name = applicant_father_name;
        }

        public String getApplicant_mother_name() {
            return getValidString(applicant_mother_name);
        }

        public void setApplicant_mother_name(String applicant_mother_name) {
            this.applicant_mother_name = applicant_mother_name;
        }

        public String getSpouse_name() {
            return getValidString(spouse_name);
        }

        public void setSpouse_name(String spouse_name) {
            this.spouse_name = spouse_name;
        }

        public String getApplicant_dob() {
            return getValidString(applicant_dob);
        }

        public void setApplicant_dob(String applicant_dob) {
            this.applicant_dob = applicant_dob;
        }

        public String getApplicant_aadhar_no() {
            return getValidString(applicant_aadhar_no);
        }

        public void setApplicant_aadhar_no(String applicant_aadhar_no) {
            this.applicant_aadhar_no = applicant_aadhar_no;
        }

        public String getApplicant_aadhar_image() {
            return getValidString(applicant_aadhar_image);
        }

        public void setApplicant_aadhar_image(String applicant_aadhar_image) {
            this.applicant_aadhar_image = applicant_aadhar_image;
        }

        public String getApplicant_id_number() {
            return getValidString(applicant_id_number);
        }

        public void setApplicant_id_number(String applicant_id_number) {
            this.applicant_id_number = applicant_id_number;
        }

        public String getApplicant_id_image() {
            return getValidString(applicant_id_image);
        }

        public void setApplicant_id_image(String applicant_id_image) {
            this.applicant_id_image = applicant_id_image;
        }

        public String getApplicant_mobile1() {
            return getValidString(applicant_mobile1);
        }

        public void setApplicant_mobile1(String applicant_mobile1) {
            this.applicant_mobile1 = applicant_mobile1;
        }

        public String getApplicant_mobile2() {
            return getValidString(applicant_mobile2);
        }

        public void setApplicant_mobile2(String applicant_mobile2) {
            this.applicant_mobile2 = applicant_mobile2;
        }

        public String getApplicant_address() {
            return getValidString(applicant_address);
        }

        public void setApplicant_address(String applicant_address) {
            this.applicant_address = applicant_address;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAdded_form() {
            return getValidString(added_form);
        }

        public void setAdded_form(String added_form) {
            this.added_form = added_form;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
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

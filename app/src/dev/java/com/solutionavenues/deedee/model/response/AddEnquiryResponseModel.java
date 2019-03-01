package com.solutionavenues.deedee.model.response;

/**
 * Created by Azher on 23/6/18.
 */
public class AddEnquiryResponseModel extends BaseWebResponseModel {

    /**
     * data : {"user_id":"1","applicant_name":"kumar","applicant_father_name":"rajesh","applicant_mother_name":"laila","spouse_name":"jatin","applicant_dob":"2021-04-15T00:00:00+00:00","applicant_aadhar_no":"8465132132122","applicant_id_number":"843513548748","applicant_mobile1":"846535131","applicant_mobile2":"846513133","applicant_address":"afads","added_form":"adarsh Colony Bagru jaipur","applicant_aadhar_image":"","applicant_id_image":"","created":"2018-06-23T10:37:35+00:00","modified":"2018-06-23T10:37:35+00:00","id":8}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 1
         * applicant_name : kumar
         * applicant_father_name : rajesh
         * applicant_mother_name : laila
         * spouse_name : jatin
         * applicant_dob : 2021-04-15T00:00:00+00:00
         * applicant_aadhar_no : 8465132132122
         * applicant_id_number : 843513548748
         * applicant_mobile1 : 846535131
         * applicant_mobile2 : 846513133
         * applicant_address : afads
         * added_form : adarsh Colony Bagru jaipur
         * applicant_aadhar_image :
         * applicant_id_image :
         * created : 2018-06-23T10:37:35+00:00
         * modified : 2018-06-23T10:37:35+00:00
         * id : 8
         */

        private String user_id;
        private String applicant_name;
        private String applicant_father_name;
        private String applicant_mother_name;
        private String spouse_name;
        private String applicant_dob;
        private String applicant_aadhar_no;
        private String applicant_id_number;
        private String applicant_mobile1;
        private String applicant_mobile2;
        private String applicant_address;
        private String added_form;
        private String applicant_aadhar_image;
        private String applicant_id_image;
        private String created;
        private String modified;
        private int id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getApplicant_name() {
            return applicant_name;
        }

        public void setApplicant_name(String applicant_name) {
            this.applicant_name = applicant_name;
        }

        public String getApplicant_father_name() {
            return applicant_father_name;
        }

        public void setApplicant_father_name(String applicant_father_name) {
            this.applicant_father_name = applicant_father_name;
        }

        public String getApplicant_mother_name() {
            return applicant_mother_name;
        }

        public void setApplicant_mother_name(String applicant_mother_name) {
            this.applicant_mother_name = applicant_mother_name;
        }

        public String getSpouse_name() {
            return spouse_name;
        }

        public void setSpouse_name(String spouse_name) {
            this.spouse_name = spouse_name;
        }

        public String getApplicant_dob() {
            return applicant_dob;
        }

        public void setApplicant_dob(String applicant_dob) {
            this.applicant_dob = applicant_dob;
        }

        public String getApplicant_aadhar_no() {
            return applicant_aadhar_no;
        }

        public void setApplicant_aadhar_no(String applicant_aadhar_no) {
            this.applicant_aadhar_no = applicant_aadhar_no;
        }

        public String getApplicant_id_number() {
            return applicant_id_number;
        }

        public void setApplicant_id_number(String applicant_id_number) {
            this.applicant_id_number = applicant_id_number;
        }

        public String getApplicant_mobile1() {
            return applicant_mobile1;
        }

        public void setApplicant_mobile1(String applicant_mobile1) {
            this.applicant_mobile1 = applicant_mobile1;
        }

        public String getApplicant_mobile2() {
            return applicant_mobile2;
        }

        public void setApplicant_mobile2(String applicant_mobile2) {
            this.applicant_mobile2 = applicant_mobile2;
        }

        public String getApplicant_address() {
            return applicant_address;
        }

        public void setApplicant_address(String applicant_address) {
            this.applicant_address = applicant_address;
        }

        public String getAdded_form() {
            return added_form;
        }

        public void setAdded_form(String added_form) {
            this.added_form = added_form;
        }

        public String getApplicant_aadhar_image() {
            return applicant_aadhar_image;
        }

        public void setApplicant_aadhar_image(String applicant_aadhar_image) {
            this.applicant_aadhar_image = applicant_aadhar_image;
        }

        public String getApplicant_id_image() {
            return applicant_id_image;
        }

        public void setApplicant_id_image(String applicant_id_image) {
            this.applicant_id_image = applicant_id_image;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

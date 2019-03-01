package com.solutionavenues.deedee.model.request;

import java.util.ArrayList;

/**
 * Created by Azher on 27/6/18.
 */
public class AddLocalityRequestModel {




    /**
     * village_name : 1
     * block_area : 2
     * tehsil : 3
     * police_station : 4
     * post_office : 5
     * district : 6
     * state : 7
     * branche_detail : {"kaccha_ghar":"8","pakka_ghar":"9","comu_gen":"10","comu_obc":"11","comu_sc":"12","comu_st":"13","comu_other":"14","distance_frm_branch":"15","distance_frm_main_road":"16","branch_name_nov":"17","distance_frm_nov":"18","road_connectivity":"0","private_school_count":"19","govt_school_count":"20","school_distance":"21","private_hospital_count":"22","govt_hospital_count":"23","hospital_distance":"24","village_credit_history":"25","political_parties_count":"26","livelihood_activity":"27","main_crops":"28","law_order_postion":"41","remark_survey_person":"42","date":"2018-06-27","place":"43","survey_person_name":"44","approval_autority_name":"45"}
     * mfi_operation_area : [{"mfi_name":"29","year_of_operations":"30","total_customers":"31","repayment":"32"},{"mfi_name":"33","year_of_operations":"34","total_customers":"35","repayment":"36"}]
     * micro_enterprise : [{"shop_type":"Kirana & General Stores","num_of_shop":"37"},{"shop_type":"0","num_of_shop":"38"}]
     * other_micro_enterprise : [{"shop_type":"Handloom Weavers","num_of_shop":"39"},{"shop_type":"1","num_of_shop":"40"}]
     */

    private String user_id;
    private String village_name;
    private String block_area;
    private String tehsil;
    private String police_station;
    private String post_office;
    private String district;
    private String state;
    private BrancheDetailBean branche_detail;
    private ArrayList<MfiOperationAreaBean> mfi_operation_area;
    private ArrayList<MicroEnterpriseBean> micro_enterprise;
    private ArrayList<OtherMicroEnterpriseBean> other_micro_enterprise;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getBlock_area() {
        return block_area;
    }

    public void setBlock_area(String block_area) {
        this.block_area = block_area;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getPolice_station() {
        return police_station;
    }

    public void setPolice_station(String police_station) {
        this.police_station = police_station;
    }

    public String getPost_office() {
        return post_office;
    }

    public void setPost_office(String post_office) {
        this.post_office = post_office;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BrancheDetailBean getBranche_detail() {
        return branche_detail;
    }

    public void setBranche_detail(BrancheDetailBean branche_detail) {
        this.branche_detail = branche_detail;
    }

    public ArrayList<MfiOperationAreaBean> getMfi_operation_area() {
        return mfi_operation_area;
    }

    public void setMfi_operation_area(ArrayList<MfiOperationAreaBean> mfi_operation_area) {
        this.mfi_operation_area = mfi_operation_area;
    }

    public ArrayList<MicroEnterpriseBean> getMicro_enterprise() {
        return micro_enterprise;
    }

    public void setMicro_enterprise(ArrayList<MicroEnterpriseBean> micro_enterprise) {
        this.micro_enterprise = micro_enterprise;
    }

    public ArrayList<OtherMicroEnterpriseBean> getOther_micro_enterprise() {
        return other_micro_enterprise;
    }

    public void setOther_micro_enterprise(ArrayList<OtherMicroEnterpriseBean> other_micro_enterprise) {
        this.other_micro_enterprise = other_micro_enterprise;
    }

    public static class BrancheDetailBean {
        /**
         * kaccha_ghar : 8
         * pakka_ghar : 9
         * comu_gen : 10
         * comu_obc : 11
         * comu_sc : 12
         * comu_st : 13
         * comu_other : 14
         * distance_frm_branch : 15
         * distance_frm_main_road : 16
         * branch_name_nov : 17
         * distance_frm_nov : 18
         * road_connectivity : 0
         * private_school_count : 19
         * govt_school_count : 20
         * school_distance : 21
         * private_hospital_count : 22
         * govt_hospital_count : 23
         * hospital_distance : 24
         * village_credit_history : 25
         * political_parties_count : 26
         * livelihood_activity : 27
         * main_crops : 28
         * law_order_postion : 41
         * remark_survey_person : 42
         * date : 2018-06-27
         * place : 43
         * survey_person_name : 44
         * approval_autority_name : 45
         */

        private String kaccha_ghar;
        private String pakka_ghar;
        private String comu_gen;
        private String comu_obc;
        private String comu_sc;
        private String comu_st;
        private String comu_other;
        private String distance_frm_branch;
        private String distance_frm_main_road;
        private String branch_name_nov;
        private String distance_frm_nov;
        private String road_connectivity;
        private String private_school_count;
        private String govt_school_count;
        private String school_distance;
        private String private_hospital_count;
        private String govt_hospital_count;
        private String hospital_distance;
        private String village_credit_history;
        private String political_parties_count;
        private String livelihood_activity;
        private String main_crops;
        private String law_order_postion;
        private String remark_survey_person;
        private String date;
        private String place;
        private String survey_person_name;
        private String approval_autority_name;

        public String getKaccha_ghar() {
            return kaccha_ghar;
        }

        public void setKaccha_ghar(String kaccha_ghar) {
            this.kaccha_ghar = kaccha_ghar;
        }

        public String getPakka_ghar() {
            return pakka_ghar;
        }

        public void setPakka_ghar(String pakka_ghar) {
            this.pakka_ghar = pakka_ghar;
        }

        public String getComu_gen() {
            return comu_gen;
        }

        public void setComu_gen(String comu_gen) {
            this.comu_gen = comu_gen;
        }

        public String getComu_obc() {
            return comu_obc;
        }

        public void setComu_obc(String comu_obc) {
            this.comu_obc = comu_obc;
        }

        public String getComu_sc() {
            return comu_sc;
        }

        public void setComu_sc(String comu_sc) {
            this.comu_sc = comu_sc;
        }

        public String getComu_st() {
            return comu_st;
        }

        public void setComu_st(String comu_st) {
            this.comu_st = comu_st;
        }

        public String getComu_other() {
            return comu_other;
        }

        public void setComu_other(String comu_other) {
            this.comu_other = comu_other;
        }

        public String getDistance_frm_branch() {
            return distance_frm_branch;
        }

        public void setDistance_frm_branch(String distance_frm_branch) {
            this.distance_frm_branch = distance_frm_branch;
        }

        public String getDistance_frm_main_road() {
            return distance_frm_main_road;
        }

        public void setDistance_frm_main_road(String distance_frm_main_road) {
            this.distance_frm_main_road = distance_frm_main_road;
        }

        public String getBranch_name_nov() {
            return branch_name_nov;
        }

        public void setBranch_name_nov(String branch_name_nov) {
            this.branch_name_nov = branch_name_nov;
        }

        public String getDistance_frm_nov() {
            return distance_frm_nov;
        }

        public void setDistance_frm_nov(String distance_frm_nov) {
            this.distance_frm_nov = distance_frm_nov;
        }

        public String getRoad_connectivity() {
            return road_connectivity;
        }

        public void setRoad_connectivity(String road_connectivity) {
            this.road_connectivity = road_connectivity;
        }

        public String getPrivate_school_count() {
            return private_school_count;
        }

        public void setPrivate_school_count(String private_school_count) {
            this.private_school_count = private_school_count;
        }

        public String getGovt_school_count() {
            return govt_school_count;
        }

        public void setGovt_school_count(String govt_school_count) {
            this.govt_school_count = govt_school_count;
        }

        public String getSchool_distance() {
            return school_distance;
        }

        public void setSchool_distance(String school_distance) {
            this.school_distance = school_distance;
        }

        public String getPrivate_hospital_count() {
            return private_hospital_count;
        }

        public void setPrivate_hospital_count(String private_hospital_count) {
            this.private_hospital_count = private_hospital_count;
        }

        public String getGovt_hospital_count() {
            return govt_hospital_count;
        }

        public void setGovt_hospital_count(String govt_hospital_count) {
            this.govt_hospital_count = govt_hospital_count;
        }

        public String getHospital_distance() {
            return hospital_distance;
        }

        public void setHospital_distance(String hospital_distance) {
            this.hospital_distance = hospital_distance;
        }

        public String getVillage_credit_history() {
            return village_credit_history;
        }

        public void setVillage_credit_history(String village_credit_history) {
            this.village_credit_history = village_credit_history;
        }

        public String getPolitical_parties_count() {
            return political_parties_count;
        }

        public void setPolitical_parties_count(String political_parties_count) {
            this.political_parties_count = political_parties_count;
        }

        public String getLivelihood_activity() {
            return livelihood_activity;
        }

        public void setLivelihood_activity(String livelihood_activity) {
            this.livelihood_activity = livelihood_activity;
        }

        public String getMain_crops() {
            return main_crops;
        }

        public void setMain_crops(String main_crops) {
            this.main_crops = main_crops;
        }

        public String getLaw_order_postion() {
            return law_order_postion;
        }

        public void setLaw_order_postion(String law_order_postion) {
            this.law_order_postion = law_order_postion;
        }

        public String getRemark_survey_person() {
            return remark_survey_person;
        }

        public void setRemark_survey_person(String remark_survey_person) {
            this.remark_survey_person = remark_survey_person;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getSurvey_person_name() {
            return survey_person_name;
        }

        public void setSurvey_person_name(String survey_person_name) {
            this.survey_person_name = survey_person_name;
        }

        public String getApproval_autority_name() {
            return approval_autority_name;
        }

        public void setApproval_autority_name(String approval_autority_name) {
            this.approval_autority_name = approval_autority_name;
        }
    }

    public static class MfiOperationAreaBean {
        /**
         * mfi_name : 29
         * year_of_operations : 30
         * total_customers : 31
         * repayment : 32
         */

        private String mfi_name;
        private String year_of_operations;
        private String total_customers;
        private String repayment;

        public String getMfi_name() {
            return mfi_name;
        }

        public void setMfi_name(String mfi_name) {
            this.mfi_name = mfi_name;
        }

        public String getYear_of_operations() {
            return year_of_operations;
        }

        public void setYear_of_operations(String year_of_operations) {
            this.year_of_operations = year_of_operations;
        }

        public String getTotal_customers() {
            return total_customers;
        }

        public void setTotal_customers(String total_customers) {
            this.total_customers = total_customers;
        }

        public String getRepayment() {
            return repayment;
        }

        public void setRepayment(String repayment) {
            this.repayment = repayment;
        }
    }

    public static class MicroEnterpriseBean {
        /**
         * shop_type : Kirana & General Stores
         * num_of_shop : 37
         */

        private String shop_type;
        private String num_of_shop;

        public String getShop_type() {
            return shop_type;
        }

        public void setShop_type(String shop_type) {
            this.shop_type = shop_type;
        }

        public String getNum_of_shop() {
            return num_of_shop;
        }

        public void setNum_of_shop(String num_of_shop) {
            this.num_of_shop = num_of_shop;
        }
    }

    public static class OtherMicroEnterpriseBean {
        /**
         * shop_type : Handloom Weavers
         * num_of_shop : 39
         */

        private String shop_type;
        private String num_of_shop;

        public String getShop_type() {
            return shop_type;
        }

        public void setShop_type(String shop_type) {
            this.shop_type = shop_type;
        }

        public String getNum_of_shop() {
            return num_of_shop;
        }

        public void setNum_of_shop(String num_of_shop) {
            this.num_of_shop = num_of_shop;
        }
    }
}

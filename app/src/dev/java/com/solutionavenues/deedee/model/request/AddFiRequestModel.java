package com.solutionavenues.deedee.model.request;

import java.util.List;

/**
 * Created by Azher on 16/7/18.
 */
public class AddFiRequestModel {

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public boolean isLocalFi() {
        return isLocalFi;
    }

    public void setLocalFi(boolean LocalFi) {
        isLocalFi = LocalFi;
    }

    /**
     * id : 13
     * date : 17/07/2018
     * name_of_officer : abc
     * application_no : 123
     * name_of_applicant : df
     * customer_type : New
     * name_of_father : Leander Daniel
     * name_of_husband : Leander Daniel
     * residential_address : Vt Road
     * name_of_city : Jaipur
     * pincode : 302012
     * house_type : Owned
     * living_since : 2011
     * mobile : 09950531055
     * name_of_landlord : sfda
     * landlord_mobile : 09024756473
     * rent_amount : 123456
     * rent_agreement_available : No
     * is_rent_paid_timely : No
     * purpose_of_loan : dsfgdsgfds
     * loan_amount : 23000
     * current_address : 5
     * adult : 2
     * child : 1
     * dependent : 2
     * source_of_income_a : 2
     * source_of_income_b : 2
     * source_of_income_c : 23
     * source_of_income_d : 23
     * source_of_income_e : 32
     * source_of_income_f : 32
     * source_of_income_g : 43
     * source_of_income_h : 54
     * child_school_name : dafdsfaafd
     * child_school_phone : 34
     * child_school_address : Plot No. 32, Block-A, Genesis, Mohan Co-operative Industrial Estate
     * native_address : Plot No. 32, Block-A, Genesis, Mohan Co-operative Industrial Estate
     * native_village : sadf
     * native_district : asdf
     * native_state : asdf
     * native_pincode : 2424234
     * native_living_since : 3424
     * native_phone1 : 09874563214
     * native_phone2 : 09024756473
     * name_of_co_applicant : sadfasf
     * co_applicant_relation : asdf
     * co_applicant_mobile : 09024756473
     * fie_appraisal : {"appraisal_a":"234","appraisal_b":"234","appraisal_c":"234","appraisal_d":"234","appraisal_e":"234","appraisal_f":"234","appraisal_g":"234","appraisal_h":"234"}
     * fie_compliance : {"compliance_a":0,"compliance_b":0,"compliance_c":0}
     * fie_risk_evaluation : {"risk_a":0,"risk_b":0,"risk_c":0,"risk_d":0,"risk_e":0,"risk_f":0,"risk_g":0,"risk_h":0}
     * fie_feedback : {"applicant_signature":"1746368950_947c239e78aca5cee9d744a5016076b5_(1).jpg","signature":"1728622123_Untitled.jpg","group_pic":"81552398_947c239e78aca5cee9d744a5016076b5_(2).jpg","name1":"234asdf","feedback1":"asfd","address1":"sadf","remark1":"Average","name2":"sfd","feedback2":"sadf","address2":"asf","remark2":"Average","name_officer":"asdf","comments":"asdf"}
     * fie_loan_details : [{"co_name":"234","loan_amount":"234","tenure":"234","emi_amount":"234","installment_due":"234"}]
     * user_id : 1
     */

    private boolean isLocalFi=false;
    private String local_id;
    private String id;
    private String lead_id;
    private String date;
    private String name_of_officer;
    private String application_no;
    private String name_of_applicant;
    private String k_number;
    private String customer_type;
    private String name_of_father;
    private String name_of_husband;
    private String residential_address;
    private String name_of_city;
    private String pincode;
    private String house_type;
    private String living_since;
    private String mobile;
    private String living_phone_2;
    private String name_of_landlord;
    private String landlord_mobile;
    private String rent_amount;
    private String rent_agreement_available;
    private String is_rent_paid_timely;
    private String purpose_of_loan;
    private String loan_amount;
    private String current_address;
    private String adult;
    private String number_of_family_member;
    private String child;
    private String dependent;
    private String child_school_name;
    private String child_school_phone;
    private String child_school_address;
    private String native_address;
    private String native_village;
    private String native_district;
    private String native_state;
    private String native_pincode;
    private String native_living_since;
    private String native_phone1;
    private String native_phone2;
    private String name_of_co_applicant;
    private String co_applicant_relation;
    private String co_applicant_mobile;
    private FieAppraisalBean fie_appraisal;
    private FieComplianceBean fie_compliance;
    private FieRiskEvaluationBean fie_risk_evaluation;
    private FieFeedbackBean fie_feedback;
    private int user_id;
    private List<FieLoanDetailsBean> fie_loan_details;
    private String income_applicant;
    private String applicant_income_source;
    private String applicant_income_amount;
    private String income_coapplicant;
    private String coapplicant_income_source;
    private String coapplicant_income_amount;
    private String income_member1;
    private String member1_income_source;
    private String member1_income_amount;
    private String income_member2;
    private String member2_income_source;
    private String member2_income_amount;

    public String getK_number() {
        return k_number;
    }

    public void setK_number(String k_number) {
        this.k_number = k_number;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }


    public String getNumber_of_family_member() {
        return number_of_family_member;
    }

    public void setNumber_of_family_member(String number_of_family_member) {
        this.number_of_family_member = number_of_family_member;
    }

    public String getIncome_applicant() {
        return income_applicant;
    }

    public void setIncome_applicant(String income_applicant) {
        this.income_applicant = income_applicant;
    }

    public String getApplicant_income_source() {
        return applicant_income_source;
    }

    public void setApplicant_income_source(String applicant_income_source) {
        this.applicant_income_source = applicant_income_source;
    }

    public String getApplicant_income_amount() {
        return applicant_income_amount;
    }

    public void setApplicant_income_amount(String applicant_income_amount) {
        this.applicant_income_amount = applicant_income_amount;
    }

    public String getIncome_coapplicant() {
        return income_coapplicant;
    }

    public void setIncome_coapplicant(String income_coapplicant) {
        this.income_coapplicant = income_coapplicant;
    }

    public String getCoapplicant_income_source() {
        return coapplicant_income_source;
    }

    public void setCoapplicant_income_source(String coapplicant_income_source) {
        this.coapplicant_income_source = coapplicant_income_source;
    }

    public String getCoapplicant_income_amount() {
        return coapplicant_income_amount;
    }

    public void setCoapplicant_income_amount(String coapplicant_income_amount) {
        this.coapplicant_income_amount = coapplicant_income_amount;
    }

    public String getIncome_member1() {
        return income_member1;
    }

    public void setIncome_member1(String income_member1) {
        this.income_member1 = income_member1;
    }

    public String getMember1_income_source() {
        return member1_income_source;
    }

    public void setMember1_income_source(String member1_income_source) {
        this.member1_income_source = member1_income_source;
    }

    public String getMember1_income_amount() {
        return member1_income_amount;
    }

    public void setMember1_income_amount(String member1_income_amount) {
        this.member1_income_amount = member1_income_amount;
    }

    public String getIncome_member2() {
        return income_member2;
    }

    public void setIncome_member2(String income_member2) {
        this.income_member2 = income_member2;
    }

    public String getMember2_income_source() {
        return member2_income_source;
    }

    public void setMember2_income_source(String member2_income_source) {
        this.member2_income_source = member2_income_source;
    }

    public String getMember2_income_amount() {
        return member2_income_amount;
    }

    public void setMember2_income_amount(String member2_income_amount) {
        this.member2_income_amount = member2_income_amount;
    }



    public String getNumber_of_member() {
        return number_of_family_member;
    }

    public void setNumber_of_member(String number_of_member) {
        this.number_of_family_member = number_of_member;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName_of_officer() {
        return name_of_officer;
    }

    public void setName_of_officer(String name_of_officer) {
        this.name_of_officer = name_of_officer;
    }

    public String getApplication_no() {
        return application_no;
    }

    public void setApplication_no(String application_no) {
        this.application_no = application_no;
    }

    public String getName_of_applicant() {
        return name_of_applicant;
    }

    public void setName_of_applicant(String name_of_applicant) {
        this.name_of_applicant = name_of_applicant;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getName_of_father() {
        return name_of_father;
    }

    public void setName_of_father(String name_of_father) {
        this.name_of_father = name_of_father;
    }

    public String getName_of_husband() {
        return name_of_husband;
    }

    public void setName_of_husband(String name_of_husband) {
        this.name_of_husband = name_of_husband;
    }

    public String getResidential_address() {
        return residential_address;
    }

    public void setResidential_address(String residential_address) {
        this.residential_address = residential_address;
    }

    public String getName_of_city() {
        return name_of_city;
    }

    public void setName_of_city(String name_of_city) {
        this.name_of_city = name_of_city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public String getLiving_since() {
        return living_since;
    }

    public void setLiving_since(String living_since) {
        this.living_since = living_since;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLiving_phone_2() {
        return living_phone_2;
    }

    public void setLiving_phone_2(String living_phone_2) {
        this.living_phone_2 = living_phone_2;
    }

    public String getName_of_landlord() {
        return name_of_landlord;
    }

    public void setName_of_landlord(String name_of_landlord) {
        this.name_of_landlord = name_of_landlord;
    }

    public String getLandlord_mobile() {
        return landlord_mobile;
    }

    public void setLandlord_mobile(String landlord_mobile) {
        this.landlord_mobile = landlord_mobile;
    }

    public String getRent_amount() {
        return rent_amount;
    }

    public void setRent_amount(String rent_amount) {
        this.rent_amount = rent_amount;
    }

    public String getRent_agreement_available() {
        return rent_agreement_available;
    }

    public void setRent_agreement_available(String rent_agreement_available) {
        this.rent_agreement_available = rent_agreement_available;
    }

    public String getIs_rent_paid_timely() {
        return is_rent_paid_timely;
    }

    public void setIs_rent_paid_timely(String is_rent_paid_timely) {
        this.is_rent_paid_timely = is_rent_paid_timely;
    }

    public String getPurpose_of_loan() {
        return purpose_of_loan;
    }

    public void setPurpose_of_loan(String purpose_of_loan) {
        this.purpose_of_loan = purpose_of_loan;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getDependent() {
        return dependent;
    }

    public void setDependent(String dependent) {
        this.dependent = dependent;
    }



    public String getChild_school_name() {
        return child_school_name;
    }

    public void setChild_school_name(String child_school_name) {
        this.child_school_name = child_school_name;
    }

    public String getChild_school_phone() {
        return child_school_phone;
    }

    public void setChild_school_phone(String child_school_phone) {
        this.child_school_phone = child_school_phone;
    }

    public String getChild_school_address() {
        return child_school_address;
    }

    public void setChild_school_address(String child_school_address) {
        this.child_school_address = child_school_address;
    }

    public String getNative_address() {
        return native_address;
    }

    public void setNative_address(String native_address) {
        this.native_address = native_address;
    }

    public String getNative_village() {
        return native_village;
    }

    public void setNative_village(String native_village) {
        this.native_village = native_village;
    }

    public String getNative_district() {
        return native_district;
    }

    public void setNative_district(String native_district) {
        this.native_district = native_district;
    }

    public String getNative_state() {
        return native_state;
    }

    public void setNative_state(String native_state) {
        this.native_state = native_state;
    }

    public String getNative_pincode() {
        return native_pincode;
    }

    public void setNative_pincode(String native_pincode) {
        this.native_pincode = native_pincode;
    }

    public String getNative_living_since() {
        return native_living_since;
    }

    public void setNative_living_since(String native_living_since) {
        this.native_living_since = native_living_since;
    }

    public String getNative_phone1() {
        return native_phone1;
    }

    public void setNative_phone1(String native_phone1) {
        this.native_phone1 = native_phone1;
    }

    public String getNative_phone2() {
        return native_phone2;
    }

    public void setNative_phone2(String native_phone2) {
        this.native_phone2 = native_phone2;
    }

    public String getName_of_co_applicant() {
        return name_of_co_applicant;
    }

    public void setName_of_co_applicant(String name_of_co_applicant) {
        this.name_of_co_applicant = name_of_co_applicant;
    }

    public String getCo_applicant_relation() {
        return co_applicant_relation;
    }

    public void setCo_applicant_relation(String co_applicant_relation) {
        this.co_applicant_relation = co_applicant_relation;
    }

    public String getCo_applicant_mobile() {
        return co_applicant_mobile;
    }

    public void setCo_applicant_mobile(String co_applicant_mobile) {
        this.co_applicant_mobile = co_applicant_mobile;
    }

    public FieAppraisalBean getFie_appraisal() {
        return fie_appraisal;
    }

    public void setFie_appraisal(FieAppraisalBean fie_appraisal) {
        this.fie_appraisal = fie_appraisal;
    }

    public FieComplianceBean getFie_compliance() {
        return fie_compliance;
    }

    public void setFie_compliance(FieComplianceBean fie_compliance) {
        this.fie_compliance = fie_compliance;
    }

    public FieRiskEvaluationBean getFie_risk_evaluation() {
        return fie_risk_evaluation;
    }

    public void setFie_risk_evaluation(FieRiskEvaluationBean fie_risk_evaluation) {
        this.fie_risk_evaluation = fie_risk_evaluation;
    }

    public FieFeedbackBean getFie_feedback() {
        return fie_feedback;
    }

    public void setFie_feedback(FieFeedbackBean fie_feedback) {
        this.fie_feedback = fie_feedback;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<FieLoanDetailsBean> getFie_loan_details() {
        return fie_loan_details;
    }

    public void setFie_loan_details(List<FieLoanDetailsBean> fie_loan_details) {
        this.fie_loan_details = fie_loan_details;
    }

    public static class FieAppraisalBean {
        /**
         * appraisal_a : 234
         * appraisal_b : 234
         * appraisal_c : 234
         * appraisal_d : 234
         * appraisal_e : 234
         * appraisal_f : 234
         * appraisal_g : 234
         * appraisal_h : 234
         */

        private String appraisal_a;
        private String appraisal_b;
        private String appraisal_c;
        private String appraisal_d;
        private String appraisal_e;
        private String appraisal_f;
        private String appraisal_g;
        private String appraisal_h;

        public String getAppraisal_a() {
            return appraisal_a;
        }

        public void setAppraisal_a(String appraisal_a) {
            this.appraisal_a = appraisal_a;
        }

        public String getAppraisal_b() {
            return appraisal_b;
        }

        public void setAppraisal_b(String appraisal_b) {
            this.appraisal_b = appraisal_b;
        }

        public String getAppraisal_c() {
            return appraisal_c;
        }

        public void setAppraisal_c(String appraisal_c) {
            this.appraisal_c = appraisal_c;
        }

        public String getAppraisal_d() {
            return appraisal_d;
        }

        public void setAppraisal_d(String appraisal_d) {
            this.appraisal_d = appraisal_d;
        }

        public String getAppraisal_e() {
            return appraisal_e;
        }

        public void setAppraisal_e(String appraisal_e) {
            this.appraisal_e = appraisal_e;
        }

        public String getAppraisal_f() {
            return appraisal_f;
        }

        public void setAppraisal_f(String appraisal_f) {
            this.appraisal_f = appraisal_f;
        }

        public String getAppraisal_g() {
            return appraisal_g;
        }

        public void setAppraisal_g(String appraisal_g) {
            this.appraisal_g = appraisal_g;
        }

        public String getAppraisal_h() {
            return appraisal_h;
        }

        public void setAppraisal_h(String appraisal_h) {
            this.appraisal_h = appraisal_h;
        }
    }

    public static class FieComplianceBean {
        /**
         * compliance_a : 0
         * compliance_b : 0
         * compliance_c : 0
         */

        private int compliance_a;
        private int compliance_b;
        private int compliance_c;

        public int getCompliance_a() {
            return compliance_a;
        }

        public void setCompliance_a(int compliance_a) {
            this.compliance_a = compliance_a;
        }

        public int getCompliance_b() {
            return compliance_b;
        }

        public void setCompliance_b(int compliance_b) {
            this.compliance_b = compliance_b;
        }

        public int getCompliance_c() {
            return compliance_c;
        }

        public void setCompliance_c(int compliance_c) {
            this.compliance_c = compliance_c;
        }
    }

    public static class FieRiskEvaluationBean {
        /**
         * risk_a : 0
         * risk_b : 0
         * risk_c : 0
         * risk_d : 0
         * risk_e : 0
         * risk_f : 0
         * risk_g : 0
         * risk_h : 0
         */

        private int risk_a;
        private int risk_b;
        private int risk_c;
        private int risk_d;
        private int risk_e;
        private int risk_f;
        private int risk_g;
        private int risk_h;

        public int getRisk_a() {
            return risk_a;
        }

        public void setRisk_a(int risk_a) {
            this.risk_a = risk_a;
        }

        public int getRisk_b() {
            return risk_b;
        }

        public void setRisk_b(int risk_b) {
            this.risk_b = risk_b;
        }

        public int getRisk_c() {
            return risk_c;
        }

        public void setRisk_c(int risk_c) {
            this.risk_c = risk_c;
        }

        public int getRisk_d() {
            return risk_d;
        }

        public void setRisk_d(int risk_d) {
            this.risk_d = risk_d;
        }

        public int getRisk_e() {
            return risk_e;
        }

        public void setRisk_e(int risk_e) {
            this.risk_e = risk_e;
        }

        public int getRisk_f() {
            return risk_f;
        }

        public void setRisk_f(int risk_f) {
            this.risk_f = risk_f;
        }

        public int getRisk_g() {
            return risk_g;
        }

        public void setRisk_g(int risk_g) {
            this.risk_g = risk_g;
        }

        public int getRisk_h() {
            return risk_h;
        }

        public void setRisk_h(int risk_h) {
            this.risk_h = risk_h;
        }
    }

    public static class FieFeedbackBean {
        /**
         * applicant_signature : 1746368950_947c239e78aca5cee9d744a5016076b5_(1).jpg
         * signature : 1728622123_Untitled.jpg
         * group_pic : 81552398_947c239e78aca5cee9d744a5016076b5_(2).jpg
         * name1 : 234asdf
         * feedback1 : asfd
         * address1 : sadf
         * remark1 : Average
         * name2 : sfd
         * feedback2 : sadf
         * address2 : asf
         * remark2 : Average
         * name_officer : asdf
         * comments : asdf
         */

        private String applicant_signature;
        private String signature;
        private String group_pic;
        private String name1;
        private String feedback1;
        private String address1;
        private String remark1;
        private String utility_bill;
        private String utility_relation;
        private String cheque;
        private String name2;
        private String feedback2;
        private String address2;
        private String remark2;
        private String name_officer;
        private String comments;

        public String getApplicant_signature() {
            return applicant_signature;
        }

        public void setApplicant_signature(String applicant_signature) {
            this.applicant_signature = applicant_signature;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getGroup_pic() {
            return group_pic;
        }

        public void setGroup_pic(String group_pic) {
            this.group_pic = group_pic;
        }

        public String getName1() {
            return name1;
        }

        public void setName1(String name1) {
            this.name1 = name1;
        }

        public String getFeedback1() {
            return feedback1;
        }

        public void setFeedback1(String feedback1) {
            this.feedback1 = feedback1;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getRemark1() {
            return remark1;
        }

        public void setRemark1(String remark1) {
            this.remark1 = remark1;
        }

        public String getUtility_bill() {
            return utility_bill;
        }

        public void setUtility_bill(String utility_bill) {
            this.utility_bill = utility_bill;
        }

        public String getUtility_relation() {
            return utility_relation;
        }

        public void setUtility_relation(String utility_relation) {
            this.utility_relation = utility_relation;
        }

        public String getCheque() {
            return cheque;
        }

        public void setCheque(String cheque) {
            this.cheque = cheque;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getFeedback2() {
            return feedback2;
        }

        public void setFeedback2(String feedback2) {
            this.feedback2 = feedback2;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getRemark2() {
            return remark2;
        }

        public void setRemark2(String remark2) {
            this.remark2 = remark2;
        }

        public String getName_officer() {
            return name_officer;
        }

        public void setName_officer(String name_officer) {
            this.name_officer = name_officer;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }

    public static class FieLoanDetailsBean {
        /**
         * co_name : 234
         * loan_amount : 234
         * tenure : 234
         * emi_amount : 234
         * installment_due : 234
         */

        private String co_name;
        private String loan_amount;
        private String tenure;
        private String emi_amount;
        private String installment_due;

        public String getCo_name() {
            return co_name;
        }

        public void setCo_name(String co_name) {
            this.co_name = co_name;
        }

        public String getLoan_amount() {
            return loan_amount;
        }

        public void setLoan_amount(String loan_amount) {
            this.loan_amount = loan_amount;
        }

        public String getTenure() {
            return tenure;
        }

        public void setTenure(String tenure) {
            this.tenure = tenure;
        }

        public String getEmi_amount() {
            return emi_amount;
        }

        public void setEmi_amount(String emi_amount) {
            this.emi_amount = emi_amount;
        }

        public String getInstallment_due() {
            return installment_due;
        }

        public void setInstallment_due(String installment_due) {
            this.installment_due = installment_due;
        }
    }
}

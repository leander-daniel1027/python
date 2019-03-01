package com.solutionavenues.deedee.model.request;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Azher on 2/7/18.
 */
public class AddLeadRequestModel extends BaseWebRequestModel implements Serializable {

    public String local_id;

    public String getEnquery_id() {
        return enquery_id;
    }

    public void setEnquery_id(String enquery_id) {
        this.enquery_id = enquery_id;
    }

    public String enquery_id;
    public String lead_id;
    public String status;
    public boolean shouldUpdate;
    public LoanPersonelInfo loanPersonelInfo;
    public AddressInfo addressInfo;
    public FamilyProfile familyProfile;
    public MonthlyHouseHoldInfo houseHoldInfo;
    public MonthlySavingsInfo savingsInfo;
    public ArrayList<ExistingLoanInfo> existingLoanInfoList;
    public ArrayList<KycInfo> kycInfoList;
    public ArrayList<ChequeInfo> chequeInfo;

    public boolean isShouldUpdate() {
        return shouldUpdate;
    }

    public void setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }

    public boolean isLocalLead() {
        return isLocalLead;
    }

    public void setLocalLead(boolean localLead) {
        isLocalLead = localLead;
    }

    public boolean isLocalLead = false;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public DeclarationInfo getDeclarationInfo() {
        return declarationInfo;
    }

    public void setDeclarationInfo(DeclarationInfo declarationInfo) {
        this.declarationInfo = declarationInfo;
    }

    public DeclarationInfo declarationInfo;


    public FamilyProfile getFamilyProfile() {
        return familyProfile;
    }

    public void setFamilyProfile(FamilyProfile familyProfile) {
        this.familyProfile = familyProfile;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }


    public MonthlyHouseHoldInfo getHouseHoldInfo() {
        return houseHoldInfo;
    }

    public void setHouseHoldInfo(MonthlyHouseHoldInfo houseHoldInfo) {
        this.houseHoldInfo = houseHoldInfo;
    }

    public MonthlySavingsInfo getSavingsInfo() {
        return savingsInfo;
    }

    public void setSavingsInfo(MonthlySavingsInfo savingsInfo) {
        this.savingsInfo = savingsInfo;
    }

    public ArrayList<ExistingLoanInfo> getExistingLoanInfoList() {
        return existingLoanInfoList;
    }

    public void setExistingLoanInfoList(ArrayList<ExistingLoanInfo> existingLoanInfoList) {
        this.existingLoanInfoList = existingLoanInfoList;
    }

    public ArrayList<KycInfo> getKycInfoList() {
        return kycInfoList;
    }

    public void setKycInfoList(ArrayList<KycInfo> kycInfoList) {
        this.kycInfoList = kycInfoList;
    }

    public ArrayList<ChequeInfo> getChequeInfo() {
        return chequeInfo;
    }

    public void setChequeInfo(ArrayList<ChequeInfo> chequeInfo) {
        this.chequeInfo = chequeInfo;
    }

    public LoanPersonelInfo getLoanPersonelInfo() {
        return loanPersonelInfo;
    }

    public void setLoanPersonelInfo(LoanPersonelInfo loanPersonelInfo) {
        this.loanPersonelInfo = loanPersonelInfo;
    }


    public static class LoanPersonelInfo implements Serializable {
        public String user_id;
        public String latitude;
        public String longitude;
        public String branche_id;
        public String branche_id_value;
        public String payment_cycle;
        public String payment_cycle_value;
        public String loan_purpose;
        public String loan_amount;
        public String emp_name;
        public String spouse_name;
        public String father_name;
        public String mother_name;
        public String dob;
        public String religion;
        public String enquiry_id;
        public String cibil_status;
        public String co_aplicant_relation;
        public String co_aplicant_age;

        public String getCo_aplicant_relation() {
            return co_aplicant_relation;
        }

        public void setCo_aplicant_relation(String co_aplicant_relation) {
            this.co_aplicant_relation = co_aplicant_relation;
        }

        public String getCo_aplicant_age() {
            return co_aplicant_age;
        }

        public void setCo_aplicant_age(String co_aplicant_age) {
            this.co_aplicant_age = co_aplicant_age;
        }

        public String getEnquiry_id() {
            return enquiry_id;
        }

        public void setEnquiry_id(String enquiry_id) {
            this.enquiry_id = enquiry_id;
        }

        public String getCibil_status() {
            return cibil_status;
        }

        public void setCibil_status(String cibil_status) {
            this.cibil_status = cibil_status;
        }

        public String getCurrent_address_area() {
            return current_address_area;
        }

        public void setCurrent_address_area(String current_address_area) {
            this.current_address_area = current_address_area;
        }

        public String current_address_area;
        public String religion_value;
        public String marital_status;
        public String marital_status_value;
        public String education;
        public String applicant_photo1;
        public String applicant_photo2;
        public String group_id;
        public String group_id_value;
        public String product_id;
        public String product_id_value;
        public String customer_id;
        public String date;
        public String type_of_customer;
        public String type_of_customer_value;
        public String name;
        public String co_aplicant_name;
        public String k_number;

        public String getK_number() {
            return k_number;
        }

        public void setK_number(String k_number) {
            this.k_number = k_number;
        }

        public String getBranche_id_value() {
            return branche_id_value;
        }

        public void setBranche_id_value(String branche_id_value) {
            this.branche_id_value = branche_id_value;
        }

        public String getPayment_cycle_value() {
            return payment_cycle_value;
        }

        public void setPayment_cycle_value(String payment_cycle_value) {
            this.payment_cycle_value = payment_cycle_value;
        }

        public String getReligion_value() {
            return religion_value;
        }

        public void setReligion_value(String religion_value) {
            this.religion_value = religion_value;
        }

        public String getMarital_status_value() {
            return marital_status_value;
        }

        public void setMarital_status_value(String marital_status_value) {
            this.marital_status_value = marital_status_value;
        }

        public String getGroup_id_value() {
            return group_id_value;
        }

        public void setGroup_id_value(String group_id_value) {
            this.group_id_value = group_id_value;
        }

        public String getProduct_id_value() {
            return product_id_value;
        }

        public void setProduct_id_value(String product_id_value) {
            this.product_id_value = product_id_value;
        }

        public String getType_of_customer_value() {
            return type_of_customer_value;
        }

        public void setType_of_customer_value(String type_of_customer_value) {
            this.type_of_customer_value = type_of_customer_value;
        }


        public String getApplicant_photo1() {
            return applicant_photo1;
        }

        public void setApplicant_photo1(String applicant_photo1) {
            this.applicant_photo1 = applicant_photo1;
        }

        public String getApplicant_photo2() {
            return applicant_photo2;
        }

        public void setApplicant_photo2(String applicant_photo2) {
            this.applicant_photo2 = applicant_photo2;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType_of_customer() {
            return type_of_customer;
        }

        public void setType_of_customer(String type_of_customer) {
            this.type_of_customer = type_of_customer;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCo_aplicant_name() {
            return co_aplicant_name;
        }

        public void setCo_aplicant_name(String co_aplicant_name) {
            this.co_aplicant_name = co_aplicant_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getBranche_id() {
            return branche_id;
        }

        public void setBranche_id(String branche_id) {
            this.branche_id = branche_id;
        }

        public String getPayment_cycle() {
            return payment_cycle;
        }

        public void setPayment_cycle(String payment_cycle) {
            this.payment_cycle = payment_cycle;
        }

        public String getLoan_purpose() {
            return loan_purpose;
        }

        public void setLoan_purpose(String loan_purpose) {
            this.loan_purpose = loan_purpose;
        }

        public String getLoan_amount() {
            return loan_amount;
        }

        public void setLoan_amount(String loan_amount) {
            this.loan_amount = loan_amount;
        }

        public String getEmp_name() {
            return emp_name;
        }

        public void setEmp_name(String emp_name) {
            this.emp_name = emp_name;
        }

        public String getSpouse_name() {
            return spouse_name;
        }

        public void setSpouse_name(String spouse_name) {
            this.spouse_name = spouse_name;
        }

        public String getFather_name() {
            return father_name;
        }

        public void setFather_name(String father_name) {
            this.father_name = father_name;
        }

        public String getMother_name() {
            return mother_name;
        }

        public void setMother_name(String mother_name) {
            this.mother_name = mother_name;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getMarital_status() {
            return marital_status;
        }

        public void setMarital_status(String marital_status) {
            this.marital_status = marital_status;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }


    }

    public static class AddressInfo implements Serializable {


        public String address;
        public String area;
        public String area_id;
        public String city;
        public String permanent_city;
        public String permanant_address_area;
        public String Current_address;
        public String pincode;
        public String landmark;
        public String landline_contact;
        public String mobile_contact;
        public String living_since;
        public String home_status;
        public String permanent_address_other;
        public String permanent_address;
        public String permanent_area;
        public String permanent_pincode;
        public String permanent_landmark;
        public String permanent_landline;
        public String permanent_living_since;
        public String permanent_phone;
        public String permanent_home_status;
        public String office_address;
        public String office_pincode;
        public String office_landmark;
        public String working_since;
        public String permanent_area_id;

        public String getPermanant_city() {
            return permanent_city;
        }

        public void setPermanant_city(String permanant_city) {
            this.permanent_city = permanant_city;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getPermanant_address_area() {
            return permanant_address_area;
        }

        public void setPermanant_address_area(String permanant_address_area) {
            this.permanant_address_area = permanant_address_area;
        }

        public String getCurrent_address() {
            return Current_address;
        }

        public void setCurrent_address(String current_address) {
            Current_address = current_address;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }


        public String getPermanent_area_id() {
            return permanent_area_id;
        }

        public void setPermanent_area_id(String permanent_area_id) {
            this.permanent_area_id = permanent_area_id;
        }

        public String getPermanent_landline() {
            return permanent_landline;
        }

        public void setPermanent_landline(String permanent_landline) {
            this.permanent_landline = permanent_landline;
        }

        public String getPermanent_address() {
            return permanent_address;
        }

        public void setPermanent_address(String permanent_address) {
            this.permanent_address = permanent_address;
        }

        public String getPermanent_area() {
            return permanent_area;
        }

        public void setPermanent_area(String permanent_area) {
            this.permanent_area = permanent_area;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getLandline_contact() {
            return landline_contact;
        }

        public void setLandline_contact(String landline_contact) {
            this.landline_contact = landline_contact;
        }

        public String getMobile_contact() {
            return mobile_contact;
        }

        public void setMobile_contact(String mobile_contact) {
            this.mobile_contact = mobile_contact;
        }

        public String getLiving_since() {
            return living_since;
        }

        public void setLiving_since(String living_since) {
            this.living_since = living_since;
        }

        public String getHome_status() {
            return home_status;
        }

        public void setHome_status(String home_status) {
            this.home_status = home_status;
        }

        public String getPermanent_address_other() {
            return permanent_address_other;
        }

        public void setPermanent_address_other(String permanent_address_other) {
            this.permanent_address_other = permanent_address_other;
        }

        public String getPermanent_pincode() {
            return permanent_pincode;
        }

        public void setPermanent_pincode(String permanent_pincode) {
            this.permanent_pincode = permanent_pincode;
        }

        public String getPermanent_landmark() {
            return permanent_landmark;
        }

        public void setPermanent_landmark(String permanent_landmark) {
            this.permanent_landmark = permanent_landmark;
        }

        public String getPermanent_living_since() {
            return permanent_living_since;
        }

        public void setPermanent_living_since(String permanent_living_since) {
            this.permanent_living_since = permanent_living_since;
        }

        public String getPermanent_phone() {
            return permanent_phone;
        }

        public void setPermanent_phone(String permanent_phone) {
            this.permanent_phone = permanent_phone;
        }

        public String getPermanent_home_status() {
            return permanent_home_status;
        }

        public void setPermanent_home_status(String permanent_home_status) {
            this.permanent_home_status = permanent_home_status;
        }

        public String getOffice_address() {
            return office_address;
        }

        public void setOffice_address(String office_address) {
            this.office_address = office_address;
        }

        public String getOffice_pincode() {
            return office_pincode;
        }

        public void setOffice_pincode(String office_pincode) {
            this.office_pincode = office_pincode;
        }

        public String getOffice_landmark() {
            return office_landmark;
        }

        public void setOffice_landmark(String office_landmark) {
            this.office_landmark = office_landmark;
        }

        public String getWorking_since() {
            return working_since;
        }

        public void setWorking_since(String working_since) {
            this.working_since = working_since;
        }


    }

    public static class FamilyProfile implements Serializable {
        public ArrayList<FamilyProfile.FamilyProfileInfo> familyProfileInfoList;

        public ArrayList<FamilyProfile.FamilyProfileInfo> getFamilyProfileInfoList() {
            return familyProfileInfoList;
        }

        public void setFamilyProfileInfoList(ArrayList<FamilyProfile.FamilyProfileInfo> familyProfileInfoList) {
            this.familyProfileInfoList = familyProfileInfoList;
        }

        public String getTotal_family_income() {
            return total_family_income;
        }

        public void setTotal_family_income(String total_family_income) {
            this.total_family_income = total_family_income;
        }

        public String total_family_income;

        public static class FamilyProfileInfo implements Serializable {
            public String member_name;
            public String sex;
            public String age;
            public String relationship;
            public String education;
            public String occupation;
            public String mobile_number;
            public String monthly_income;
            public boolean isCoapp = false;

            public boolean isCoapp() {
                return isCoapp;
            }

            public void setCoapp(boolean coapp) {
                isCoapp = coapp;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getRelationship() {
                return relationship;
            }

            public void setRelationship(String relationship) {
                this.relationship = relationship;
            }

            public String getEducation() {
                return education;
            }

            public void setEducation(String education) {
                this.education = education;
            }

            public String getOccupation() {
                return occupation;
            }

            public void setOccupation(String occupation) {
                this.occupation = occupation;
            }

            public String getMobile_number() {
                return mobile_number;
            }

            public void setMobile_number(String mobile_number) {
                this.mobile_number = mobile_number;
            }

            public String getMonthly_income() {
                return monthly_income;
            }

            public void setMonthly_income(String monthly_income) {
                this.monthly_income = monthly_income;
            }


        }
    }


    public static class MonthlyHouseHoldInfo implements Serializable {
        public String household_rent;
        public String household_food_n_fule;
        public String household_eduction;
        public String household_medical;
        public String household_loan_detail;
        public String household_other;
        public String household_surplus;

        public String getHousehold_rent() {
            return household_rent;
        }

        public void setHousehold_rent(String household_rent) {
            this.household_rent = household_rent;
        }

        public String getHousehold_food_n_fule() {
            return household_food_n_fule;
        }

        public void setHousehold_food_n_fule(String household_food_n_fule) {
            this.household_food_n_fule = household_food_n_fule;
        }

        public String getHousehold_eduction() {
            return household_eduction;
        }

        public void setHousehold_eduction(String household_eduction) {
            this.household_eduction = household_eduction;
        }

        public String getHousehold_medical() {
            return household_medical;
        }

        public void setHousehold_medical(String household_medical) {
            this.household_medical = household_medical;
        }

        public String getHousehold_loan_detail() {
            return household_loan_detail;
        }

        public void setHousehold_loan_detail(String household_loan_detail) {
            this.household_loan_detail = household_loan_detail;
        }

        public String getHousehold_other() {
            return household_other;
        }

        public void setHousehold_other(String household_other) {
            this.household_other = household_other;
        }

        public String getHousehold_surplus() {
            return household_surplus;
        }

        public void setHousehold_surplus(String household_surplus) {
            this.household_surplus = household_surplus;
        }


    }

    public static class MonthlySavingsInfo implements Serializable {
        public String average_post_office;
        public String average_bank;
        public String average_insurance;
        public String average_finance_company;
        public String average_chit_fund;
        public String average_other;
        public String average_monthly_saving;
        public String average_surplus;

        public String getAverage_post_office() {
            return average_post_office;
        }

        public void setAverage_post_office(String average_post_office) {
            this.average_post_office = average_post_office;
        }

        public String getAverage_bank() {
            return average_bank;
        }

        public void setAverage_bank(String average_bank) {
            this.average_bank = average_bank;
        }

        public String getAverage_insurance() {
            return average_insurance;
        }

        public void setAverage_insurance(String average_insurance) {
            this.average_insurance = average_insurance;
        }

        public String getAverage_finance_company() {
            return average_finance_company;
        }

        public void setAverage_finance_company(String average_finance_company) {
            this.average_finance_company = average_finance_company;
        }

        public String getAverage_chit_fund() {
            return average_chit_fund;
        }

        public void setAverage_chit_fund(String average_chit_fund) {
            this.average_chit_fund = average_chit_fund;
        }

        public String getAverage_other() {
            return average_other;
        }

        public void setAverage_other(String average_other) {
            this.average_other = average_other;
        }

        public String getAverage_monthly_saving() {
            return average_monthly_saving;
        }

        public void setAverage_monthly_saving(String average_monthly_saving) {
            this.average_monthly_saving = average_monthly_saving;
        }

        public String getAverage_surplus() {
            return average_surplus;
        }

        public void setAverage_surplus(String average_surplus) {
            this.average_surplus = average_surplus;
        }


    }

    public static class ExistingLoanInfo implements Serializable {
        public String company_name;
        public String loan_amount;
        public String tenure;
        public String emi_amount;
        public String installment_due;

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
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

    public static class ChequeInfo implements Serializable {
        public String detaills_of;
        public String bank_name;
        public String branch_name;
        public String cheque_number;

        public String getDetaills_of() {
            return detaills_of;
        }

        public void setDetaills_of(String detaills_of) {
            this.detaills_of = detaills_of;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getCheque_number() {
            return cheque_number;
        }

        public void setCheque_number(String cheque_number) {
            this.cheque_number = cheque_number;
        }


    }


    public static class KycInfo implements Serializable {
        private String userType;
        private String documentName;
        private String documentNumber;
        private String documentId;
        private ArrayList<String> documentImages;

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public String getDocumentName() {
            return documentName;
        }

        public void setDocumentName(String documentName) {
            this.documentName = documentName;
        }

        public String getDocumentNumber() {
            return documentNumber;
        }

        public void setDocumentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
        }


        public ArrayList<String> getDocumentImages() {
            return documentImages;
        }

        public void setDocumentImages(ArrayList<String> documentImages) {
            this.documentImages = documentImages;
        }

        @Override
        public String toString() {
            return "KycInfo{" +
                    "documentName='" + documentName + '\'' +
                    ", documentNumber='" + documentNumber + '\'' +
                    ", documentId='" + documentId + '\'' +
                    ", documentImages=" + documentImages +
                    '}';
        }
    }


    public static class DeclarationInfo implements Serializable {
        public String applicant_signature;

        public String getApplicant_signature() {
            return applicant_signature;
        }

        public void setApplicant_signature(String applicant_signature) {
            this.applicant_signature = applicant_signature;
        }

        public String getCoapplicant_signature() {
            return coapplicant_signature;
        }

        public void setCoapplicant_signature(String coapplicant_signature) {
            this.coapplicant_signature = coapplicant_signature;
        }

        public String getEmployee_signature() {
            return employee_signature;
        }

        public void setEmployee_signature(String employee_signature) {
            this.employee_signature = employee_signature;
        }

        public String coapplicant_signature;
        public String employee_signature;
    }

    @Override
    public String toString() {
        return "AddLeadRequestModel{" +
                "kycInfoList=" + kycInfoList +
                '}';
    }
}

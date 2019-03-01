package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 30/6/18.
 */
public class GetLeadFieldsResponseModel extends BaseWebResponseModel {


    /**
     * data : {"areaPaymentCycle":[{"id":1,"type":"monthly"},{"id":2,"type":"quarterly"}],"areaTypeOfCustomer":[{"id":1,"type":"Regular"},{"id":2,"type":"Not Regular"}],"areaReligion":[{"id":1,"type":"Hindu"},{"id":2,"type":"Muslim"},{"id":3,"type":"Sikh"}],"areaMaritalStatus":[{"id":1,"type":"Yes"},{"id":2,"type":"No"}],"areaHomeStatus":[{"id":1,"type":"owned"},{"id":2,"type":"rented"}],"areaPermanentHomeStatus":[{"id":1,"type":"owned"},{"id":2,"type":"rented"}],"areaDocuments":[{"id":1,"type":"Aadhar Card"},{"id":2,"type":"Voter Id"},{"id":3,"type":"Bank Statement"},{"id":4,"type":"Ration Card"},{"id":5,"type":"Pan Card"},{"id":6,"type":"Driving Licence"},{"id":7,"type":"Electricity/Water Bill"},{"id":8,"type":"Rent Agreement"},{"id":9,"type":"Others"}],"CheckDetaiosOf":[{"id":1,"type":"Applicant"},{"id":2,"type":"Co Applicant"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ArrayList<AreaPaymentCycleBean> areaPaymentCycle;
        private ArrayList<AreaTypeOfCustomerBean> areaTypeOfCustomer;
        private ArrayList<AreaReligionBean> areaReligion;
        private ArrayList<AreaMaritalStatusBean> areaMaritalStatus;
        private ArrayList<AreaHomeStatusBean> areaHomeStatus;
        private ArrayList<AreaPermanentHomeStatusBean> areaPermanentHomeStatus;
        private ArrayList<AreaDocumentsBean> areaDocuments;
        private ArrayList<CheckDetaiosOfBean> CheckDetaiosOf;

        public ArrayList<AreaPaymentCycleBean> getAreaPaymentCycle() {
            return areaPaymentCycle;
        }

        public void setAreaPaymentCycle(ArrayList<AreaPaymentCycleBean> areaPaymentCycle) {
            this.areaPaymentCycle = areaPaymentCycle;
        }

        public ArrayList<AreaTypeOfCustomerBean> getAreaTypeOfCustomer() {
            return areaTypeOfCustomer;
        }

        public void setAreaTypeOfCustomer(ArrayList<AreaTypeOfCustomerBean> areaTypeOfCustomer) {
            this.areaTypeOfCustomer = areaTypeOfCustomer;
        }

        public ArrayList<AreaReligionBean> getAreaReligion() {
            return areaReligion;
        }

        public void setAreaReligion(ArrayList<AreaReligionBean> areaReligion) {
            this.areaReligion = areaReligion;
        }

        public ArrayList<AreaMaritalStatusBean> getAreaMaritalStatus() {
            return areaMaritalStatus;
        }

        public void setAreaMaritalStatus(ArrayList<AreaMaritalStatusBean> areaMaritalStatus) {
            this.areaMaritalStatus = areaMaritalStatus;
        }

        public ArrayList<AreaHomeStatusBean> getAreaHomeStatus() {
            return areaHomeStatus;
        }

        public void setAreaHomeStatus(ArrayList<AreaHomeStatusBean> areaHomeStatus) {
            this.areaHomeStatus = areaHomeStatus;
        }

        public ArrayList<AreaPermanentHomeStatusBean> getAreaPermanentHomeStatus() {
            return areaPermanentHomeStatus;
        }

        public void setAreaPermanentHomeStatus(ArrayList<AreaPermanentHomeStatusBean> areaPermanentHomeStatus) {
            this.areaPermanentHomeStatus = areaPermanentHomeStatus;
        }

        public ArrayList<AreaDocumentsBean> getAreaDocuments() {
            return areaDocuments;
        }

        public void setAreaDocuments(ArrayList<AreaDocumentsBean> areaDocuments) {
            this.areaDocuments = areaDocuments;
        }

        public ArrayList<CheckDetaiosOfBean> getCheckDetaiosOf() {
            return CheckDetaiosOf;
        }

        public void setCheckDetaiosOf(ArrayList<CheckDetaiosOfBean> CheckDetaiosOf) {
            this.CheckDetaiosOf = CheckDetaiosOf;
        }

        public static class AreaPaymentCycleBean {
            /**
             * id : 1
             * type : monthly
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class AreaTypeOfCustomerBean {
            /**
             * id : 1
             * type : Regular
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class AreaReligionBean {
            /**
             * id : 1
             * type : Hindu
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class AreaMaritalStatusBean {
            /**
             * id : 1
             * type : Yes
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class AreaHomeStatusBean {
            /**
             * id : 1
             * type : owned
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class AreaPermanentHomeStatusBean {
            /**
             * id : 1
             * type : owned
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class AreaDocumentsBean {
            /**
             * id : 1
             * type : Aadhar Card
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class CheckDetaiosOfBean {
            /**
             * id : 1
             * type : Applicant
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}

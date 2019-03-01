package com.solutionavenues.deedee.model.response;

import java.util.List;

/**
 * Created by Azher on 28/6/18.
 */
public class LocalityMicroEnterPriseModel extends BaseWebResponseModel {

    /**
     * data : {"micro_enterprise":[{"id":1,"type":"Kirana & General Stores"},{"id":2,"type":"Tea Stalls"},{"id":3,"type":"Tailoring Shops"},{"id":4,"type":"Electrical Repairs & Servicing"},{"id":5,"type":"Agri Input Shops"},{"id":6,"type":"Agri Processing Units(Flour Mills)"},{"id":7,"type":"Cloth Shops"}],"other_micro_enterprise":[{"id":1,"type":"Handloom Weavers"},{"id":2,"type":"Stationary Shops"},{"id":3,"type":"Diaries"},{"id":4,"type":"Vegetable Stalls"},{"id":5,"type":"Fruit Stalls"},{"id":6,"type":"Hotels/Restaurent/Dhabas"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<MicroEnterpriseBean> micro_enterprise;
        private List<OtherMicroEnterpriseBean> other_micro_enterprise;

        public List<MicroEnterpriseBean> getMicro_enterprise() {
            return micro_enterprise;
        }

        public void setMicro_enterprise(List<MicroEnterpriseBean> micro_enterprise) {
            this.micro_enterprise = micro_enterprise;
        }

        public List<OtherMicroEnterpriseBean> getOther_micro_enterprise() {
            return other_micro_enterprise;
        }

        public void setOther_micro_enterprise(List<OtherMicroEnterpriseBean> other_micro_enterprise) {
            this.other_micro_enterprise = other_micro_enterprise;
        }

        public static class MicroEnterpriseBean {
            /**
             * id : 1
             * type : Kirana & General Stores
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

        public static class OtherMicroEnterpriseBean {
            /**
             * id : 1
             * type : Handloom Weavers
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

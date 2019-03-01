package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 6/7/18.
 */
public class ProductListResponseModel {
    /**
     * code : 1
     * error : false
     * data : [{"id":2,"product_name":"shirt","new_customer_fee1":"1","new_customer_fee2":"1","existing_customer_fee1":"1","existing_customer_fee2":"1","amount":"24000","tenure":12,"interest_rate":"18","discount":"12","user_id":1,"status":2,"created":"2018-07-02T12:39:21+00:00","modified":"2018-07-02T15:54:08+00:00"}]
     * message : Success
     */

    private int code;
    private boolean error;
    private String message;
    private ArrayList<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * product_name : shirt
         * new_customer_fee1 : 1
         * new_customer_fee2 : 1
         * existing_customer_fee1 : 1
         * existing_customer_fee2 : 1
         * amount : 24000
         * tenure : 12
         * interest_rate : 18
         * discount : 12
         * user_id : 1
         * status : 2
         * created : 2018-07-02T12:39:21+00:00
         * modified : 2018-07-02T15:54:08+00:00
         */

        private int id;
        private String product_name;
        private String new_customer_fee1;
        private String new_customer_fee2;
        private String existing_customer_fee1;
        private String existing_customer_fee2;
        private String amount;
        private int tenure;
        private String interest_rate;
        private String discount;
        private int user_id;
        private int status;
        private String created;
        private String modified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getNew_customer_fee1() {
            return new_customer_fee1;
        }

        public void setNew_customer_fee1(String new_customer_fee1) {
            this.new_customer_fee1 = new_customer_fee1;
        }

        public String getNew_customer_fee2() {
            return new_customer_fee2;
        }

        public void setNew_customer_fee2(String new_customer_fee2) {
            this.new_customer_fee2 = new_customer_fee2;
        }

        public String getExisting_customer_fee1() {
            return existing_customer_fee1;
        }

        public void setExisting_customer_fee1(String existing_customer_fee1) {
            this.existing_customer_fee1 = existing_customer_fee1;
        }

        public String getExisting_customer_fee2() {
            return existing_customer_fee2;
        }

        public void setExisting_customer_fee2(String existing_customer_fee2) {
            this.existing_customer_fee2 = existing_customer_fee2;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getTenure() {
            return tenure;
        }

        public void setTenure(int tenure) {
            this.tenure = tenure;
        }

        public String getInterest_rate() {
            return interest_rate;
        }

        public void setInterest_rate(String interest_rate) {
            this.interest_rate = interest_rate;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

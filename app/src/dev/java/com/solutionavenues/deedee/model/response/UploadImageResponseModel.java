package com.solutionavenues.deedee.model.response;

/**
 * Created by Azher on 23/6/18.
 */
public class UploadImageResponseModel extends BaseWebResponseModel {


    /**
     * data : {"name":"683208323_current_location_pin.png","type":"ADH"}
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
         * name : 683208323_current_location_pin.png
         * type : ADH
         */

        private String name;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

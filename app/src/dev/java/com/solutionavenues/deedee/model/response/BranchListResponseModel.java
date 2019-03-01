package com.solutionavenues.deedee.model.response;

import java.util.ArrayList;

/**
 * Created by Azher on 30/6/18.
 */
public class BranchListResponseModel extends BaseWebResponseModel {

    private ArrayList<DataBean> data;

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : Manish
         * status : 1
         * created : 2018-06-29T15:38:20+00:00
         * modified : 2018-06-30T05:58:29+00:00
         */

        private int id;
        private String name;
        private int status;
        private String created;
        private String modified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return getValidString(name);
        }

        public void setName(String name) {
            this.name = name;
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

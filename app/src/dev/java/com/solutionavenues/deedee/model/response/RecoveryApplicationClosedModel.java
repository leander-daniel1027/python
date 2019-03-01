package com.solutionavenues.deedee.model.response;

import java.util.List;

/**
 * Created by Azher on 1/11/18.
 */
public class RecoveryApplicationClosedModel {
    /**
     * isSuccess : true
     * message : Your details successfully updated.
     * data : [{"id":13,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":14,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":15,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":16,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":17,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":18,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":19,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":20,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":21,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":22,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":23,"is_closed":1,"modified":"2018-11-01T21:26:23"},{"id":24,"is_closed":1,"modified":"2018-11-01T21:26:23"}]
     */

    private boolean isSuccess;
    private String message;
    private List<DataBean> data;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 13
         * is_closed : 1
         * modified : 2018-11-01T21:26:23
         */

        private int id;
        private int is_closed;
        private String modified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_closed() {
            return is_closed;
        }

        public void setIs_closed(int is_closed) {
            this.is_closed = is_closed;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }
    }
}

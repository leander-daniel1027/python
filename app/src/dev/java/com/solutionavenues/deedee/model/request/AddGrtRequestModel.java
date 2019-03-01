package com.solutionavenues.deedee.model.request;

import java.util.ArrayList;

/**
 * Created by Azher on 23/7/18.
 */
public class AddGrtRequestModel extends BaseWebRequestModel {

    /**
     * questions : [{"question_id":"1","question":"1 Have all the members arrived on time?","selected_members":[1,2,3]},{"question_id":"1","question":"1 Have all the members arrived on time?","selected_members":[1,2,3]}]
     * others :
     */

    private String user_id;
    private String group_passed;
    private String cite_reason;
    private String bm_remark;
    private String bm_name;
    private String center_leader;
    private String bm_signature;
    private String cl_signature;
    private ArrayList<QuestionsBean> questions;
    private ArrayList<MembersBean> members;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGroup_passed() {
        return group_passed;
    }

    public void setGroup_passed(String group_passed) {
        this.group_passed = group_passed;
    }

    public String getCite_reason() {
        return cite_reason;
    }

    public void setCite_reason(String cite_reason) {
        this.cite_reason = cite_reason;
    }

    public String getBm_remark() {
        return bm_remark;
    }

    public void setBm_remark(String bm_remark) {
        this.bm_remark = bm_remark;
    }

    public String getBm_name() {
        return bm_name;
    }

    public void setBm_name(String bm_name) {
        this.bm_name = bm_name;
    }

    public String getCenter_leader() {
        return center_leader;
    }

    public void setCenter_leader(String center_leader) {
        this.center_leader = center_leader;
    }

    public String getBm_signature() {
        return bm_signature;
    }

    public void setBm_signature(String bm_signature) {
        this.bm_signature = bm_signature;
    }

    public String getCl_signature() {
        return cl_signature;
    }

    public void setCl_signature(String cl_signature) {
        this.cl_signature = cl_signature;
    }

    public ArrayList<MembersBean> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<MembersBean> members) {
        this.members = members;
    }


    public ArrayList<QuestionsBean> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionsBean> questions) {
        this.questions = questions;
    }

    public static class QuestionsBean {
        /**
         * question_id : 1
         * question : 1 Have all the members arrived on time?
         * selected_members : [1,2,3]
         */

        private String question_id;
        private String question;
        private ArrayList<String> selected_members;

        public ArrayList<AllMembers> getAll_members() {
            return all_members;
        }

        public void setAll_members(ArrayList<AllMembers> all_members) {
            this.all_members = all_members;
        }

        private ArrayList<AllMembers> all_members;

        public String getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public ArrayList<String> getSelected_members() {
            return selected_members;
        }

        public void setSelected_members(ArrayList<String> selected_members) {
            this.selected_members = selected_members;
        }
        public static class AllMembers {

            /**
             * id : 1
             * name : saurabh
             */


            private int id;
            private String name;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            private boolean isSelected=false;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class MembersBean {

        private String member_name;
        private String father_name;
        private String mother_name;
        private String husband_father_name;
        private String member_signature;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
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

        public String getHusband_father_name() {
            return husband_father_name;
        }

        public void setHusband_father_name(String husband_father_name) {
            this.husband_father_name = husband_father_name;
        }

        public String getMember_signature() {
            return member_signature;
        }

        public void setMember_signature(String member_signature) {
            this.member_signature = member_signature;
        }



    }
}

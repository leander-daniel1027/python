package com.solutionavenues.deedee.model;


import com.solutionavenues.deedee.model.response.BaseWebResponseModel;

/**
 * Created by br on 29/3/18.
 */

public class UserModel extends BaseWebResponseModel {


    /**
     * id : 1
     * group_id : 1
     * role_id : 1
     * username : superadmin
     * email : superadmin@mailinator.com
     * first_name : Super
     * last_name : Admin
     * contact : 919-333-4444
     * image : 1466190031_404.jpeg
     * status : 1
     * verification_code : null
     * is_verify : 1
     * created : 2016-10-17T00:00:00+00:00
     * modified : 2018-06-20T12:44:55+00:00
     * reset_password_token : b202e74fb8315aa2947394dce0fdb6de445e7058dd4a7fa607c004a4bd0e8928
     * token_created_at : 2018-06-17T14:17:31+00:00
     */

    private int id;
    private int group_id;
    private int role_id;
    private String username;
    private String email;
    private String first_name;
    private String last_name;
    private String contact;
    private String image;
    private int status;
    private String verification_code;
    private int is_verify;
    private String created;
    private String modified;
    private String reset_password_token;
    private String token_created_at;
    private String branch_id;
    private String parent_id;
    private String role_type;

    public String getLo_time() {
        return lo_time;
    }

    public void setLo_time(String lo_time) {
        this.lo_time = lo_time;
    }

    private String lo_time;
    private ProductData product;

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }
    //    public ArrayList<ProductData> getProduct() {
//        return product;
//    }
//
//    public void setProduct(ArrayList<ProductData> product) {
//        this.product = product;
//    }
//
//    private ArrayList<ProductData> product;

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public int getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(int is_verify) {
        this.is_verify = is_verify;
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

    public String getReset_password_token() {
        return reset_password_token;
    }

    public void setReset_password_token(String reset_password_token) {
        this.reset_password_token = reset_password_token;
    }

    public String getToken_created_at() {
        return token_created_at;
    }

    public void setToken_created_at(String token_created_at) {
        this.token_created_at = token_created_at;
    }

    public String getRole_type() {
        return role_type;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }

    public static class ProductData{
       private int id;

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

       private String product_name;
   }


}
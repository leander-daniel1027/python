package com.solutionavenues.deedee.model.request;

import java.util.ArrayList;

/**
 * Created by sid on 02/04/2016.
 */
public class KycDocumentItemModel {

    private String documentName;
    private String documentNumber;
    private String documentPath;
    private String userName;
    private ArrayList<String> documentImages;

    public KycDocumentItemModel() {
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getDocumentImages() {

        return documentImages;
    }

    public void setDocumentImages(ArrayList<String> documentImages) {
        this.documentImages = documentImages;
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

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}

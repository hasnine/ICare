package com.newfeds.icare.model;

/**
 * Created by GT on 1/23/2016.
 */
public class Prescription {
    public  String ID = null;
    public  String MEMBER_ID = null;
    public  String TITLE = null;
    public  String DESCRIPTION = null;
    public  String PHOTO = null;

    public Prescription() {
    }

    public Prescription(String ID, String MEMBER_ID, String TITLE, String DESCRIPTION, String PHOTO) {
        this.ID = ID;
        this.MEMBER_ID = MEMBER_ID;
        this.TITLE = TITLE;
        this.DESCRIPTION = DESCRIPTION;
        this.PHOTO = PHOTO;
    }
}

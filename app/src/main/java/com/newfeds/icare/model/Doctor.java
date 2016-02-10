package com.newfeds.icare.model;

/**
 * Created by GT on 1/20/2016.
 */
public class Doctor {
    public String TABLE_NAME= null;
    public String ID= null;
    public String NAME= null;
    public String SPECIALITY= null;
    public String PHOTO= "";
    public String HOSPITAL= null;
    public String PHONE= null;
    public String EMAIL= null;

    public Doctor() {
    }

    public Doctor(String TABLE_NAME, String ID, String NAME, String SPECIALITY, String PHOTO, String HOSPITAL, String PHONE, String EMAIL) {
        this.TABLE_NAME = TABLE_NAME;
        this.ID = ID;
        this.NAME = NAME;
        this.SPECIALITY = SPECIALITY;
        this.PHOTO = PHOTO;
        this.HOSPITAL = HOSPITAL;
        this.PHONE = PHONE;
        this.EMAIL = EMAIL;
    }
}

package com.newfeds.icare.model;

/**
 * Created by GT on 1/16/2016.
 */
public class Profile {
    public String ID = null;
    public String NAME = null;
    public String AGE = null;
    public String HEIGHT = null;
    public String WEIGHT = null;
    public String GENDER = null;
    public String PHONE = null;
    public String PHOTO = "";
    public String RELATIONSHIP = null;

    public Profile(){
    }

    public Profile(String ID, String NAME, String AGE, String HEIGHT, String WEIGHT, String GENDER, String PHONE, String PHOTO, String RELATIONSHIP) {
        this.ID = ID;
        this.NAME = NAME;
        this.AGE = AGE;
        this.HEIGHT = HEIGHT;
        this.WEIGHT = WEIGHT;
        this.GENDER = GENDER;
        this.PHONE = PHONE;
        this.PHOTO = PHOTO;
        this.RELATIONSHIP = RELATIONSHIP;
    }
}

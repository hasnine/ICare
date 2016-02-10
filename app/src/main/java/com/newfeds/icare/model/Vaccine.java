package com.newfeds.icare.model;

/**
 * Created by GT on 1/23/2016.
 */
public class Vaccine {
    public String ID = null;
    public String MEMBER_ID = null;
    public String DETAIL = null;
    public String REMINDER = null;
    public String DATETIME = null;

    public Vaccine() {
    }

    public Vaccine(String ID, String MEMBER_ID, String DETAIL, String REMINDER, String DATETIME){
        this.ID = ID;
        this.MEMBER_ID = MEMBER_ID;
        this.DETAIL  = DETAIL;
        this.REMINDER = REMINDER;
        this.DATETIME = DATETIME;
    }
}

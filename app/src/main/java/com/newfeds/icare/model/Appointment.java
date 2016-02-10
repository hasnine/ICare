package com.newfeds.icare.model;

/**
 * Created by GT on 1/23/2016.
 */
public class Appointment {
    public String ID = null;
    public String MEMBER_ID = null;
    public String DOCTOR_NAME = null;
    public String TITLE = null;
    public String DESCRIPTION = null;
    public String DATETIME = null;

    public Appointment() {
    }

    public Appointment(String ID, String MEMBER_ID, String DOCTOR_NAME, String TITLE, String DESCRIPTION, String DATETIME) {
        this.ID = ID;
        this.MEMBER_ID = MEMBER_ID;
        this.DOCTOR_NAME = DOCTOR_NAME;
        this.TITLE = TITLE;
        this.DESCRIPTION = DESCRIPTION;
        this.DATETIME = DATETIME;
    }
}

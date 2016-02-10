package com.newfeds.icare.model;

/**
 * Created by GT on 1/23/2016.
 */
public class Diet {
    public String ID = null;
    public String MEMBER_ID = null;
    public String MENU = null;
    public String DATETIME = null;
    public String REMINDER = null;
    public String DAILY_REPEAT = null;

    public Diet() {
    }

    public Diet(String ID, String MEMBER_ID, String MENU, String DATETIME, String REMINDER, String DAILY_REPEAT) {
        this.ID = ID;
        this.MENU = MENU;
        this.DATETIME = DATETIME;
        this.MEMBER_ID = MEMBER_ID;
        this.REMINDER = REMINDER;
        this.DAILY_REPEAT = DAILY_REPEAT;
    }
}

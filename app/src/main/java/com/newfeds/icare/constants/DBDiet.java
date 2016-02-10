package com.newfeds.icare.constants;

/**
 * Created by GT on 1/22/2016.
 */

public class DBDiet {
    public static final String TABLE_NAME = "tbl_diet";
    public static final String ID = "id";
    public static final String MEMBER_ID = "member_id";
    public static final String MENU = "menu";
    public static final String DATETIME = "datetime";
    public static final String REMINDER = "reminder";
    public static final String DAILY_REPEAT = "daily_repeat";


    public static String getTableCreationString(){
        return "CREATE TABLE "+ TABLE_NAME +
                "("+
                ID+" integer primary key,"+
                MEMBER_ID+" text,"+
                MENU + " text,"+
                REMINDER + " text,"+
                DAILY_REPEAT+" text,"+
                DATETIME +" text)";
    }
}

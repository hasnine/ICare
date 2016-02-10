package com.newfeds.icare.constants;

/**
 * Created by GT on 1/23/2016.
 */
public class DBVaccine {
    public static final String TABLE_NAME = "tbl_vaccine";
    public static final String ID = "id";
    public static final String MEMBER_ID = "menu";
    public static final String DETAIL = "detail";
    public static final String REMINDER = "reminder";
    public static final String DATETIME = "datetime";

    public static String getTableCreationString(){
        return "CREATE TABLE "+ TABLE_NAME +
                "("+
                ID+" integer primary key,"+
                MEMBER_ID + " text,"+
                DETAIL + " text,"+
                REMINDER + " int,"+
                DATETIME +" text)";
    }
}

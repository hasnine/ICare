package com.newfeds.icare.constants;

/**
 * Created by GT on 1/22/2016.
 */

public class DBAppointment {
    public static final String TABLE_NAME = "tbl_appointment";
    public static final String ID = "id";
    public static final String MEMBER_ID = "member_id";
    public static final String DOCTOR_NAME = "doctor_name";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DATETIME = "datetime";


    public static String getTableCreationString(){
        return "CREATE TABLE "+ TABLE_NAME +
                "("+
                ID+" integer primary key,"+
                TITLE + " text,"+
                MEMBER_ID+ " text,"+
                DOCTOR_NAME + " text,"+
                DESCRIPTION + " text,"+
                DATETIME +" text)";
    }
}

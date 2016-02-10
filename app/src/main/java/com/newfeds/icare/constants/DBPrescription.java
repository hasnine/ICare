package com.newfeds.icare.constants;

/**
 * Created by GT on 1/22/2016.
 */

public class DBPrescription {
    public static final String TABLE_NAME  = "tbl_prescription";

    public static final String ID = "id";
    public static final String MEMBER_ID = "patient_id";
    public static final String TITLE = "name";
    public static final String DESCRIPTION = "description";
    public static final String PHOTO = "photo";


    public static final String getTableCreationString(){
        return "CREATE TABLE " + TABLE_NAME +
                "("+
                ID +" integer primary key,"+
                MEMBER_ID +" text,"+
                TITLE +" text,"+
                DESCRIPTION+ " text,"+
                PHOTO+" text)";
    }
}

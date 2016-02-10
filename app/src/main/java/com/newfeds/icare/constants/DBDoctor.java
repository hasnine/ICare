package com.newfeds.icare.constants;

/**
 * Created by GT on 1/14/2016.
 */

public class DBDoctor {
    public static final String TABLE_NAME = "tbl_doctors";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SPECIALITY = "speciality";
    public static final String PHOTO = "photo";
    public static final String HOSPITAL = "hostpital";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    public static String getTableCreationString(){
        return "CREATE TABLE "+ TABLE_NAME +
                "("+
                ID+" integer primary key,"+
                NAME+" text,"+
                PHONE+" text,"+
                HOSPITAL+" text,"+
                PHOTO +" text,"+
                EMAIL + " text,"+
                SPECIALITY+" text)";
    }

}

package com.newfeds.icare.constants;

/**
 * Created by GT on 1/14/2016.
 */
public class DBProfile {
    public static final String TABLE_NAME  = "tbl_profiles";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String GENDER = "gender";
    public static final String PHONE = "phonenumber";
    public static final String PHOTO = "photo";
    public static final String RELATIONSHIP = "relationship";

    public static final String getTableCreationString(){
        return "CREATE TABLE " + TABLE_NAME +
                "("+
                ID +" integer primary key,"+
                NAME+" text,"+
                PHONE+" text,"+
                AGE+" text,"+
                HEIGHT+" text,"+
                WEIGHT+" text,"+
                GENDER+" text,"+
                PHOTO+" text,"+
                RELATIONSHIP+" text)";
    }
}

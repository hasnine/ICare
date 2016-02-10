package com.newfeds.icare.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.newfeds.icare.constants.Const;
import com.newfeds.icare.constants.DBAppointment;
import com.newfeds.icare.constants.DBDiet;
import com.newfeds.icare.constants.DBDoctor;
import com.newfeds.icare.constants.DBPrescription;
import com.newfeds.icare.constants.DBProfile;
import com.newfeds.icare.constants.DBVaccine;
import com.newfeds.icare.model.Appointment;
import com.newfeds.icare.model.Diet;
import com.newfeds.icare.model.Doctor;
import com.newfeds.icare.model.Prescription;
import com.newfeds.icare.model.Profile;
import com.newfeds.icare.model.Vaccine;

import java.util.ArrayList;

/**
 * Created by GT on 1/14/2016.
 */

public class DBhelper extends SQLiteOpenHelper {

    Context context = null;

    public static final String DATABASE_NAME = "icaredb";
    public static final int DATABASE_VERSION = 17;


    public DBhelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            L.log("creating new tables");
            db.execSQL(DBProfile.getTableCreationString());
            db.execSQL(DBDoctor.getTableCreationString());
            db.execSQL(DBAppointment.getTableCreationString());
            db.execSQL(DBDiet.getTableCreationString());
            db.execSQL(DBPrescription.getTableCreationString());
            db.execSQL(DBVaccine.getTableCreationString());
        }catch (Exception e){
            L.log(e.getMessage());
        }
        try{
            L.log("Context is: " + context);
            SharedPreferences sharedPreferences = context.getSharedPreferences(Const.SHARED_PREF_KEY, Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
            L.log("SharedPref should be cleared");
        }catch (Exception e)
        {
            L.log(e.getMessage().toString());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + DBProfile.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBDoctor.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBDiet.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBAppointment.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBVaccine.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DBPrescription.TABLE_NAME);

            L.log("Ugrading this shit");
        }catch (Exception e){
            L.log(e.getMessage());
        }
        onCreate(db);
    }

    //Prescription Database

    public void deletePrescription(String id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DBPrescription.TABLE_NAME, DBPrescription.ID+"=?",new String[]{id});
            db.close();
        }catch (Exception e){
            L.log("prescription delete failed");
        }
    }

    public void inputPrescription(String member_id,String title, String description, String photo){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBPrescription.MEMBER_ID, member_id);
            contentValues.put(DBPrescription.TITLE, title);
            contentValues.put(DBPrescription.DESCRIPTION, description);
            contentValues.put(DBPrescription.PHOTO, photo);
            db.insert(DBPrescription.TABLE_NAME, null, contentValues);
            db.close();
        }catch (Exception e){
            L.log("PRESCRIPTION INPUT ERROR");
        }
    }

    public ArrayList<Prescription> getPrescriptions(String member_id){
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(DBPrescription.TABLE_NAME,null,DBPrescription.MEMBER_ID+"=?",new String[]{member_id},null,null,null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                Prescription prescription = new Prescription();
                prescription.ID = cursor.getString(cursor.getColumnIndex(DBPrescription.ID));
                prescription.MEMBER_ID = cursor.getString(cursor.getColumnIndex(DBPrescription.MEMBER_ID));
                prescription.PHOTO = cursor.getString(cursor.getColumnIndex(DBPrescription.PHOTO));
                prescription.TITLE = cursor.getString(cursor.getColumnIndex(DBPrescription.TITLE));
                prescription.DESCRIPTION = cursor.getString(cursor.getColumnIndex(DBPrescription.DESCRIPTION));
                prescriptions.add(prescription);
                cursor.moveToNext();
            }
        }catch (Exception e){
            L.log("Error fetching prescriptions");
        }
        return prescriptions;
    }


    //Prescription End
    //Vaccines Database

    public void inputVaccine(String member_id, String detail, String  reminder, String datetime){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBVaccine.MEMBER_ID, member_id);
            contentValues.put(DBVaccine.DETAIL, detail);
            contentValues.put(DBVaccine.REMINDER, reminder);
            contentValues.put(DBVaccine.DATETIME, datetime);
            db.insert(DBVaccine.TABLE_NAME, null, contentValues);
            db.close();
        }catch (Exception e){
            L.log("ERROR VACCINE");
        }
    }



    public ArrayList<Vaccine> getVaccines(String member_id){
        ArrayList<Vaccine> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(DBVaccine.TABLE_NAME, null,DBVaccine.MEMBER_ID+"=?",new String[]{member_id},null,null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Vaccine vaccine = new Vaccine();

                vaccine.ID = cursor.getString(cursor.getColumnIndex(DBVaccine.ID));
                vaccine.MEMBER_ID = cursor.getString(cursor.getColumnIndex(DBVaccine.MEMBER_ID));
                vaccine.DATETIME = cursor.getString(cursor.getColumnIndex(DBVaccine.DATETIME));
                vaccine.DETAIL = cursor.getString(cursor.getColumnIndex(DBVaccine.DETAIL));
                vaccine.REMINDER = cursor.getString(cursor.getColumnIndex(DBVaccine.REMINDER));

                arrayList.add(vaccine);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            L.log("Error getting vaccines list");
        }
        for(Vaccine vaccine: arrayList){
            L.log("id: " + vaccine.ID + " member:" + vaccine.MEMBER_ID + " datetime:" + vaccine.DATETIME + " detail:" + vaccine.DETAIL + " reminder:" + vaccine.REMINDER);
        }
        return arrayList;
    }

    public void deleteVaccine(String id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DBVaccine.TABLE_NAME, DBVaccine.ID + "=?", new String[]{id});
            db.close();
        }catch (Exception e){
            L.log("Error deleting vaccine");
        }
    }

    //Vaccines Ends
    //Diet Start
    public void deleteDiet(String id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DBDiet.TABLE_NAME,DBDiet.ID+"=?",new String[]{id});
            db.close();
        }catch (Exception e){
            L.log("Failed to delete");
        }
    }

    public void inputDiet(String member_id, String menu, String datetime, String reminder, String daily_repeat){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBDiet.MEMBER_ID, member_id);
            contentValues.put(DBDiet.MENU, menu);
            contentValues.put(DBDiet.DATETIME, datetime);
            contentValues.put(DBDiet.REMINDER, reminder);
            contentValues.put(DBDiet.DAILY_REPEAT, daily_repeat);
            db.insert(DBDiet.TABLE_NAME, null, contentValues);
            db.close();
        }catch (Exception e){
            L.log("ERROR DIET INPUT");
        }
    }
    public ArrayList<Diet> getDiet(String member_id){
        ArrayList<Diet> diets = new ArrayList<>();
        try{

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(DBDiet.TABLE_NAME, null, DBDiet.MEMBER_ID + "=?", new String[]{member_id}, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Diet diet = new Diet();

                diet.ID  = cursor.getString(cursor.getColumnIndex(DBDiet.ID));
                diet.MENU  = cursor.getString(cursor.getColumnIndex(DBDiet.MENU));
                diet.DAILY_REPEAT  = cursor.getString(cursor.getColumnIndex(DBDiet.DAILY_REPEAT));
                diet.DATETIME  = cursor.getString(cursor.getColumnIndex(DBDiet.DATETIME));
                diet.MEMBER_ID = cursor.getString(cursor.getColumnIndex(DBDiet.MEMBER_ID));
                diets.add(diet);
                cursor.moveToNext();
            }
        }catch (Exception e){
            L.log("error getting diet");
        }
        return diets;
    }

    //Diet End
    //Appointment Start
    public void deleteAppointment(String id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DBAppointment.TABLE_NAME,DBAppointment.ID+"=?", new String[]{id});
            db.close();
        }catch (Exception e){
            L.log("appointment");
        }
    }

    public void inputAppointment(String member_id, String doctor_name, String title, String description, String datetime){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBAppointment.MEMBER_ID, member_id);
            contentValues.put(DBAppointment.DOCTOR_NAME, doctor_name);
            contentValues.put(DBAppointment.TITLE, title);
            contentValues.put(DBAppointment.DESCRIPTION, description);
            contentValues.put(DBAppointment.DATETIME,datetime);

            db.insert(DBAppointment.TABLE_NAME, null, contentValues);
            db.close();
        }catch (Exception e){
            L.log("ERROR APPOINTMENT DATABASE");
        }
    }

    public ArrayList<Appointment> getAppointment(String member_id){
        ArrayList<Appointment> appointments = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(DBAppointment.TABLE_NAME,null,DBAppointment.MEMBER_ID+"=?",new String[]{member_id},null,null,null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Appointment appointment = new Appointment();

                appointment.ID = cursor.getString(cursor.getColumnIndex(DBAppointment.ID));
                appointment.DATETIME = cursor.getString(cursor.getColumnIndex(DBAppointment.DATETIME));
                appointment.DOCTOR_NAME = cursor.getString(cursor.getColumnIndex(DBAppointment.DOCTOR_NAME));
                appointment.DESCRIPTION = cursor.getString(cursor.getColumnIndex(DBAppointment.DESCRIPTION));
                appointment.TITLE = cursor.getString(cursor.getColumnIndex(DBAppointment.TITLE));
                appointments.add(appointment);
                cursor.moveToNext();
            }
        }catch (Exception e){
            L.log("Error fetching appointments");
        }
        return appointments;
    }
    //Appointment End
    public void inputDoctor(String name, String speciality, String photo, String hospital, String phone,String email){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBDoctor.NAME, name);
            contentValues.put(DBDoctor.EMAIL, email);
            contentValues.put(DBDoctor.PHONE, phone);
            contentValues.put(DBDoctor.PHOTO, photo);
            contentValues.put(DBDoctor.SPECIALITY, speciality);
            contentValues.put(DBDoctor.HOSPITAL, hospital);
            db.insert(DBDoctor.TABLE_NAME, null, contentValues);
            L.log("registered");
        }catch (Exception e){
            L.log(e.toString());
        }
    }


    //Member Start
    public Profile getMember(String id){
        Profile profile = new Profile();
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.query(DBProfile.TABLE_NAME, null, DBProfile.ID + "=?", new String[]{id}, null, null, null, "1");
            cursor.moveToFirst();

            if(cursor.getCount()==1){
                profile.ID = cursor.getString(cursor.getColumnIndex(DBProfile.ID));
                profile.NAME = cursor.getString(cursor.getColumnIndex(DBProfile.NAME));
                profile.RELATIONSHIP = cursor.getString(cursor.getColumnIndex(DBProfile.RELATIONSHIP));
                profile.HEIGHT = cursor.getString(cursor.getColumnIndex(DBProfile.HEIGHT));
                profile.WEIGHT = cursor.getString(cursor.getColumnIndex(DBProfile.WEIGHT));
                profile.GENDER = cursor.getString(cursor.getColumnIndex(DBProfile.GENDER));
                profile.AGE = cursor.getString(cursor.getColumnIndex(DBProfile.AGE));
                profile.PHOTO = cursor.getString(cursor.getColumnIndex(DBProfile.PHOTO));
                profile.PHONE = cursor.getString(cursor.getColumnIndex(DBProfile.PHONE));
            }
        }catch (Exception e){
            L.log(e.toString());
        }
        return profile;
    }

    public void deleteMember(String id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            db.delete(DBProfile.TABLE_NAME, DBProfile.ID + "=?", new String[]{id});
            db.close();
            L.log("deleted: " + id);
        }catch (Exception e){
            L.log(e.toString());
        }
    }

    public void inputMember(String name,String photo, String age, String height, String weight, String gender, String phone, String relationship){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBProfile.NAME, name);
            contentValues.put(DBProfile.AGE, age);
            contentValues.put(DBProfile.HEIGHT, height);
            contentValues.put(DBProfile.WEIGHT, weight);
            contentValues.put(DBProfile.GENDER, gender);
            contentValues.put(DBProfile.PHOTO, photo);
            contentValues.put(DBProfile.PHONE, phone);
            contentValues.put(DBProfile.RELATIONSHIP, relationship);
            db.insert(DBProfile.TABLE_NAME, null, contentValues);
            db.close();
            L.log("registered");
        }catch (Exception e){
            L.log(e.toString());
        }
    }

    public void updateMember(String id, String name, String photo, String age, String height, String weight, String gender, String phone, String relationship){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBProfile.NAME,name);
            contentValues.put(DBProfile.AGE, age);
            contentValues.put(DBProfile.HEIGHT, height);
            contentValues.put(DBProfile.WEIGHT, weight);
            contentValues.put(DBProfile.GENDER, gender);
            contentValues.put(DBProfile.PHOTO, photo);
            contentValues.put(DBProfile.PHONE,phone);
            contentValues.put(DBProfile.RELATIONSHIP, relationship);
            db.update(DBProfile.TABLE_NAME, contentValues, DBProfile.ID + "=?", new String[]{id});
            db.close();
        }catch (Exception e){
            L.log("member update fail");
        }
    }
    //Member end
    //Doctors
    public void deleteDoctor(String id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DBDoctor.TABLE_NAME,DBDoctor.ID+"=?",new String[]{id});
            db.close();
        }catch (Exception e){
            L.log("failed deleting doc");
        }
    }
    public ArrayList<Doctor> getDoctors(){
        ArrayList<Doctor> arrayList = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(DBDoctor.TABLE_NAME, null,null,null,null,null,DBDoctor.NAME,null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                Doctor doctor = new Doctor();
                doctor.ID = cursor.getString(cursor.getColumnIndex(DBDoctor.ID));
                doctor.NAME = cursor.getString(cursor.getColumnIndex(DBDoctor.NAME));
                doctor.EMAIL = cursor.getString(cursor.getColumnIndex(DBDoctor.EMAIL));
                doctor.PHONE = cursor.getString(cursor.getColumnIndex(DBDoctor.PHONE));
                doctor.SPECIALITY = cursor.getString(cursor.getColumnIndex(DBDoctor.SPECIALITY));
                doctor.HOSPITAL = cursor.getString(cursor.getColumnIndex(DBDoctor.HOSPITAL));
                doctor.PHOTO = cursor.getString(cursor.getColumnIndex(DBDoctor.PHOTO));
                arrayList.add(doctor);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            L.log("Error fecthing doctors: "+e.toString());
        }
        return arrayList;
    }

    public ArrayList<Profile> getmembers(){
        ArrayList<Profile> arrayList = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(DBProfile.TABLE_NAME, null, DBProfile.RELATIONSHIP+"!=?", new String[]{"none"}, null, null,null, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                Profile profile = new Profile();
                profile.ID = cursor.getString(cursor.getColumnIndex(DBProfile.ID));
                profile.NAME = cursor.getString(cursor.getColumnIndex(DBProfile.NAME));
                profile.AGE = cursor.getString(cursor.getColumnIndex(DBProfile.AGE));
                profile.HEIGHT = cursor.getString(cursor.getColumnIndex(DBProfile.HEIGHT));
                profile.WEIGHT = cursor.getString(cursor.getColumnIndex(DBProfile.WEIGHT));
                profile.PHOTO = cursor.getString(cursor.getColumnIndex(DBProfile.PHOTO));
                profile.RELATIONSHIP = cursor.getString(cursor.getColumnIndex(DBProfile.RELATIONSHIP));

                arrayList.add(profile);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            L.log(e.toString());
        }
        return arrayList;
    }

    public void registerMyself(String name, String age, String height, String weight, String gender, String phone){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBProfile.NAME, name);
            contentValues.put(DBProfile.AGE, age);
            contentValues.put(DBProfile.HEIGHT, height);
            contentValues.put(DBProfile.WEIGHT, weight);
            contentValues.put(DBProfile.GENDER, gender);
            contentValues.put(DBProfile.PHOTO, "");
            contentValues.put(DBProfile.PHONE, phone);
            contentValues.put(DBProfile.RELATIONSHIP, "none");
            db.insert(DBProfile.TABLE_NAME, null, contentValues);
            db.close();
            L.log("registered");
        }catch (Exception e){
            L.log(e.getMessage());
        }
    }

    public Profile getMyProfileMain(){
        SQLiteDatabase db = this.getReadableDatabase();
        Profile myProfile = null;
        try {
            Cursor cursor = db.query(DBProfile.TABLE_NAME, null, DBProfile.RELATIONSHIP + "=?", new String[]{"none"}, null, null, null, "1");
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                myProfile = new Profile();
                myProfile.ID = cursor.getString(cursor.getColumnIndex(DBProfile.ID));
                myProfile.NAME = cursor.getString(cursor.getColumnIndex(DBProfile.NAME));
                myProfile.AGE = cursor.getString(cursor.getColumnIndex(DBProfile.AGE));
                myProfile.GENDER = cursor.getString(cursor.getColumnIndex(DBProfile.GENDER));
                myProfile.HEIGHT = cursor.getString(cursor.getColumnIndex(DBProfile.HEIGHT));
                myProfile.WEIGHT = cursor.getString(cursor.getColumnIndex(DBProfile.WEIGHT));
                myProfile.PHONE = cursor.getString(cursor.getColumnIndex(DBProfile.PHONE));
                myProfile.RELATIONSHIP = cursor.getString(cursor.getColumnIndex(DBProfile.RELATIONSHIP));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();

        }catch (Exception e){
            L.log(e.getMessage());
        }
        return myProfile;
    }

    public void updateMyProfile(String name,String age, String photo, String height,String weight,String phone,String gender){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBProfile.NAME, name);
            contentValues.put(DBProfile.AGE, age);
            contentValues.put(DBProfile.PHOTO, photo);
            contentValues.put(DBProfile.HEIGHT, height);
            contentValues.put(DBProfile.WEIGHT, weight);
            contentValues.put(DBProfile.PHONE, phone);
            contentValues.put(DBProfile.GENDER, gender);
            db.update(DBProfile.TABLE_NAME, contentValues, DBProfile.RELATIONSHIP + "=?", new String[]{"none"});
            db.close();
            L.log("Updated");
        }catch (Exception e){
            L.log("error updating own profile");
        }
    }

    public void setMyProfilePic(String id, String photo){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBProfile.PHOTO, photo);
            db.update(DBProfile.TABLE_NAME, contentValues, DBProfile.ID + "=?", new String[]{id});
            db.close();
            L.log("photo set");
        }catch (Exception e){
            L.log(e.getMessage());
        }
    }





}

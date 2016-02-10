package com.newfeds.icare.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.newfeds.icare.R;

public class EditDoctor extends AppCompatActivity {

    Button buttonEditDoctorChooseFromGallary;
    ImageView imageViewEditDoctor;

    private EditText editTextEditDoctorName;
    private EditText editTextEditDoctorPhone;
    private EditText editTextEditDoctorEmail;
    private EditText editTextEditDoctorHospital;
    private EditText editTextEditDoctorSpeciality;

    private Button buttonEditDoctorFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        imageViewEditDoctor = (ImageView) findViewById(R.id.imageViewEditDoctor);

        editTextEditDoctorName = (EditText) findViewById(R.id.editTextEditDoctorName);
        editTextEditDoctorPhone = (EditText) findViewById(R.id.editTextEditDoctorPhone);
        editTextEditDoctorEmail = (EditText) findViewById(R.id.editTextEditDoctorEmail);
        editTextEditDoctorHospital = (EditText) findViewById(R.id.editTextEditDoctorHospital);
        editTextEditDoctorSpeciality = (EditText) findViewById(R.id.editTextEditDoctorSpeciality);


        buttonEditDoctorChooseFromGallary = (Button) findViewById(R.id.buttonEditDoctorChooseFromGallary);
    }
}

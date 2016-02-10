package com.newfeds.icare.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.newfeds.icare.R;
import com.newfeds.icare.constants.Const;
import com.newfeds.icare.helper.Crypto;
import com.newfeds.icare.helper.DBhelper;

public class RegisterActivity extends AppCompatActivity {


    private EditText editTextName;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextAge;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private EditText editTextPhone;
    private Spinner spinnerGender;

    Button buttonRegister;

    DBhelper dBhelper = null;

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        editTextName = (EditText) findViewById(R.id.editTextRegisterName);
        editTextPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextRegisterConfirmPassword);
        editTextAge = (EditText) findViewById(R.id.editTextRegisterAge);
        editTextHeight = (EditText) findViewById(R.id.editTextRegisterHeight);
        editTextWeight = (EditText) findViewById(R.id.editTextRegisterWeight);
        editTextPhone = (EditText) findViewById(R.id.editTextRegisterPhone);

        spinnerGender = (Spinner) findViewById(R.id.spinnerRegisterGender);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        dBhelper = new DBhelper(this);
        
        sharedPreferences = getSharedPreferences(Const.SHARED_PREF_KEY, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allOK = true;
                if(editTextName.getText().length()<6){
                    editTextName.setError("Must be at least 6 chars");
                    allOK = false;
                }
                if(editTextPassword.getText().length()<6){
                    editTextPassword.setError("Must be at least 6 chars");
                    allOK = false;
                }
                if(!editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString()))
                {
                    editTextConfirmPassword.setError("Passwords don't match");
                    allOK = false;
                }
                if(editTextAge.getText().length()==0){
                    editTextAge.setError("Set your age");
                    allOK = false;
                }
                if(editTextHeight.getText().length()==0){
                    editTextHeight.setError("Set your height");
                    allOK = false;
                }
                if(editTextWeight.getText().length()==0){
                    editTextWeight.setError("Set your weight");
                    allOK = false;
                }
                if(editTextPhone.getText().length()<9){
                    editTextPhone.setError("Mustbe atleast 9 chars");
                    allOK = false;
                }
                if(allOK){
                    if(dBhelper!=null){
                        String name = editTextName.getText().toString();
                        String age = editTextAge.getText().toString();
                        String height = editTextHeight.getText().toString();
                        String weight = editTextWeight.getText().toString();
                        String phone = editTextPhone.getText().toString();
                        String gender = spinnerGender.getSelectedItem().toString();
                        String password = editTextPassword.getText().toString();

                        dBhelper.registerMyself(name, age, height, weight, gender, phone);
                        editor.putString(Const.SHARED_PREF_PASS, Crypto.sha1(password));
                        editor.commit();

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Error connecting to database", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}

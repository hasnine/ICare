package com.newfeds.icare.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.newfeds.icare.R;
import com.newfeds.icare.constants.Const;
import com.newfeds.icare.helper.Crypto;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences =null;
    private SharedPreferences.Editor editor = null;

    Button buttonLogin;

    EditText editTextLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBhelper dBhelper = new DBhelper(this);
        dBhelper.getMyProfileMain();

        sharedPreferences = getSharedPreferences(Const.SHARED_PREF_KEY, MODE_PRIVATE);

        final String password = sharedPreferences.getString(Const.SHARED_PREF_PASS,"");
        L.log(password);
        if(password.length()==0){
            Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
            startActivity(intent);
        }

        editTextLoginPassword = (EditText) findViewById(R.id.editTextLoginPassword);



        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editPass = Crypto.sha1(editTextLoginPassword.getText().toString());
                if(!editPass.equals(password)){
                    editTextLoginPassword.setError("Password mismatch");
                }else{
                    Intent intent = new Intent(getBaseContext(), Dashboard.class);
                    startActivity(intent);
                }
            }
        });
    }
}

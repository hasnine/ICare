package com.newfeds.icare.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.newfeds.icare.R;
import com.newfeds.icare.constants.Const;
import com.newfeds.icare.helper.L;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    EditText editTextEmergencyNumber;
    Button buttonSaveSettings;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences = getContext().getSharedPreferences(Const.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editTextEmergencyNumber = (EditText) getView().findViewById(R.id.editTextEmergency);
        buttonSaveSettings = (Button) getView().findViewById(R.id.buttonSaveSettings);

        String emergency_number = sharedPreferences.getString(Const.SHARED_PREF_EMEREGENCY,"");
        L.log(emergency_number);
        editTextEmergencyNumber.setText(emergency_number);

        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(Const.SHARED_PREF_EMEREGENCY, editTextEmergencyNumber.getText().toString());
                editor.commit();
                Toast.makeText(getContext(),"Saved", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

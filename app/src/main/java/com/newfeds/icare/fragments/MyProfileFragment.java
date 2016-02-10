package com.newfeds.icare.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.newfeds.icare.R;
import com.newfeds.icare.activities.AddDiet;
import com.newfeds.icare.activities.EditMyProfile;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Profile;

import java.io.File;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {


    public MyProfileFragment() {
        // Required empty public constructor
    }

    EditText editTextMyProfileName;
    EditText editTextMyProfileAge;
    EditText editTextMyProfileHeight;
    EditText editTextMyProfileWeight;
    EditText editTextMyProfilePhone;
    EditText editTextMyProfileGender;

    ImageView imageViewMyProfileView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.additem, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_item) {
            Intent intent = new Intent(getContext(), EditMyProfile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        L.log("My Profile on resume called");

        imageViewMyProfileView = (ImageView) getView().findViewById(R.id.imageViewMyProfilePhoto);

        editTextMyProfileName = (EditText) getView().findViewById(R.id.editTextMyProfileName);
        editTextMyProfileAge = (EditText) getView().findViewById(R.id.editTextMyProfileAge);
        editTextMyProfileHeight = (EditText) getView().findViewById(R.id.editTextMyProfileHeight);
        editTextMyProfileWeight = (EditText) getView().findViewById(R.id.editTextMyProfileWeight);
        editTextMyProfilePhone = (EditText) getView().findViewById(R.id.editTextMyProfilePhone);
        editTextMyProfileGender = (EditText) getView().findViewById(R.id.editTextMyProfileGender);


        try {
            DBhelper dBhelper = new DBhelper(getContext());
            Profile myprofile = dBhelper.getMyProfileMain();

            Glide.clear(imageViewMyProfileView);
            if(myprofile.PHOTO!=null && !myprofile.PHOTO.equals("")){
                Glide.with(this).load(new File(myprofile.PHOTO))
                        .skipMemoryCache(true)
                        .signature(new StringSignature(UUID.randomUUID().toString()))
                        .into(imageViewMyProfileView);
            }
            editTextMyProfileName.setText(myprofile.NAME);
            editTextMyProfileAge.setText(myprofile.AGE);
            editTextMyProfileHeight.setText(myprofile.HEIGHT);
            editTextMyProfileWeight.setText(myprofile.WEIGHT);
            editTextMyProfilePhone.setText(myprofile.PHONE);
            editTextMyProfileGender.setText(myprofile.GENDER);
        }catch (Exception e){
            L.log(e.getMessage());
        }
    }
}

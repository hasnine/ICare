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
import com.newfeds.icare.activities.EditMember;
import com.newfeds.icare.activities.MemberDashboard;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Profile;

import java.io.File;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */

public class MemberDetailsFragment extends Fragment {


    String memberid = null;

    EditText editTextName;
    EditText editTextAge;
    EditText editTextWeight;
    EditText editTextHeight;
    EditText editTextRelationship;
    EditText editTextPhone;

    ImageView imageViewMemberDetailPhoto;


    public MemberDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        memberid = MemberDashboard.memberId;



        return inflater.inflate(R.layout.fragment_member_details, container, false);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edititem, menu);


        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_edit_item) {
            Intent intent = new Intent(getContext(), EditMember.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        imageViewMemberDetailPhoto = (ImageView) getView().findViewById(R.id.imageViewMemberDetailPhoto);

        editTextName = (EditText) getView().findViewById(R.id.editTextMemberDetailName);
        editTextAge = (EditText) getView().findViewById(R.id.editTextMemberDetailAge);
        editTextWeight = (EditText) getView().findViewById(R.id.editTextMemberDetailWeight);
        editTextHeight = (EditText) getView().findViewById(R.id.editTextMemberDetailHeight);
        editTextPhone = (EditText) getView().findViewById(R.id.editTextMemberDetailPhone);
        editTextRelationship = (EditText) getView().findViewById(R.id.editTextMemberDetailRelationship);
        DBhelper dBhelper = new DBhelper(getContext());

        Profile profile = dBhelper.getMember(memberid);
        L.log("Member detail resumed");
        L.log("member name:" + profile.NAME);

        Glide.clear(imageViewMemberDetailPhoto);
        Glide.with(this).load(new File(profile.PHOTO))
                .skipMemoryCache(true)
                .signature(new StringSignature(UUID.randomUUID().toString()))
                .into(imageViewMemberDetailPhoto);

        editTextName.setText(profile.NAME);
        editTextAge.setText(profile.AGE);
        editTextHeight.setText(profile.HEIGHT);
        editTextWeight.setText(profile.WEIGHT);
        editTextPhone.setText(profile.PHONE);
        editTextRelationship.setText(profile.RELATIONSHIP);

    }
}

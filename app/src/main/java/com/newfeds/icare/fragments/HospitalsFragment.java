package com.newfeds.icare.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.newfeds.icare.R;
import com.newfeds.icare.activities.MapsActivity;
import com.newfeds.icare.adapters.HospitalsAdapter;
import com.newfeds.icare.model.Hospital;

import java.util.ArrayList;

public class HospitalsFragment extends Fragment {

    ListView listViewHospitalsList;

    public HospitalsFragment() {
    }

    public static final String HOSP_NAME= "com.newfeds.icare.hosp.name";
    public static final String HOSP_LAT = "com.newfeds.icare.hosp.lat";
    public static final String HOSP_LON = "com.newfeds.icare.hosp.lon";

    @Override
    public void onResume() {
        super.onResume();
        listViewHospitalsList = (ListView) getView().findViewById(R.id.listViewHostpitalList);
        final ArrayList<Hospital> hospitals = new ArrayList<>();
        hospitals.add(new Hospital("Square Hospital", "23.7528239","90.3830035"));
        hospitals.add(new Hospital("City Hospital", "23.7541658","90.365304"));
        hospitals.add(new Hospital("IBN Sina Hospital", "23.7516749","90.3706144"));
        hospitals.add(new Hospital("Bangladesh Eye Hospital", "23.7514851","90.3667905"));

        HospitalsAdapter hospitalsAdapter = new HospitalsAdapter(getContext(),hospitals);
        listViewHospitalsList.setAdapter(hospitalsAdapter);
        listViewHospitalsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                intent.putExtra(HOSP_NAME, hospitals.get(position).name);
                intent.putExtra(HOSP_LAT,hospitals.get(position).lat);
                intent.putExtra(HOSP_LON,hospitals.get(position).lon);
                startActivity(intent);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospitals, container, false);
    }

}

package com.newfeds.icare.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.newfeds.icare.R;
import com.newfeds.icare.activities.AddDoctor;
import com.newfeds.icare.adapters.DoctorsAdapter;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Doctor;

import java.io.File;
import java.util.ArrayList;


public class DoctorsFragment extends Fragment {

    DBhelper dBhelper;
    public DoctorsFragment() {
    }

    ListView listViewDoctorsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.additem, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onResume() {
        super.onResume();
        listViewDoctorsList = (ListView) getView().findViewById(R.id.listViewDoctorsList);
        try{
            dBhelper = new DBhelper(getActivity());
            final ArrayList<Doctor> doctors = dBhelper.getDoctors();
            L.log("Doctors: "+ doctors.size());

            DoctorsAdapter doctorsAdapter = new DoctorsAdapter(getContext(),doctors);
            listViewDoctorsList.setAdapter(doctorsAdapter);


            listViewDoctorsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final CharSequence[] items = { "Delete",
                            "Cancel" };

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("What do you want to do?");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (items[which].equals("Delete")) {
                                dBhelper.deleteDoctor(doctors.get(position).ID);
                                L.log("id: "+ doctors.get(position).ID);
                                File file = new File(doctors.get(position).PHOTO);
                                file.delete();

                                Toast.makeText(getContext(),"Deleted", Toast.LENGTH_SHORT).show();
                                onResume();
                            } else if (items[which].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();

                    return true;
                }
            });
        }catch (Exception e){
            L.log(e.toString());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_item) {
            Intent intent = new Intent(getContext(), AddDoctor.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

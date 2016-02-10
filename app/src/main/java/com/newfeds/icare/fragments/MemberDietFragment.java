package com.newfeds.icare.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.newfeds.icare.activities.AddDiet;
import com.newfeds.icare.activities.AddPrescription;
import com.newfeds.icare.activities.AddVaccination;
import com.newfeds.icare.activities.MemberDashboard;
import com.newfeds.icare.adapters.DietsAdapter;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Diet;

import java.io.File;
import java.util.ArrayList;


public class MemberDietFragment extends Fragment {
    ListView listViewDietList;
    DBhelper dBhelper;
    public MemberDietFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        dBhelper = new DBhelper(getContext());
        return inflater.inflate(R.layout.fragment_member_diet, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        listViewDietList = (ListView) getView().findViewById(R.id.listViewDietList);
        final ArrayList<Diet> diets = dBhelper.getDiet(MemberDashboard.memberId);
        DietsAdapter dietsAdapter = new DietsAdapter(getContext(), diets);
        listViewDietList.setAdapter(dietsAdapter);

        listViewDietList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                            dBhelper.deleteDiet(diets.get(position).ID);
                            L.log("id: "+ diets.get(position).ID);
                            //File file = new File(diets.get(position).PHOTO);
                            //file.delete();

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
            Intent intent = new Intent(getContext(), AddDiet.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

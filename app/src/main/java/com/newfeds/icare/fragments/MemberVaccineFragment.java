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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.newfeds.icare.R;
import com.newfeds.icare.activities.AddMember;
import com.newfeds.icare.activities.AddVaccination;
import com.newfeds.icare.activities.MemberDashboard;
import com.newfeds.icare.adapters.VaccinesAdapter;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Vaccine;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberVaccineFragment extends Fragment {


    ListView listViewMemberVaccine ;
    DBhelper dBhelper = null;
    public MemberVaccineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_member_vaccine, container, false);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.additem, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onResume() {
        super.onResume();
        dBhelper = new DBhelper(getContext());
        listViewMemberVaccine = (ListView) getView().findViewById(R.id.listViewMemberVaccines);
        final ArrayList<Vaccine> vaccines = dBhelper.getVaccines(MemberDashboard.memberId);

        VaccinesAdapter vaccinesAdapter = new VaccinesAdapter(getContext(),vaccines);
        listViewMemberVaccine.setAdapter(vaccinesAdapter);

        listViewMemberVaccine.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {"Delete",
                        "Cancel"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("What do you want to do?");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals("Delete")) {
                            dBhelper.deleteVaccine(vaccines.get(position).ID);
                            L.log("id: " + vaccines.get(position).ID);

                            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_item) {
            Intent intent = new Intent(getContext(), AddVaccination.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

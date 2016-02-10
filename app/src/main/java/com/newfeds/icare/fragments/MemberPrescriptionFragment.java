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
import com.newfeds.icare.activities.AddPrescription;
import com.newfeds.icare.activities.AddVaccination;
import com.newfeds.icare.activities.MemberDashboard;
import com.newfeds.icare.adapters.PrescriptionsAdapter;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Prescription;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberPrescriptionFragment extends Fragment {

    ListView listViewMemberPrescription;
    DBhelper dBhelper;

    public MemberPrescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        dBhelper = new DBhelper(getContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member_prescription, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.additem, menu);



        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onResume() {
        super.onResume();
        listViewMemberPrescription = (ListView) getView().findViewById(R.id.listViewMemberPrescription);
        final ArrayList<Prescription> prescriptions = dBhelper.getPrescriptions(MemberDashboard.memberId);
        PrescriptionsAdapter prescriptionsAdapter = new PrescriptionsAdapter(getContext(), prescriptions );
        listViewMemberPrescription.setAdapter(prescriptionsAdapter);
        listViewMemberPrescription.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                            dBhelper.deletePrescription(prescriptions.get(position).ID);
                            L.log("id: " + prescriptions.get(position).ID);
                            File file = new File(prescriptions.get(position).PHOTO);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_item) {
            Intent intent = new Intent(getContext(), AddPrescription.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

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
import com.newfeds.icare.activities.AddMember;
import com.newfeds.icare.activities.MemberDashboard;
import com.newfeds.icare.adapters.MembersAdapter;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Profile;

import java.io.File;
import java.util.ArrayList;

public class MembersFragment extends Fragment {
    public static final String MEMBER_ID_KEY = "com.newfeds.icare.member_id";

    public MembersFragment() {
    }


    ListView listViewMembersList;
    ArrayList<Profile> membersList;
    DBhelper dBhelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        L.log("Member fragment creating");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        L.log("memeber fragment creating view");



        return inflater.inflate(R.layout.fragment_members, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.additem, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public void onResume() {
        super.onResume();
        listViewMembersList = (ListView) getView().findViewById(R.id.listViewMembersList);

        try {
            dBhelper = new DBhelper(getActivity());
            membersList = dBhelper.getmembers();
            L.log("Members Size: "+ membersList.size());

            MembersAdapter membersAdapter = new MembersAdapter(getActivity(), membersList);
            listViewMembersList.setAdapter(membersAdapter);
        }catch (Exception e){
            L.log(e.toString());
        }

        listViewMembersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MemberDashboard.class);
                intent.putExtra(MEMBER_ID_KEY, membersList.get(position).ID);
                startActivity(intent);
            }
        });

        listViewMembersList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                            dBhelper.deleteMember(membersList.get(position).ID);
                            L.log("id: "+ membersList.get(position).ID);
                            File file = new File(membersList.get(position).PHOTO);
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

        L.log("Member fragment resumed");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_item) {
            Intent intent = new Intent(getContext(), AddMember.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.newfeds.icare.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;
import com.newfeds.icare.R;
import com.newfeds.icare.activities.AddDiet;
import com.newfeds.icare.adapters.DashboardFactsAdapter;
import com.newfeds.icare.constants.Const;
import com.newfeds.icare.helper.L;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;

    String emergency_number = "";

    ListView listViewDashboardFacts;
    ImageView imageViewDashboard;

    public HomeFragment(){
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.log("Home fragment creating");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        L.log("Home fragment creating view");
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        imageViewDashboard = (ImageView) getView().findViewById(R.id.imageViewDashboardLogo);
        sharedPreferences = getContext().getSharedPreferences(Const.SHARED_PREF_KEY,Context.MODE_PRIVATE);
        emergency_number = sharedPreferences.getString(Const.SHARED_PREF_EMEREGENCY,"");

        ViewAnimator.animate(imageViewDashboard)
                .translationY(-210,0)
                .alpha(0,1)
                .descelerate()
                .duration(2000)
                .start();


        InputStream inputStream;
        listViewDashboardFacts = (ListView) getView().findViewById(R.id.listViewDashboardFacts);
        try {
            inputStream = getContext().getAssets().open("dashboard.json");
            BufferedReader in=
                    new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String str;
            StringBuffer buf = new StringBuffer();
            while ((str=in.readLine()) != null) {
                buf.append(str);
            }

            in.close();
            inputStream.close();
            String result = buf.toString();

            ArrayList<String> facts = getFactList(result);
            DashboardFactsAdapter dashboardFactsAdapter = new DashboardFactsAdapter(getContext(),facts);
            listViewDashboardFacts.setAdapter(dashboardFactsAdapter);





        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.callitem, menu);



        super.onCreateOptionsMenu(menu, inflater);

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_call_item) {
            if(emergency_number.equals("")){
                Toast.makeText(getContext(),"Configure Emergency Number in settings", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + emergency_number));
                startActivity(intent);
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> getFactList(String json){
        ArrayList<String> factList = new ArrayList<>();
        try{
            JSONObject mainObject = new JSONObject(json);
            JSONArray jsonArray = mainObject.getJSONArray("data");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fact = jsonObject.getString("fact");
                factList.add(fact);
            }

        }catch (Exception e){
            L.log(e.getMessage());
        }
        return  factList;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

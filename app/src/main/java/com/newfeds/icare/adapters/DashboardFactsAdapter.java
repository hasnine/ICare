package com.newfeds.icare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newfeds.icare.R;
import com.newfeds.icare.model.Profile;

import java.io.File;
import java.util.List;

/**
 * Created by GT on 1/26/2016.
 */
public class DashboardFactsAdapter extends ArrayAdapter<String> {
    Context context;



    public DashboardFactsAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String fact = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.dashboard_facts_card,parent,false);
        }

        TextView textViewFact = (TextView) convertView.findViewById(R.id.textViewdashboardFactsText);
        textViewFact.setText(fact);

        return convertView;
    }
}

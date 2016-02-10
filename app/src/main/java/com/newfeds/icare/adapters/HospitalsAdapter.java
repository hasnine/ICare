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
import com.newfeds.icare.model.Doctor;
import com.newfeds.icare.model.Hospital;

import java.io.File;
import java.util.List;

/**
 * Created by GT on 1/26/2016.
 */
public class HospitalsAdapter extends ArrayAdapter<Hospital> {

    Context context;

    public HospitalsAdapter(Context context, List<Hospital> objects) {
        super(context, 0, objects);
        this.context  = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Hospital hospital = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.hospital_card,parent,false);
        }


        TextView textView = (TextView) convertView.findViewById(R.id.textviewHosptialName);

        textView.setText(hospital.name);

        return convertView;
    }

}

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
import com.newfeds.icare.model.Prescription;
import com.newfeds.icare.model.Profile;

import java.io.File;
import java.util.List;

/**
 * Created by GT on 1/25/2016.
 */
public class PrescriptionsAdapter extends ArrayAdapter<Prescription> {
    Context context;
    public PrescriptionsAdapter(Context context, List<Prescription> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Prescription prescription = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.prescription_card,parent,false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewPrescriptionPhoto);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textviewPrescriptionTitle);
        TextView textViewDetails = (TextView) convertView.findViewById(R.id.textViewPrescriptionDetails);


        if(prescription.PHOTO!=null && prescription.PHOTO.length()!=0){
            //ImageHelper.setImageInList(profile.PHOTO, imageView);
            Glide.with(context).load(new File(prescription.PHOTO))
                    .into(imageView);
        }
        textViewTitle.setText(prescription.TITLE);
        textViewDetails.setText(prescription.DESCRIPTION);

        return convertView;
    }
}

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


import java.io.File;
import java.util.List;

/**
 * Created by GT on 1/20/2016.
 */
public class DoctorsAdapter extends ArrayAdapter<Doctor>
{
    Context context;
    public DoctorsAdapter(Context context, List<Doctor> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctor = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.member_card,parent,false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewPersonPhoto);
        TextView textView = (TextView) convertView.findViewById(R.id.textviewPersonName);

        if(doctor.PHOTO!=null && doctor.PHOTO.length()!=0){
            //ImageHelper.setImageInList(profile.PHOTO, imageView);
            Glide.with(context).load(new File(doctor.PHOTO))
                    .into(imageView);
        }
        textView.setText(doctor.NAME);

        return convertView;
    }


}

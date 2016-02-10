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
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Profile;
import com.newfeds.icare.model.Vaccine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.newfeds.icare.R.id.textviewVaccineDate;

/**
 * Created by GT on 1/24/2016.
 */
public class VaccinesAdapter extends ArrayAdapter<Vaccine> {

    Context context;
    public VaccinesAdapter(Context context, List<Vaccine> objects) {
        super(context,0, objects);
        this.context  = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vaccine vaccine = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.vaccine_card,parent,false);
        }


        TextView textViewName = (TextView) convertView.findViewById(R.id.textviewVaccineName);
        TextView textViewDate = (TextView) convertView.findViewById(R.id.textviewVaccineDate);
        TextView textViewReminder = (TextView) convertView.findViewById(R.id.textViewVaccinesReminder);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        long datetimeMillis = Long.valueOf(vaccine.DATETIME).longValue();
        calendar.setTimeInMillis(datetimeMillis);

        String time = sdfDate.format(calendar.getTime());

        textViewName.setText("Detail:" + vaccine.DETAIL);
        textViewDate.setText("Time:"+time);
        textViewReminder.setText("Reminder:"+vaccine.REMINDER);

        return convertView;
    }
}

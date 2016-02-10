package com.newfeds.icare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.newfeds.icare.R;
import com.newfeds.icare.model.Appointment;
import com.newfeds.icare.model.Diet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by GT on 1/25/2016.
 */
public class AppointsmentAdapter extends ArrayAdapter<Appointment> {
    Context context;

    public AppointsmentAdapter(Context context, List<Appointment> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Appointment appointment = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.appointment_card,parent,false);
        }


        TextView textViewAppointmentTitle = (TextView) convertView.findViewById(R.id.textviewAppointmentTitle);
        TextView textViewAppointmentDoctor = (TextView) convertView.findViewById(R.id.textviewAppointmentDoctor);
        TextView textViewAppointmentDescription = (TextView) convertView.findViewById(R.id.textviewAppointmentDescription);
        TextView textViewAppointmentTime = (TextView) convertView.findViewById(R.id.textviewAppointmentTime);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        long datetimeMillis = Long.valueOf(appointment.DATETIME).longValue();
        calendar.setTimeInMillis(datetimeMillis);

        String time = sdfDate.format(calendar.getTime());

        textViewAppointmentTitle.setText("Title: "+ appointment.TITLE);
        textViewAppointmentDoctor.setText("Doc: "+ appointment.DOCTOR_NAME);
        textViewAppointmentTime.setText("Time: "+ time);
        textViewAppointmentDescription.setText(""+appointment.DESCRIPTION);

        return convertView;
    }
}

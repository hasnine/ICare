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
import com.newfeds.icare.model.Diet;
import com.newfeds.icare.model.Profile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by GT on 1/25/2016.
 */

public class DietsAdapter extends ArrayAdapter<Diet> {
    Context context;


    public DietsAdapter(Context context, List<Diet> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Diet diet = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.diet_card,parent,false);
        }


        TextView textViewMenu = (TextView) convertView.findViewById(R.id.textviewDietTitle);
        TextView textViewDietTime = (TextView) convertView.findViewById(R.id.textViewDietTime);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        long datetimeMillis = Long.valueOf(diet.DATETIME).longValue();
        calendar.setTimeInMillis(datetimeMillis);

        String time = sdfDate.format(calendar.getTime());

        textViewMenu.setText(diet.MENU);
        textViewDietTime.setText("Time: " + time);
        return convertView;
    }
}

package com.newfeds.icare.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.newfeds.icare.BroadcastReceivers.AlarmReceiver;
import com.newfeds.icare.R;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;

import java.util.Calendar;

public class AddDiet extends AppCompatActivity {

    EditText editTextAddDietMenu;
    Button buttonAddDietDate;
    Button buttonAddDietTime;
    CheckBox checkBoxAddDietReminder;
    CheckBox checkBoxAddDietDailyRepeat;
    Button buttonAddDiet;

    DBhelper dBhelper;

    int cDay, cMonth, cYear;
    int cHour, cMinute;

    public static final String TIMESTAMP = "com.newfeds.icare.adddiet.timestamp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dBhelper = new DBhelper(this);

        editTextAddDietMenu=(EditText)findViewById(R.id.editTextAddDietMenu);
        buttonAddDietDate =(Button)findViewById(R.id.editButtonAddDietDate);
        buttonAddDietTime =(Button)findViewById(R.id.editButtonAddDietTime);
        checkBoxAddDietReminder=(CheckBox)findViewById(R.id.checkBoxAddDietReminder);
        checkBoxAddDietDailyRepeat=(CheckBox)findViewById(R.id.checkBoxAddDietDailyRepeat);
        buttonAddDiet=(Button)findViewById(R.id.buttonAddDiet);

        buttonAddDietDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        buttonAddDietTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "Time Picker");
            }
        });

        buttonAddDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allOk = true;

                if(allOk){
                    if(checkBoxAddDietReminder.isChecked()){
                        if(checkBoxAddDietDailyRepeat.isChecked()){
                            setRepeatingAlarm();
                            dBhelper.inputDiet(MemberDashboard.memberId,editTextAddDietMenu.getText().toString(),String.valueOf(getAlarmTimeInMilis()),
                                    "1","1");
                        }else{
                            setAlarm();
                            dBhelper.inputDiet(MemberDashboard.memberId,editTextAddDietMenu.getText().toString(),String.valueOf(getAlarmTimeInMilis()),
                                    "1","0");
                        }
                    }else{
                        dBhelper.inputDiet(MemberDashboard.memberId,editTextAddDietMenu.getText().toString(),String.valueOf(getAlarmTimeInMilis()),
                                "0","0");
                    }

                    onBackPressed();
                }
            }
        });
    }

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            L.log("Init: year: " + year+" month: "+ month+ " day: "+ day);
            return new DatePickerDialog(getActivity(), this, year,month,day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //
            cMonth = monthOfYear;
            cDay = dayOfMonth;
            cYear = year;

            L.log("After: year: " + cYear+" month: "+ cMonth+ " day: "+cDay);
            buttonAddDietDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    }


    public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            L.log("Init: hour: " + hour+" minute: "+ minute);
            return new TimePickerDialog(getActivity(),this,hour,minute,android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            cMinute = minute;
            cHour = hourOfDay;
            L.log("After: hour: " +cHour+" minute: "+ cMinute);
            buttonAddDietTime.setText(minute + ":" + hourOfDay);
        }
    }

    public long getAlarmTimeInMilis(){
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.YEAR, cYear);
        alarmTime.set(Calendar.MONTH, cMonth);
        alarmTime.set(Calendar.DAY_OF_MONTH, cDay);
        alarmTime.set(Calendar.HOUR_OF_DAY, cHour);
        alarmTime.set(Calendar.MINUTE, cMinute);
        return alarmTime.getTimeInMillis();
    }


    public void setRepeatingAlarm(){

        L.log("Finally: year: " + cYear + " month: " + cMonth + " day:" + cDay + " hour:" + cHour + " minute:" + cMinute);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra(TIMESTAMP, String.valueOf(getAlarmTimeInMilis()));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getAlarmTimeInMilis(),24*60*60*1000, pendingIntent);
        Toast.makeText(getBaseContext(), "Alarm set", Toast.LENGTH_SHORT).show();
    }

    public void setAlarm(){

        L.log("Finally: year: " + cYear + " month: " + cMonth + " day:" + cDay + " hour:" + cHour + " minute:" + cMinute);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra(TIMESTAMP,String.valueOf(getAlarmTimeInMilis()));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, getAlarmTimeInMilis(), pendingIntent);
        Toast.makeText(getBaseContext(), "Alarm set", Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

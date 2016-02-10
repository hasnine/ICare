package com.newfeds.icare.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddVaccination extends AppCompatActivity {

    EditText editTextAddVaccineName;
    Button buttonAddVaccineDate;
    Button buttonAddVaccineTime;
    EditText editTextAddVaccineDetails;
    CheckBox checkBoxAddVaccinationReminder;
    Button buttonAddVaccination;

    int cDay, cMonth, cYear;
    int cHour, cMinute;

    public static final String TIMESTAMP= "com.newfeds.icare.vaccination.timestamp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccination);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonAddVaccineDate = (Button) findViewById(R.id.buttonAddVaccinationDate);
        editTextAddVaccineName = (EditText) findViewById(R.id.editTextAddVaccinationName);
        buttonAddVaccineTime = (Button) findViewById(R.id.buttonAddVaccinationTime);
        editTextAddVaccineDetails = (EditText) findViewById(R.id.editTextAddVaccinationDetails);
        checkBoxAddVaccinationReminder = (CheckBox) findViewById(R.id.checkBoxAddVaccinationReminder);
        buttonAddVaccination = (Button) findViewById(R.id.buttonAddVaccination);

        final DBhelper dBhelper = new DBhelper(this);

        buttonAddVaccineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        buttonAddVaccineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.show(getFragmentManager(), "Time Picker");
            }
        });

        buttonAddVaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.log("Add vaccination pressed");
                boolean allOK = true;
                if(editTextAddVaccineName.getText().length()==0){
                    editTextAddVaccineName.setError("Give vaccination name");
                    allOK = false;
                }
                if(buttonAddVaccineDate.getText().length()==0){
                    buttonAddVaccineDate.setError("Set Date");
                    allOK = false;
                }
                if(buttonAddVaccineTime.getText().length()==0){
                    buttonAddVaccineTime.setError("Set time");
                    allOK = false;
                }
                if(editTextAddVaccineDetails.getText().length()==0){
                    editTextAddVaccineDetails.setError("Write description");
                    allOK = false;
                }
                if(allOK){
                    if(checkBoxAddVaccinationReminder.isChecked()){
                        setAlarm();
                        dBhelper.inputVaccine(MemberDashboard.memberId,editTextAddVaccineDetails.getText().toString(),
                                "1",String.valueOf(getAlarmTimeInMilis()));
                        onBackPressed();
                    }else{
                        dBhelper.inputVaccine(MemberDashboard.memberId,editTextAddVaccineDetails.getText().toString(),
                                "0",String.valueOf(getAlarmTimeInMilis()));
                        onBackPressed();
                    }
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
            buttonAddVaccineDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
            buttonAddVaccineTime.setText(minute + ":" + hourOfDay);
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

    public void setAlarm(){

        L.log("Finally: year: " + cYear + " month: " + cMonth +" day:"+ cDay+" hour:"+ cHour + " minute:"+ cMinute);
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

package com.newfeds.icare.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.newfeds.icare.R;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;

import java.util.Calendar;

public class AddAppointments extends AppCompatActivity {

    EditText editTextAddAppointmentTitle;
    EditText editTextAddAppointmentDoctorName;
    EditText editTextAddAppointmentDescription;
    Button editButtonAddAppointmentDate;
    Button editButtonAddAppointmentTime;
    Button buttonAddAppointment;
    int cDay, cMonth, cYear;
    int cHour, cMinute;

    DBhelper dBhelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextAddAppointmentTitle=(EditText)findViewById(R.id.editTextAddAppointmentTitle);
        editTextAddAppointmentDoctorName=(EditText)findViewById(R.id.editTextAddAppointmentDoctorName);
        editTextAddAppointmentDescription=(EditText)findViewById(R.id.editTextAddAppointmentDescription);
        editButtonAddAppointmentDate=(Button)findViewById(R.id.editButtonAddAppointmentDate);
        editButtonAddAppointmentTime=(Button)findViewById(R.id.editButtonAddAppointmentTime);
        buttonAddAppointment=(Button)findViewById(R.id.buttonAddAppointment);

        dBhelper = new DBhelper(this);

        editButtonAddAppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        editButtonAddAppointmentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        buttonAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allOk = true;

                if(allOk){
                        dBhelper.inputAppointment(MemberDashboard.memberId,editTextAddAppointmentDoctorName.getText().toString(),
                                editTextAddAppointmentTitle.getText().toString(),editTextAddAppointmentDescription.getText().toString(),
                                String.valueOf(getAlarmTimeInMilis()));
                    Toast.makeText(getApplicationContext(),"Appointment Added",Toast.LENGTH_SHORT).show();
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

            L.log("Init: year: " + year + " month: " + month + " day: " + day);
            return new DatePickerDialog(getActivity(), this, year,month,day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //
            cMonth = monthOfYear;
            cDay = dayOfMonth;
            cYear = year;

            L.log("After: year: " + cYear+" month: "+ cMonth+ " day: "+cDay);
            editButtonAddAppointmentDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
            editButtonAddAppointmentTime.setText(minute + ":" + hourOfDay);
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

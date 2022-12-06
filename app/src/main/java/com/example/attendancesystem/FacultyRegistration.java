package com.example.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Calendar;

public class FacultyRegistration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.Faculty_registration_page);
        setContentView(R.layout.activity_faculty_registration);
        Intent intent = getIntent();
        Button getDateButton = (Button) findViewById(R.id.getDateButton);
        getDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TastyToast.makeText(getApplicationContext(), "Registered Successfully !",
                        TastyToast.LENGTH_LONG,
                        TastyToast.SUCCESS);
                mHandler.postDelayed(toastDelay, 1500);
            }
        });
    }

    private Runnable toastDelay = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        StringBuilder date = new StringBuilder();
        month += 1;
        if (dayOfMonth < 10) {
            date.append(0);
        }
        date.append(dayOfMonth);
        date.append('/');
        if (month < 10) {
            date.append(0);
        }
        date.append(month);
        date.append('/');
        date.append(year);
        EditText calendarText = (EditText) findViewById(R.id.dobEditText);
        calendarText.setText(date.toString());
    }
}
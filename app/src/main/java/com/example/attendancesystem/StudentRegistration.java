package com.example.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.attendancesystem.ui.StudentHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Calendar;

public class StudentRegistration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Handler mHandler = new Handler();
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.student_registration_page);

        setContentView(R.layout.activity_student_registration);
        //variables
        EditText nametext = (EditText) findViewById(R.id.nameEditText);
        EditText regtext = (EditText) findViewById(R.id.registrationNumberEditText);
        EditText emailtext = (EditText) findViewById(R.id.emailEditText);
        EditText passwordtext = (EditText) findViewById(R.id.password);
        //EditText dobtext = findViewById(R.id.password);
        EditText calendarText=(EditText) findViewById(R.id.dobEditText);
        Spinner courseSpinner = (Spinner)findViewById(R.id.branchSpinner);
        Spinner semSpinner = (Spinner)findViewById(R.id.semesterSpinner);
        Spinner sectionSpinner = (Spinner)findViewById(R.id.sectionSpinner);

        Intent intent=getIntent();
        Button getDateButton=(Button)findViewById(R.id.getDateButton);
        getDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        Button registerButton=(Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootnode = FirebaseDatabase.getInstance();
                reference = rootnode.getReference("Students");
                String name = nametext.getText().toString();
                String regno = regtext.getText().toString();
                String email = emailtext.getText().toString();
                String password = passwordtext.getText().toString();
                String dob = calendarText.getText().toString();
                String course = courseSpinner.getSelectedItem().toString();
                String sem = semSpinner.getSelectedItem().toString();
                String section = sectionSpinner.getSelectedItem().toString();

                StudentHelper helper = new StudentHelper(name, regno, email, password, dob, course, sem, section);

                reference.child(regno).setValue(helper);

                //reference.setValue("hello")
                TastyToast.makeText(getApplicationContext(), "Registered Successfully !",
                        TastyToast.LENGTH_LONG,
                        TastyToast.SUCCESS);
                mHandler.postDelayed(toastDelay,1500);
            }
        });
    }
    private Runnable toastDelay=new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        StringBuilder date=new StringBuilder();
        month+=1;
        if(dayOfMonth<10)
        {
            date.append(0);
        }
        date.append(dayOfMonth);
        date.append('/');
        if(month<10)
        {
            date.append(0);
        }
        date.append(month);
        date.append('/');
        date.append(year);
        EditText calendarText=(EditText) findViewById(R.id.dobEditText);
        calendarText.setText(date.toString());
    }

}
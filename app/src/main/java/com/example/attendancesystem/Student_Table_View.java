package com.example.attendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.example.attendancesystem.ui.StudentHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Student_Table_View extends AppCompatActivity {
    private Handler mHandler = new Handler();
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    ArrayList<String> Dates = new ArrayList<String>();
    ArrayList<String> Presence = new ArrayList<String>();
    //String[] Dates={"01/12/2020","02/12/2020","03/12/2020","04/12/2020","05/12/2020","06/12/2020"};
    //String[] Presence={"Present","Absent","Present","Absent","Present","Present"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_table_view);
        Intent intent=getIntent();
        Button btn=findViewById(R.id.button);
        btn.setText(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
        String Regno=intent.getStringExtra("RegNo");
        String courseName=intent.getStringExtra("Course");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students/180911206/attendance/1200");
        Query getData;
        getData = reference.orderByChild("regno").equalTo("180911206");
        Log.d("hello", "onCreate: ");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //Log.d("userdata", snapshot.toString());
                     //= snapshot.child("180911206").child("attendance").child("1200");

                    for (DataSnapshot s : snapshot.getChildren()) {
                        Log.d("q", s.toString());

                        AttendanceHelper at = s.getValue(AttendanceHelper.class);
                        Log.d("q", at.date);
                        Dates.add(at.date);
                        Presence.add(at.status);
                    }
                    setTitle(courseName);
                    addHeaders();
                    addData();

                    //StudentHelper post = snapshot.getValue(StudentHelper.class);
//                    for(DataSnapshot ds : snapshot.getChildren()) {
//                        String key = ds.getKey();
//                        String city = ds.child("city").getValue(String.class);
//                        String name = ds.child("name").getValue(String.class);
//                    }
                }else{
                    Log.d("nodata", "why?");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        setTitle(courseName);
//        addHeaders();
//        addData();
    }

    @NonNull
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        return tv;
    }

    public void addHeaders() {
        TableLayout tl = findViewById(R.id.studentAttendanceTable);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "Date", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "Marked As:", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tl.addView(tr, getTblLayoutParams());
    }

    public void addData() {
        int numDates = Dates.size();
        TableLayout tl = findViewById(R.id.studentAttendanceTable);
        for (int i = 0; i < numDates; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, Dates.get(i), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.gray)));
            if(Presence.get(i).equals("PRESENT")){
                tr.addView(getTextView(i + numDates, Presence.get(i), Color.GREEN, Typeface.NORMAL, ContextCompat.getColor(this, R.color.gray)));
            }else{
                tr.addView(getTextView(i + numDates, Presence.get(i), Color.RED, Typeface.NORMAL, ContextCompat.getColor(this, R.color.gray)));
            }

            tl.addView(tr, getTblLayoutParams());
        }
    }

    public void btnClick(View view) {
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("Students");
        String date=new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String status="PRESENT";
        AttendanceHelper helper = new AttendanceHelper(status,date);
        reference.child("180911206").child("attendance").child("1200").child(date).setValue(helper);

        //reference.setValue("hello")
        TastyToast.makeText(getApplicationContext(), "Attendance Updated!",
                TastyToast.LENGTH_LONG,
                TastyToast.SUCCESS);
        mHandler.postDelayed(toastDelay,1500);

    }
    private Runnable toastDelay=new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };
}
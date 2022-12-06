package com.example.attendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class Table_View extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student");

    String[] Dates={"01/12/2020","02/12/2020","03/12/2020","04/12/2020","05/12/2020","06/12/2020"};
    String[] Presence={"Present","Absent","Present","Absent","Present","Present"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table__view);
        Intent intent=getIntent();
        String Regno=intent.getStringExtra("RegNo");
        String courseName=intent.getStringExtra("Course");

        setTitle(courseName);
        addHeaders();
        addData();
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
        int numDates = Dates.length;
        TableLayout tl = findViewById(R.id.studentAttendanceTable);
        for (int i = 0; i < numDates; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, Dates[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.gray)));
            tr.addView(getTextView(i + numDates, Presence[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.gray)));
            tl.addView(tr, getTblLayoutParams());
        }
    }
}
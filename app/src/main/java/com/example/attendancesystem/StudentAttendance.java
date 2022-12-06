package com.example.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class StudentAttendance extends AppCompatActivity {
    String dummyCourses[]={"Cyber Security","Essentials of Management","Embedded Systems","Distributed Systems"};
    String dummyPercentages[]={"71%","71%","71%","71%"};
    CourseAdapter ca;
    ListView listView;
    String Name;
    String Regno;
    ArrayList<Pair<String,String>> Datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__attendance);
        Intent intent=getIntent();
        Name=intent.getStringExtra("Name");
        Regno=intent.getStringExtra("RegNo");
        Log.v("Test",Regno);
        Datas=getPairList();
        ca=new CourseAdapter(this,Datas);
        listView=(ListView) findViewById(R.id.courseWiseAttendance);
        listView.setAdapter(ca);
        setTitle(Name);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setIcon(R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("Do you want to delete "+dummyCourses[position])
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Datas.remove(position);
                                ca.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(StudentAttendance.this,Table_View.class);
                Log.v("Test",dummyCourses[position]);
                intent.putExtra("RegNo",Regno);
                intent.putExtra("Course",dummyCourses[position]);
                startActivity(intent);
            }
        });
    }
    private ArrayList<Pair<String,String>> getPairList()
    {
        ArrayList<Pair<String,String>> datas=new ArrayList<Pair<String,String>>();
        for(int i=0;i<dummyCourses.length;i++)
        {
            Pair<String,String> data=new Pair<String, String>(dummyCourses[i],dummyPercentages[i]);
            datas.add(data);
        }
        return datas;
    }
}
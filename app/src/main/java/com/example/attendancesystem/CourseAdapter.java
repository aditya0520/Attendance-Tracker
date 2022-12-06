package com.example.attendancesystem;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter{

    LayoutInflater mInflator;
    ArrayList<Pair<String, String>> datas;
    public CourseAdapter(@NotNull StudentAttendance c, ArrayList<Pair<String, String>> datas)
    {
        mInflator=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.datas=datas;
    }

    public CourseAdapter(Context a, ArrayList<Pair<String, String>> datas) {
        mInflator=LayoutInflater.from(a);
        this.datas=datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=mInflator.inflate(R.layout.student_course_list,null);
        TextView courseTV=view.findViewById(R.id.courseNameTV);
        TextView percentTV=view.findViewById(R.id.attendancePercentTV);
        courseTV.setText(datas.get(position).first);
        String currTxt=percentTV.getText().toString();
        percentTV.setText(currTxt+datas.get(position).second);
        return view;
    }
}

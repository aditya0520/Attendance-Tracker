package com.example.attendancesystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentAttendanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentAttendanceFragment extends Fragment {
    String dummyCourses[]={"Cyber Security","Essentials of Management","Embedded Systems","Distributed Systems"};
    String dummyPercentages[]={"71%","71%","71%","71%"};
    CourseAdapter ca;
    ListView listView;
    String Name;
    String Regno;
    ArrayList<Pair<String,String>> Datas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentAttendanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentAttendanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentAttendanceFragment newInstance(String param1, String param2) {
        StudentAttendanceFragment fragment = new StudentAttendanceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Datas=getPairList();
        ca=new CourseAdapter(getActivity(),Datas);
        View root = inflater.inflate(R.layout.fragment_student_attendance, container, false);
        listView=root.findViewById(R.id.courseWiseAttendance);
        listView.setAdapter(ca);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),Student_Table_View.class);
                Log.v("Test",dummyCourses[position]);
                intent.putExtra("RegNo",Regno);
                intent.putExtra("Course",dummyCourses[position]);
                startActivity(intent);
            }
        });
        return root;
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
package com.example.attendancesystem.ui.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.attendancesystem.AdminPage;
import com.example.attendancesystem.R;
import com.example.attendancesystem.StudentAttendance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import com.example.attendancesystem.ui.student.StudentViewModel;
public class StudentFragment extends Fragment {

    private StudentViewModel studentViewModel;
    ItemAdapter ia;
    ListView studentLV;
    ArrayList<Students> students;



    //String[] names={"Jay Veer Singh","Aditya","Arjun Kuzhivayalil Praveen","Jay Veer Singh","Aayush","Arjun Kuzhivayalil Praveen"};
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> regnos = new ArrayList<String>();
    ArrayList<String> branches = new ArrayList<String>();
    ArrayList<String> emails = new ArrayList<String>();
    //String[] regnos={"200945780","180911220","190911222","210945780","220911220","170911222"};
    //String[] branches={"Computer Science","Information Technology","Electrical and Instrumentation Engineering","Computer Science","Information Technology","Electrical and Instrumentation Engineering"};
    //String[] emails={"jayveer1@learner.manipal.edu","aditya.aayush1@learner.manipal.edu","arjun@gmail.com","jayveer1@learner.manipal.edu","aditya.aayush1@learner.manipal.edu","arjun@gmail.com"};
    CountDownTimer timer;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studentViewModel =
                new ViewModelProvider(this).get(StudentViewModel.class);
        setHasOptionsMenu(true);
        //CountDownLatch done = new CountDownLatch(1);
        //final AtomicBoolean done = new AtomicBoolean(false);
        //final AtomicInteger message1 = new AtomicInteger(0);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Students");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    Log.d("name", dataSnapshot1.child("name").getValue(String.class));
                    names.add(dataSnapshot1.child("name").getValue(String.class));
                    regnos.add(dataSnapshot1.child("regno").getValue(String.class));
                    branches.add(dataSnapshot1.child("course").getValue(String.class));
                    emails.add(dataSnapshot1.child("email").getValue(String.class));
                }
//                View root = inflater.inflate(R.layout.fragment_student, container, false);
//
//                studentLV=(ListView) root.findViewById(R.id.studentListView);
//                ia=new ItemAdapter(this,students);
//                studentLV.setAdapter(ia);
//
//                return root;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("hello", "onCreateView: ");
//
        View root = inflater.inflate(R.layout.fragment_student, container, false);
        ((AdminPage) getActivity()).ShowFAB();
        studentLV=(ListView) root.findViewById(R.id.studentListView);
//        timer = new CountDownTimer(2000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Log.d("hello", "onTick: ");
//            }
//
//            @Override
//            public void onFinish() {
//                students=getStudents();
//
//            }
//        }.start();
        students=getStudents();
        ia=new ItemAdapter(this,students);
        studentLV.setAdapter(ia);

        return root;
//        ((AdminPage) getActivity()).ShowFAB();
//        ia=new ItemAdapter(this,students);
//        studentLV.setAdapter(ia);
//
//        return root;
    }


    private ArrayList<Students> getStudents()
    {
        ArrayList<Students> students=new ArrayList<Students>();
        Students S;

        for(int i=0;i<names.size();i++)
        {
            S=new Students(names.get(i),regnos.get(i),branches.get(i),emails.get(i));
            Log.d("user", names.get(i));
            students.add(S);
        }
        return students;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_menu);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ia.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    public void onStart() {
        super.onStart();

        studentLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(view.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setIcon(R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("Do you want to delete "+students.get(position).getName())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                students.remove(position);
                                ia.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

        studentLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("position", String.valueOf(position));
                Intent intent=new Intent(getActivity(), StudentAttendance.class);
                intent.putExtra("Name",students.get(position).getName());
                intent.putExtra("RegNo",students.get(position).getRegno());
                startActivity(intent);
            }
        });
    }
}
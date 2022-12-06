package com.example.attendancesystem.ui.faculty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.attendancesystem.AdminPage;
import com.example.attendancesystem.FacultyAttendance;
import com.example.attendancesystem.R;

import java.util.ArrayList;

public class FacultyFragment extends Fragment {

    private FacultyViewModel facultyViewModel;
    ItemAdapterFaculty ia;
    ListView facultyLV;
    ArrayList<Faculties> faculties;
    String[] names={"Faculty1","Faculty2","Faculty3","Faculty4","Faculty5","Faculty6"};
    String[] regnos={"200945780","180911220","190911222","210945780","220911220","170911222"};
    String[] branches={"Computer Science","Information Technology","Electrical and Instrumentation Engineering","Computer Science","Information Technology","Electrical and Instrumentation Engineering"};
    String[] emails={"faculty1@manipal.edu","faculty2@manipal.edu","faculty3@gmail.com","faculty4@manipal.edu","faculty5@learner.manipal.edu","faculty6@gmail.com"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        facultyViewModel =
                new ViewModelProvider(this).get(FacultyViewModel.class);
        ((AdminPage) getActivity()).ShowFAB();
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_faculty, container, false);
        ((AdminPage) getActivity()).ShowFAB();
        facultyLV=(ListView) root.findViewById(R.id.studentListView);
        faculties=getFaculties();
        ia=new ItemAdapterFaculty(this,faculties);
        facultyLV.setAdapter(ia);
        return root;
    }
    private ArrayList<Faculties> getFaculties()
    {
        ArrayList<Faculties> faculties=new ArrayList<Faculties>();
        Faculties F;
        for(int i=0;i<names.length;i++)
        {
            F=new Faculties(names[i],regnos[i],branches[i],emails[i]);
            faculties.add(F);
        }
        return faculties;
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

        facultyLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(view.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setIcon(R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("Do you want to delete " + faculties.get(position).getName())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                faculties.remove(position);
                                ia.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

        facultyLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("position", String.valueOf(position));
                Intent intent=new Intent(getActivity(), FacultyAttendance.class);
                intent.putExtra("Name",faculties.get(position).getName());
                intent.putExtra("RegNo",faculties.get(position).getRegno());
                startActivity(intent);
            }
        });
    }
}
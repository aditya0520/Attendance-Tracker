package com.example.attendancesystem.ui.faculty;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.attendancesystem.R;

import java.util.ArrayList;

public class ItemAdapterFaculty extends BaseAdapter implements Filterable {

    LayoutInflater mInflator;
    ArrayList<Faculties> faculties;
    CustomFilter filter;
    ArrayList<Faculties> filterList;

    public ItemAdapterFaculty(FacultyFragment c, ArrayList<Faculties> faculties)
    {
        this.faculties=faculties;
        this.filterList=faculties;
        mInflator=(LayoutInflater) c.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return faculties.size();
    }

    @Override
    public Object getItem(int position) {
        return faculties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=mInflator.inflate(R.layout.faculty_view,null);
        TextView nameTV=(TextView) v.findViewById(R.id.facultyNameTV);
        TextView regNoTV=(TextView) v.findViewById(R.id.registrationNumberTV);
        TextView branchTV=(TextView) v.findViewById(R.id.branchTV);
        TextView emailTV=(TextView) v.findViewById(R.id.emailTV);
        nameTV.setText(faculties.get(position).getName());
        regNoTV.setText(faculties.get(position).getRegno());
        branchTV.setText(faculties.get(position).getBranch());
        emailTV.setText(faculties.get(position).getEmail());
        return v;
    }

    @Override
    public Filter getFilter() {

        if(filter==null)
        {
            filter=new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults=new FilterResults();
            if(constraint!=null&&constraint.length()>0)
            {
                constraint=constraint.toString().toUpperCase();
                ArrayList<Faculties> filters=new ArrayList<Faculties>();
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint)||
                            filterList.get(i).getRegno().toUpperCase().contains(constraint))
                    {
                        Faculties s=new Faculties(filterList.get(i).getName(),
                                filterList.get(i).getRegno(),
                                filterList.get(i).getBranch(),
                                filterList.get(i).getEmail()
                        );
                        filters.add(s);
                    }
                }
                filterResults.count=filters.size();
                filterResults.values=filters;
            }
            else
            {
                filterResults.count=filterList.size();
                filterResults.values=filterList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            faculties= (ArrayList<Faculties>) results.values;
            notifyDataSetChanged();
        }
    }
}

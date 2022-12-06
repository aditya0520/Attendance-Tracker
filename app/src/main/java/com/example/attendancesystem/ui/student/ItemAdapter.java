package com.example.attendancesystem.ui.student;

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

public class ItemAdapter extends BaseAdapter implements Filterable {

    LayoutInflater mInflator;
    ArrayList<Students> students;
    CustomFilter filter;
    ArrayList<Students> filterList;

    public ItemAdapter(StudentFragment c, ArrayList<Students> students)
    {
        this.students=students;
        this.filterList=students;
        mInflator=(LayoutInflater) c.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=mInflator.inflate(R.layout.student_view,null);
        TextView nameTV=(TextView) v.findViewById(R.id.studentNameTV);
        TextView regNoTV=(TextView) v.findViewById(R.id.registrationNumberTV);
        TextView branchTV=(TextView) v.findViewById(R.id.branchTV);
        TextView emailTV=(TextView) v.findViewById(R.id.emailTV);
        nameTV.setText(students.get(position).getName());
        regNoTV.setText(students.get(position).getRegno());
        branchTV.setText(students.get(position).getBranch());
        emailTV.setText(students.get(position).getEmail());
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
                ArrayList<Students> filters=new ArrayList<Students>();
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint)||
                            filterList.get(i).getRegno().toUpperCase().contains(constraint))
                    {
                        Students s=new Students(filterList.get(i).getName(),
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
            students= (ArrayList<Students>) results.values;
            notifyDataSetChanged();
        }
    }
}

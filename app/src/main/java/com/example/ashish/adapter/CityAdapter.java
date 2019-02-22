package com.example.ashish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ashish.thebookstore.R;

import java.util.ArrayList;



public class CityAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> arcity;
    private static LayoutInflater inflater = null;
    TextView tv;


    public  CityAdapter(Context context, ArrayList<String> arcity){

        this.context = context;
        this.arcity = arcity;

    }

    @Override
    public int getCount() {
        return arcity.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        View rowView;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.singlecountrycity, null);

        tv = (TextView)rowView.findViewById(R.id.txtcountrycity);
        String c = arcity.get(i);
        tv.setText(c);


        return rowView;
    }
}

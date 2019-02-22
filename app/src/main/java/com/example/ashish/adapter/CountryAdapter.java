package com.example.ashish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ashish.pojo.Country;
import com.example.ashish.thebookstore.R;

import java.util.ArrayList;


public class CountryAdapter extends BaseAdapter {

    Context context;
    ArrayList<Country> arcountry;
    private static LayoutInflater inflater = null;
    TextView tv;


    public  CountryAdapter(Context context, ArrayList<Country> arcountry){

        this.context = context;
        this.arcountry = arcountry;

    }

    @Override
    public int getCount() {
        return arcountry.size();
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
        Country c = (Country)arcountry.get(i);
        tv.setText(c.getCname());


        return rowView;
    }
}

package com.example.ashish.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashish.pojo.Books;
import com.example.ashish.thebookstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class BooksAdapter extends BaseAdapter{

    Context context;
    ArrayList<Books> arbooks;
    private static LayoutInflater inflater = null;
    int my;

    public class Holder {
        ImageView imgsinglebook;
        TextView txtbookname,txtwritername,txtplace,txtprice,txtpostedname,txtsell;
        Button btnbuybook,btnexchange;
    }



    public BooksAdapter(Context context, ArrayList<Books> arbooks,int my){

        this.context = context;
        this.arbooks = arbooks;
        this.my = my;

    }


    @Override
    public int getCount() {
        return arbooks.size();
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
        Holder holder = new Holder();
        View rowView;
        SharedPreferences sp = context.getSharedPreferences("users",context.MODE_PRIVATE);
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.singlebooks, null);

        holder.imgsinglebook = (ImageView)rowView.findViewById(R.id.imgsinglebook);
        holder.txtbookname  = (TextView)rowView.findViewById(R.id.txtbookname);
        holder.txtwritername  = (TextView)rowView.findViewById(R.id.txtwritername);
        holder.txtplace  = (TextView)rowView.findViewById(R.id.txtplace);
        holder.txtprice  = (TextView)rowView.findViewById(R.id.txtprice);
        holder.txtpostedname  = (TextView)rowView.findViewById(R.id.txtpostedname);
        holder.txtsell  = (TextView)rowView.findViewById(R.id.txtsell);

        holder.btnbuybook = (Button)rowView.findViewById(R.id.btnbuybook);
        holder.btnexchange = (Button)rowView.findViewById(R.id.btnexchange);

        Books b = (Books)arbooks.get(i);

        if (b.getBookimg()!= null && b.getBookimg().length()!=0) {
            Picasso.with(context).load(b.getBookimg()).resize(150,150).into(holder.imgsinglebook);
        }

        holder.txtbookname.setText(b.getBookname());
        holder.txtwritername.setText(b.getBookwriten());
        holder.txtplace.setText(b.getBookcity()+","+b.getBookcountry());
        holder.txtprice.setText(b.getBooksellingprice());
        holder.txtpostedname.setText(sp.getString("uname",""));
        holder.txtsell.setText(b.getBooksellexchange());

        if (my==1){

            holder.btnbuybook.setVisibility(View.GONE);
            holder.btnexchange.setVisibility(View.GONE);

        }else{

            holder.btnbuybook.setVisibility(View.GONE);
            holder.btnexchange.setVisibility(View.GONE);
        }




        return rowView;
    }
}

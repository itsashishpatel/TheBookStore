package com.example.ashish.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashish.pojo.Books;
import com.example.ashish.pojo.Users;
import com.example.ashish.thebookstore.Main2Activity;
import com.example.ashish.thebookstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


@SuppressLint("ValidFragment")
public class ViewBook extends Fragment {

    TextView edbookname,edbookwritername,edbookdiscri,edbookmarketprice,edbooksellingprice;
    TextView spnbooktype,spnbookcondition,spnbooksell,spnproinstitute,spnbookcity;
    ImageView imgbook;
    TextView spnbookcountry;
    Books books;
    Context context;
    String userid;
    Button btndelete,btnedit;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    Users users;
    Button btnbuybook,btnexchange,btnrent;
    int i;

    public ViewBook(Context context,Books books,int i){

        this.books = books;
        this.context = context;
        this.i  = i ;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewbook,container,false);

        imgbook = (ImageView)view.findViewById(R.id.imgbook);
        final SharedPreferences sp = context.getSharedPreferences("users",context.MODE_PRIVATE);



        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseInstance.getReference("app_title").setValue("TheBookStore");


        userid = books.getBookuserid();

        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String uid = postSnapshot.getKey();
                    if (uid.equals(userid)){

                        users = postSnapshot.getValue(Users.class);


                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        edbookname = (TextView) view.findViewById(R.id.edbookname);
        edbookwritername = (TextView)view.findViewById(R.id.edbookwritername);
        edbookdiscri = (TextView)view.findViewById(R.id.edbookdiscri);
        edbookmarketprice = (TextView)view.findViewById(R.id.edbookmarketprice);
        edbooksellingprice = (TextView)view.findViewById(R.id.edbooksellingprice);

        spnbooktype = (TextView) view.findViewById(R.id.spnbooktype);
        spnbookcondition = (TextView) view.findViewById(R.id.spnbookcondition);
        spnbooksell = (TextView) view.findViewById(R.id.spnbooksell);
        spnbookcountry = (TextView) view.findViewById(R.id.spnbookcountry);
        spnbookcity = (TextView) view.findViewById(R.id.spnbookcity);
        spnproinstitute = (TextView) view.findViewById(R.id.spninstitute);

        btnbuybook = (Button)view.findViewById(R.id.btnbuybook);
        btnexchange = (Button)view.findViewById(R.id.btnexchange);
        btndelete = (Button)view.findViewById(R.id.btndelete);
        btnedit = (Button)view.findViewById(R.id.btnedit);
        btnrent = (Button)view.findViewById(R.id.btnrent);

        if (books.getBookimg().length()!=0) {
            Picasso.with(context).load(books.getBookimg()).into(imgbook);
        }

        edbookname.setText("name : "+books.getBookname());
        edbookwritername.setText("Written by : "+books.getBookwriten());
        edbookdiscri.setText("Discription : "+books.getBookdiscription());
        edbookmarketprice.setText("Market Price : "+books.getBookmarketprice());
        edbooksellingprice.setText("Selling Price : "+books.getBooksellingprice());

        spnbooktype.setText("Type : "+books.getBooktype());
        spnbookcondition.setText("Condition : "+books.getBookcondition());
        spnbooksell.setText("Want to : "+books.getBooksellexchange());
        spnbookcountry.setText("Country : "+books.getBookcountry());
        spnbookcity.setText("City : "+books.getBookcity());
        spnproinstitute.setText("Institute Name : "+books.getBookinstitute());

        if (i==0) {
            btnbuybook.setVisibility(View.GONE);
            btnexchange.setVisibility(View.GONE);
            btnrent.setVisibility(View.GONE);
            btndelete.setVisibility(View.VISIBLE);
          /*  btndelete.setVisibility(View.VISIBLE);

            btnedit.setVisibility(View.VISIBLE);*/
        }

        btnbuybook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: "+users.getUseremail()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
                emailIntent.putExtra(Intent.EXTRA_TEXT,sp.getString("uname","")+" wants to buy your "+books.getBooktype()+"  "+books.getBookname()+" please contact him. \n Thank You");
                startActivity(Intent.createChooser(emailIntent, "Choose Email"));


            }
        });

        btnrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: "+users.getUseremail()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
                emailIntent.putExtra(Intent.EXTRA_TEXT,sp.getString("uname","")+" wants to take on rent your "+books.getBooktype()+"  "+books.getBookname()+" please contact him. \n Thank You");
                startActivity(Intent.createChooser(emailIntent, "Choose Email"));

            }
        });

        btnexchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: "+users.getUseremail()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
                emailIntent.putExtra(Intent.EXTRA_TEXT,sp.getString("uname","")+" wants to exchange your "+books.getBooktype()+"  "+books.getBookname()+" please contact him. \n Thank You");
                startActivity(Intent.createChooser(emailIntent, "Choose Email"));

            }
        });


        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mFirebaseDatabase = mFirebaseInstance.getReference("books");
                        mFirebaseInstance.getReference("app_title").setValue("TheBookStore");

                        mFirebaseDatabase.child(books.getBookid()).removeValue();

                        Fragment f1 = new BookLists(context,0,"my");
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentcontainer,f1);
                        ft.commit();




                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setMessage("Do you want to exit Delete?").create().show();

            }
        });


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,"This is demo");
        startActivity(Intent.createChooser(intent,"Choose email"));

    }
}

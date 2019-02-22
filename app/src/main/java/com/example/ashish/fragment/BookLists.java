package com.example.ashish.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ashish.adapter.BooksAdapter;
import com.example.ashish.helper.UtilClass;
import com.example.ashish.pojo.Books;
import com.example.ashish.thebookstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



@SuppressLint("ValidFragment")
public class BookLists extends Fragment {

    Context context;
    int edittexton;
    String searchcondition;

    ListView lvbooks;
    EditText edsearch;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String bookid;

    String userid;
    ArrayList<Books> arbooks;
    ProgressDialog pd;

    public BookLists(Context context,int edittexton,String searchcondition){

        this.context = context;
        this.edittexton = edittexton;
        this.searchcondition = searchcondition;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listbooks,container,false);



        pd = new ProgressDialog(context);
        pd.setTitle("Please wait...");
        pd.setCancelable(true);
        pd.setIndeterminate(false);
        pd.show();

        final SharedPreferences sp = context.getSharedPreferences("users",context.MODE_PRIVATE);
        userid = sp.getString("userid","");


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("books");
        mFirebaseInstance.getReference("app_title").setValue("TheBookStore");


        lvbooks = (ListView)view.findViewById(R.id.lvbooks);
        edsearch = (EditText)view.findViewById(R.id.edsearch);

        if (edittexton==1){

            edsearch.setVisibility(View.VISIBLE);
        }else{

            edsearch.setVisibility(View.GONE);
        }

        edsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (edsearch.getText().toString().length()==0){

                    getdata(pd, "home", edsearch.getText().toString());

                }else {

                    if (searchcondition.equals("city")) {

                        getdata(pd, "city", edsearch.getText().toString());

                    } else if (searchcondition.equals("insti")) {

                        getdata(pd, "insti", edsearch.getText().toString());

                    } else if (searchcondition.equals("home")) {

                        getdata(pd, "home", edsearch.getText().toString());

                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

               /* if (edsearch.getText().toString().length()==0){


                    arbooks.clear();
                    arbooks = new ArrayList<>();

                    if (UtilClass.isNetworkAvailable(context)) {

                        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                    bookid = postSnapshot.getKey();
                                    Books books = postSnapshot.getValue(Books.class);


                                    if (!books.getBookuserid().equals(userid)){

                                        arbooks.add(books);
                                    }


                                }

                                BooksAdapter booksAdapter = new BooksAdapter(context, arbooks, 0);
                                lvbooks.setAdapter(booksAdapter);

                                if (pd.isShowing()) {

                                    pd.dismiss();
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }


                }*/
            }
        });

        // condition goes here
         if (searchcondition.equals("my")){


             arbooks = new ArrayList<>();


             if (UtilClass.isNetworkAvailable(context)){

                 mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {

                         for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                             bookid = postSnapshot.getKey();
                             Books books = postSnapshot.getValue(Books.class);
                             books.setBookid(bookid);

                             if (books.getBookuserid().equals(userid)){

                                 arbooks.add(books);

                             }


                         }

                         BooksAdapter booksAdapter = new BooksAdapter(context,arbooks,1);
                         lvbooks.setAdapter(booksAdapter);

                         if (pd.isShowing()){

                             pd.dismiss();
                         }


                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });


             }else{


             }

         }else if (searchcondition.equals("insti")){


             edsearch.setHint("Enter the name of the Institute");
             arbooks = new ArrayList<>();

             if (UtilClass.isNetworkAvailable(context)) {

                 mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {

                         for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                             bookid = postSnapshot.getKey();
                             Books books = postSnapshot.getValue(Books.class);
                             books.setBookid(bookid);

                             if (!books.getBookuserid().equals(userid)){

                                 arbooks.add(books);
                             }


                         }

                         BooksAdapter booksAdapter = new BooksAdapter(context, arbooks, 0);
                         lvbooks.setAdapter(booksAdapter);

                         if (pd.isShowing()) {

                             pd.dismiss();
                         }


                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });
             }



         }else if (searchcondition.equals("city")){

             edsearch.setHint("Enter the name of the City");
             arbooks = new ArrayList<>();

             if (UtilClass.isNetworkAvailable(context)) {

                 mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {

                         for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                             bookid = postSnapshot.getKey();
                             Books books = postSnapshot.getValue(Books.class);
                             books.setBookid(bookid);
                             if (!books.getBookuserid().equals(userid)){

                                 arbooks.add(books);
                             }

                         }

                         BooksAdapter booksAdapter = new BooksAdapter(context, arbooks, 0);
                         lvbooks.setAdapter(booksAdapter);

                         if (pd.isShowing()) {

                             pd.dismiss();
                         }


                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });
             }


             }else if (searchcondition.equals("home")){


             edsearch.setHint("Search book");
                 arbooks = new ArrayList<>();

                 if (UtilClass.isNetworkAvailable(context)){

                     mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {

                             for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                                 bookid = postSnapshot.getKey();


                                 Books books = postSnapshot.getValue(Books.class);
                                 books.setBookid(bookid);
                                 if (!books.getBookuserid().equals(userid)){

                                     arbooks.add(books);
                                 }

                             }

                             BooksAdapter booksAdapter = new BooksAdapter(context,arbooks,0);
                             lvbooks.setAdapter(booksAdapter);

                             if (pd.isShowing()){

                                 pd.dismiss();
                             }


                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                         }
                     });



                 }



         }


         lvbooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                 if (!searchcondition.equals("my")){

                     Books books = (Books) arbooks.get(i);
                     Fragment f1 = new ViewBook(context, books, 1);
                     FragmentTransaction ft = getFragmentManager().beginTransaction();
                     ft.addToBackStack(f1.getClass().getName().toString());
                     ft.replace(R.id.fragmentcontainer, f1);
                     ft.commit();


                 }else {

                     Books books = (Books) arbooks.get(i);
                     Fragment f1 = new ViewBook(context, books, 0);
                     FragmentTransaction ft = getFragmentManager().beginTransaction();
                     ft.addToBackStack(f1.getClass().getName().toString());
                     ft.replace(R.id.fragmentcontainer, f1);
                     ft.commit();
                 }

             }
         });



        return view;
    }


    void getdata(final ProgressDialog pd, String name, final String city){

        pd.show();

         if (name.equals("city")){
             arbooks = new ArrayList<>();

            if (UtilClass.isNetworkAvailable(context)) {

                mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            bookid = postSnapshot.getKey();
                            Books books = postSnapshot.getValue(Books.class);
                            books.setBookid(bookid);
                            if ((!books.getBookuserid().equals(userid)) && books.getBookcity().toLowerCase().contains(city)) {

                                arbooks.add(books);

                            } else {


                            }


                        }

                        BooksAdapter booksAdapter = new BooksAdapter(context, arbooks, 0);
                        lvbooks.setAdapter(booksAdapter);

                        if (pd.isShowing()) {

                            pd.dismiss();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            } else {


            }
        }else if (name.equals("insti")){
             arbooks = new ArrayList<>();

             if (UtilClass.isNetworkAvailable(context)) {

                 mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {

                         for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                             bookid = postSnapshot.getKey();
                             Books books = postSnapshot.getValue(Books.class);
                             books.setBookid(bookid);
                             if ((!books.getBookuserid().equals(userid)) && books.getBookinstitute().toLowerCase().contains(city)) {

                                 arbooks.add(books);

                             } else {


                             }


                         }

                         BooksAdapter booksAdapter = new BooksAdapter(context, arbooks, 0);
                         lvbooks.setAdapter(booksAdapter);

                         if (pd.isShowing()) {

                             pd.dismiss();
                         }


                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });


             } else {


             }
         }else if (name.equals("home")){
             arbooks = new ArrayList<>();

             if (UtilClass.isNetworkAvailable(context)) {

                 mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {

                         for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                             bookid = postSnapshot.getKey();
                             Books books = postSnapshot.getValue(Books.class);
                             books.setBookid(bookid);
                             if ((!books.getBookuserid().equals(userid)) && (books.getBookname().toLowerCase().contains(city) || books.getBookinstitute().toLowerCase().contains(city) || books.getBookcity().toLowerCase().contains(city) || books.getBookcondition().toLowerCase().contains(city) || books.getBooksellexchange().toLowerCase().contains(city) || books.getBookcountry().toLowerCase().contains(city))) {

                                 arbooks.add(books);

                             } else {


                             }


                         }

                         BooksAdapter booksAdapter = new BooksAdapter(context, arbooks, 0);
                         lvbooks.setAdapter(booksAdapter);

                         if (pd.isShowing()) {

                             pd.dismiss();
                         }


                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });


             } else {


             }
         }



    }
}

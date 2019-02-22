package com.example.ashish.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashish.adapter.CountryAdapter;
import com.example.ashish.helper.Arraygetcitycountry;
import com.example.ashish.helper.UtilClass;
import com.example.ashish.pojo.Books;
import com.example.ashish.pojo.Country;
import com.example.ashish.thebookstore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;



@SuppressLint("ValidFragment")
public class Addbooks extends Fragment{

    ImageView imgbook;
    TextView txtclickimg;
    EditText edbookname,edbookwritername,edbookdiscri,edbookmarketprice,edbooksellingprice;

    Button btnaddbook;

    Context context;
    SharedPreferences sp;
    ProgressDialog pd;

    TextView spnbookcountry;

    ArrayList<Country> arcountry;
    Arraygetcitycountry arraygetcitycountry;

    String strselectedcountry,strselectedcity,strselectedtype,strselectedsell,strselectedcondition,strselectedinsti;
    ArrayList<String> arcity;
    ArrayList<String> artype,arsell,arcondition,arintsti;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String bookid;
    int PICK_IMAGE_REQUEST = 111;

    FirebaseStorage storage;
    StorageReference storageRef;
    String filename = "",picturePath = "";
    Uri selectedImage;
    String strbookname,strbookwriter,strbookdiscri,strbookmarketprice,strbooksellingprice;

    AutoCompleteTextView spnbooktype,spnbookcondition,spnbooksell,spnproinstitute,spnbookcity;


    public Addbooks(Context context){

        this.context = context;
        arraygetcitycountry = new Arraygetcitycountry();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.addbooks,container,false);

        sp = context.getSharedPreferences("users",context.MODE_PRIVATE);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("books");
        mFirebaseInstance.getReference("app_title").setValue("TheBookStore");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://thebookstore1811.appspot.com");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String appTitle = dataSnapshot.getValue(String.class);
                Log.e("app name on firebase ",appTitle);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        artype = new ArrayList<>();
        artype.add("Book");
        artype.add("Novel");
        artype.add("Magazine");
        artype.add("Book Set");

        arsell = new ArrayList<>();
        arsell.add("Sell");
        arsell.add("Exchange");
        arsell.add("Rent");
        arsell.add("Free/Donate");

        arcondition = new ArrayList<>();
        arcondition.add("New");
        arcondition.add("Used");

        arintsti = new ArrayList<>();

        arintsti.add("California State University Sonoma");
        arintsti.add("California State University San Marcos");
        arintsti.add("California State University San Luis Obispo");
        arintsti.add("California State University San Jose");
        arintsti.add("California State University San Francisco");
        arintsti.add("California State University San Diego");
        arintsti.add("California State University San Bernardino");
        arintsti.add("California State University Sacramento");
        arintsti.add("California State University Pomona");
        arintsti.add("California State University Northridge");
        arintsti.add("California State University Los Angeles");
        arintsti.add("California State University Long Beach");
        arintsti.add("California State University Fullerton");
        arintsti.add("California State University Fresno");
        arintsti.add("California State University Chico");
        arintsti.add("California State University Dominguez Hills");


        //CityAdapter ctype = new CityAdapter(context,artype);
        ArrayAdapter<String> ctype = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,artype);
        //CityAdapter csell = new CityAdapter(context,arsell);
        ArrayAdapter<String> csell = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arsell);
        //CityAdapter ccondition = new CityAdapter(context,arcondition);
        ArrayAdapter<String> ccondition = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arcondition);
        //CityAdapter cinsti = new CityAdapter(context,arintsti);
        ArrayAdapter<String> cinsti = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arintsti);


        arcountry = arraygetcitycountry.getcountries();
        final CountryAdapter c = new CountryAdapter(context,arcountry);

         pd = new ProgressDialog(context);
        pd.setTitle("Please wait...");
        pd.setCancelable(true);
        pd.setIndeterminate(false);


        imgbook = (ImageView)view.findViewById(R.id.imgbook);

        txtclickimg = (TextView)view.findViewById(R.id.txtclickimg);

        edbookname = (EditText)view.findViewById(R.id.edbookname);
        edbookwritername = (EditText)view.findViewById(R.id.edbookwritername);
        edbookdiscri = (EditText)view.findViewById(R.id.edbookdiscri);
        edbookmarketprice = (EditText)view.findViewById(R.id.edbookmarketprice);
        edbooksellingprice = (EditText)view.findViewById(R.id.edbooksellingprice);

        spnbooktype = (AutoCompleteTextView) view.findViewById(R.id.spnbooktype);
        spnbookcondition = (AutoCompleteTextView) view.findViewById(R.id.spnbookcondition);
        spnbooksell = (AutoCompleteTextView) view.findViewById(R.id.spnbooksell);
        spnbookcountry = (TextView) view.findViewById(R.id.spnbookcountry);
        spnbookcity = (AutoCompleteTextView) view.findViewById(R.id.spnbookcity);
        spnproinstitute = (AutoCompleteTextView) view.findViewById(R.id.spninstitute);

        spnbookcity.setVisibility(View.GONE);

        spnbooktype.setThreshold(0);
        spnbookcondition.setThreshold(0);
        spnproinstitute.setThreshold(0);
        spnbooksell.setThreshold(0);
        spnbookcity.setThreshold(2);

        spnbooktype.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spnbooktype.showDropDown();
                return false;
            }

        });
        spnbookcondition.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spnbookcondition.showDropDown();
                return false;
            }

        });
        spnproinstitute.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spnproinstitute.showDropDown();
                return false;
            }

        });
        spnbooksell.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spnbooksell.showDropDown();
                return false;
            }

        });

        btnaddbook = (Button)view.findViewById(R.id.btnaddbook);

        imgbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 111);
                    }


        });

        /*spnbookcountry.setAdapter(c);
        spnbookcountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!pd.isShowing()){

                    pd.show();
                }

                Country co = (Country)arcountry.get(i);
                strselectedcountry = co.getCname();
                arcity = arraygetcitycountry.getcity(co.getCcode(),context);
                CityAdapter cityAdapter = new CityAdapter(context,arcity);
                spnbookcity.setAdapter(cityAdapter);

                i = 1;
                if (pd.isShowing()) {
                    pd.dismiss();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


        spnbookcountry.setOnClickListener(new View.OnClickListener() {
            ListView lv;
            ArrayList<Country> searcharray;

            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.listbooks);
                dialog.setTitle("Select Country");
                final EditText edname = (EditText)dialog.findViewById(R.id.edsearch);
                lv = (ListView)dialog.findViewById(R.id.lvbooks);

                lv.setAdapter(c);

                edname.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        searcharray = new ArrayList<>();
                        for (int j = 0;j<arcountry.size();j++){

                            Country co = (Country)arcountry.get(j);
                            if (co.getCname().toLowerCase().contains(edname.getText().toString())){

                                searcharray.add(co);
                            }

                        }

                        CountryAdapter cad = new CountryAdapter(context,searcharray);
                        lv.setAdapter(cad);



                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        if (edname.getText().toString().length()==0){

                            lv.setAdapter(c);
                        }

                    }
                });

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                        //showdialog();


                        if (edname.getText().toString().length()==0){

                            Country co = (Country)arcountry.get(i);
                            strselectedcountry = co.getCname();
                            spnbookcountry.setText(co.getCname());

                            //pd.show();
                            arcity = arraygetcitycountry.getcity(co.getCcode(),context);
                            //CityAdapter cityAdapter = new CityAdapter(context,arcity);

                            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arcity);
                            spnbookcity.setAdapter(cityAdapter);

                            spnbookcity.setVisibility(View.VISIBLE);

                            //progressdissmiss();
                            dialog.dismiss();


                        }else{

                            //showdialog();

                            //pd.show();


                            Country co = (Country)searcharray.get(i);
                            strselectedcountry = co.getCname();
                            spnbookcountry.setText(co.getCname());


                            arcity = arraygetcitycountry.getcity(co.getCcode(),context);
                            //CityAdapter cityAdapter = new CityAdapter(context,arcity);
                            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arcity);
                            spnbookcity.setAdapter(cityAdapter);

                            spnbookcity.setVisibility(View.VISIBLE);

                           // progressdissmiss();
                            //dialog.d
                            // ismiss();

                            if (pd.isShowing()){

                                pd.dismiss();
                            }
                        }
                    }
                });

                dialog.show();



            }
        });


            }
        });


        spnbookcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strselectedcity = arcity.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnbooktype.setAdapter(ctype);
        spnbooktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strselectedtype = artype.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnbooksell.setAdapter(csell);
        spnbooksell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strselectedsell = arsell.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnbookcondition.setAdapter(ccondition);
        spnbookcondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strselectedcondition = arcondition.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnproinstitute.setAdapter(cinsti);
        spnproinstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strselectedinsti = arintsti.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnaddbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();

                strbookname = edbookname.getText().toString();
                strbookwriter = edbookwritername.getText().toString();
                strbookdiscri = edbookdiscri.getText().toString();
                strbookmarketprice = edbookmarketprice.getText().toString();
                strbooksellingprice = edbooksellingprice.getText().toString();

                strselectedcity = spnbookcity.getText().toString();
                strselectedcondition = spnbookcondition.getText().toString();
                strselectedcountry = spnbookcountry.getText().toString();
                strselectedinsti = spnproinstitute.getText().toString();
                strselectedsell = spnbooksell.getText().toString();
                strselectedtype = spnbooktype.getText().toString();

                if (strbookname.length()==0
                        || strbookwriter.length()==0
                        || strbookdiscri.length()==0
                        || strbookmarketprice.length()==0
                        || strselectedcity.length()==0
                        || strselectedcondition.length()==0
                        || strselectedcountry.length()==0
                        || strselectedinsti.length()==0
                        || strselectedsell.length()==0
                        || strselectedtype.length()==0){

                    Toast.makeText(context,"Fill all the Fields",Toast.LENGTH_LONG).show();

                    if (pd.isShowing()){

                        pd.dismiss();
                    }
                }else{

                    if (UtilClass.isNetworkAvailable(context)){

                        final Books b = new Books();


                        if (picturePath.length()==0 && filename.length()==0){

                            b.setBookimg("");

                            Toast.makeText(context,"Please add image",Toast.LENGTH_LONG).show();


                            if (pd.isShowing()){

                                pd.dismiss();
                            }

                        }else{

                            StorageReference childRef = storageRef.child(filename);
                            UploadTask uploadTask = childRef.putFile(selectedImage);

                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show();
                                    Log.e("firebasepath ",taskSnapshot.getDownloadUrl().toString());
                                    b.setBookimg(taskSnapshot.getDownloadUrl().toString());
                                    insertdata(b);

                                    if (pd.isShowing()){

                                        pd.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    b.setBookimg("");
                                    insertdata(b);
                                    Toast.makeText(context, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                                    if (pd.isShowing()){

                                        pd.dismiss();
                                    }
                                }
                            });



                        }

                    }else{

                        Toast.makeText(context,"No Internet, Please connect to the Internet",Toast.LENGTH_LONG).show();
                    }


                }

            }
        });



        return view;
    }

    void insertdata(Books b){


        b.setBookname(strbookname);
        b.setBooktype(strselectedtype);
        b.setBookcondition(strselectedcondition);
        b.setBookwriten(strbookwriter);
        b.setBookdiscription(strbookdiscri);
        b.setBooksellexchange(strselectedsell);

        b.setBookmarketprice("Rs."+strbookmarketprice+"/-");
        b.setBooksellingprice("Rs."+strbooksellingprice+"/-");
        b.setBookuserid(sp.getString("userid",""));
        b.setBookcountry(strselectedcountry);
        b.setBookcity(strselectedcity);
        b.setBookinstitute(strselectedinsti);

        bookid = mFirebaseDatabase.push().getKey();
        mFirebaseDatabase.child(bookid).setValue(b);

        Toast.makeText(context,"Book inserted success",Toast.LENGTH_LONG).show();

        Fragment f1 = new Addbooks(context);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentcontainer,f1);
        ft.commit();

    }

    void showdialog(){


        pd = new ProgressDialog(context);
        pd.setTitle("Please wait...");
        pd.setCancelable(true);
        pd.setIndeterminate(false);
        pd.show();


    }

    void progressdissmiss(){

        if (pd.isShowing()){
            pd.dismiss();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.e("filepath ",picturePath);
            File f = new File(picturePath);
            filename = f.getName();
            Log.e("filename ",filename);
            imgbook.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}

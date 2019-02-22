package com.example.ashish.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashish.adapter.CountryAdapter;
import com.example.ashish.helper.Arraygetcitycountry;
import com.example.ashish.helper.UtilClass;
import com.example.ashish.pojo.Country;
import com.example.ashish.thebookstore.Main2Activity;
import com.example.ashish.thebookstore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class UpdateProfile extends Fragment {

    ImageView imgpro;
    EditText edproname,edprophone,edproadd;
    TextView txtprogender,txtprocountry,txtprocity,txtprouinstitute,txtprotype;
    TextView txtprogender1,txtprocountry1,txtprocity1,txtprouinstitute1,txtprotype1;

    LinearLayout lincity;

    TextView spnprocountry;
    AutoCompleteTextView spnprocity,spnproinstitute,spnprotype,spnprogender;

    Button btnupdateprofile,btneditprofile,btnprocancel;
    Context context;
    SharedPreferences sp;

    ArrayList<Country> arcountry;
    Arraygetcitycountry arraygetcitycountry;
    ArrayList<String> arcity;
    String strselectedcountry,strselectedcity,strselectedgender,strselectedtype,strselectedinsti;

    ArrayList<String> argender;
    ArrayList<String> artype;
    ArrayList<String> arintsti;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    TextView txtclick;
    String strname,strphone,stradd;

    int i = 0;

    FirebaseStorage storage;
    StorageReference storageRef;
    String filename = "",picturePath = "";
    Uri selectedImage;
    String strbookname,strbookwriter,strbookdiscri,strbookmarketprice,strbooksellingprice,strimgurl;

    public UpdateProfile(Context context){

        this.context = context;
        arraygetcitycountry = new Arraygetcitycountry();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewprofile,container,false);

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("Please wait...");
        pd.setCancelable(true);
        pd.setIndeterminate(false);


        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://thebookstore1811.appspot.com");

        argender = new ArrayList<>();
        argender.add("Male");
        argender.add("Female");
        argender.add("Transgender");

        artype = new ArrayList<>();
        artype.add("Strudent");
        artype.add("Teacher");
        artype.add("Author");
        artype.add("Other");

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


        //CityAdapter cgender = new CityAdapter(context,argender);
        ArrayAdapter<String> cgender = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,argender);
        //CityAdapter ctype = new CityAdapter(context,artype);
        ArrayAdapter<String> ctype = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,artype);
        //CityAdapter cinsti = new CityAdapter(context,arintsti);
        ArrayAdapter<String> cinsti = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arintsti);

        arcountry = arraygetcitycountry.getcountries();

        final CountryAdapter c = new CountryAdapter(context,arcountry);

        imgpro = (ImageView)view.findViewById(R.id.imgpro);

        txtclick = (TextView)view.findViewById(R.id.txtclickimg);

        edproname = (EditText) view.findViewById(R.id.edproname);
        edprophone = (EditText)view.findViewById(R.id.edprophone);
        edproadd = (EditText)view.findViewById(R.id.edproadd);

        txtprogender = (TextView)view.findViewById(R.id.txtprogender);
        txtprocountry = (TextView)view.findViewById(R.id.txtprocountry);
        txtprocity = (TextView)view.findViewById(R.id.txtprocity);
        txtprouinstitute = (TextView)view.findViewById(R.id.txtproinstitute);
        txtprotype = (TextView)view.findViewById(R.id.txtprotype);

        txtprogender1 = (TextView)view.findViewById(R.id.txtprogender1);
        txtprocountry1 = (TextView)view.findViewById(R.id.txtprocountry1);
        txtprocity1 = (TextView)view.findViewById(R.id.txtprocity1);
        txtprouinstitute1 = (TextView)view.findViewById(R.id.txtproinstitute1);
        txtprotype1 = (TextView)view.findViewById(R.id.txtprotype1);

        spnprogender = (AutoCompleteTextView) view.findViewById(R.id.spnprogender);
        spnprocountry = (TextView) view.findViewById(R.id.spnprocountry);
        spnprocity = (AutoCompleteTextView) view.findViewById(R.id.spnprocity);
        spnproinstitute = (AutoCompleteTextView) view.findViewById(R.id.spnproinstitute);
        spnprotype = (AutoCompleteTextView) view.findViewById(R.id.spnprotype);

        btnupdateprofile = (Button)view.findViewById(R.id.btnupdatepro);
        btneditprofile = (Button)view.findViewById(R.id.btnproedit);
        btnprocancel = (Button)view.findViewById(R.id.btnprocancel);

        spnprogender.setThreshold(0);
        spnprotype.setThreshold(0);
        spnproinstitute.setThreshold(0);
        spnprocity.setThreshold(0);

        spnprogender.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spnprogender.showDropDown();
                return false;
            }

        });

        spnprotype.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spnprotype.showDropDown();
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
        spnprocity.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                spnprocity.showDropDown();
                return false;
            }

        });

       /* lincity = (LinearLayout)view.findViewById(R.id.lincity);
        lincity.setVisibility(View.GONE);
*/



        imgpro.setEnabled(false);
        imgpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 111);
            }


        });

       sp = context.getSharedPreferences("users",context.MODE_PRIVATE);

       String imgurl = sp.getString("uimg","");

       if (imgurl!=null && imgurl.length()!=0){

           Picasso.with(context).load(imgurl).into(imgpro);
       }

        edproname.setText(sp.getString("uname",""));
        edproname.setHint("Name : ");
        edproname.setEnabled(false);
        edproname.setCursorVisible(false);

        edprophone.setText(sp.getString("uphone",""));
        edprophone.setHint("Phone no : ");
        edprophone.setEnabled(false);
        edprophone.setCursorVisible(false);

        edproadd.setText(sp.getString("uaddress",""));
        edproadd.setHint("Address : ");
        edproadd.setEnabled(false);
        edproadd.setCursorVisible(false);

        txtprogender.setText(sp.getString("ugender",""));
        txtprogender1.setText("Gender : ");
        spnprogender.setVisibility(View.GONE);
        spnprogender.setText(sp.getString("ugender",""));

        txtprocountry.setText(sp.getString("ucountry",""));
        txtprocountry1.setText("Country : ");
        spnprocountry.setVisibility(View.GONE);
        spnprocountry.setText(sp.getString("ucountry",""));

        txtprocity.setText(sp.getString("ucity",""));
        txtprocity1.setText("City : ");
        spnprocity.setVisibility(View.GONE);
        spnprocity.setText(sp.getString("ucity",""));

        txtprouinstitute.setText(sp.getString("uinstitute",""));
        txtprouinstitute1.setText("Institute : ");
        spnproinstitute.setVisibility(View.GONE);
        spnproinstitute.setText(sp.getString("uinstitute",""));

        txtprotype.setText(sp.getString("utype",""));
        txtprotype1.setText("User type : ");
        spnprotype.setVisibility(View.GONE);
        spnprotype.setText(sp.getString("utype",""));

        btnupdateprofile.setVisibility(View.GONE);
        btnprocancel.setVisibility(View.GONE);

       /* spnprocountry.setAdapter(c);
        spnprocountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                Country co = (Country)arcountry.get(i);
                strselectedcountry = co.getCname();
                arcity = arraygetcitycountry.getcity(co.getCcode(),context);
                CityAdapter cityAdapter = new CityAdapter(context,arcity);
                spnprocity.setAdapter(cityAdapter);

                i = 1;
                if (pd.isShowing()) {
                    pd.dismiss();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        spnprocountry.setOnClickListener(new View.OnClickListener() {
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

                        if (edname.getText().toString().length()==0){
                            pd.show();

                            Country co = (Country)arcountry.get(i);
                            strselectedcountry = co.getCname();
                            spnprocountry.setText(co.getCname());
                            dialog.dismiss();
                            arcity = arraygetcitycountry.getcity(co.getCcode(),context);
                            //CityAdapter cityAdapter = new CityAdapter(context,arcity);

                            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arcity);
                            spnprocity.setAdapter(cityAdapter);

                            spnprocity.setVisibility(View.VISIBLE);

                            if (pd.isShowing()){

                                pd.dismiss();
                            }

                        }else{
                            pd.show();

                            Country co = (Country)searcharray.get(i);
                            strselectedcountry = co.getCname();
                            spnprocountry.setText(co.getCname());
                            dialog.dismiss();
                            arcity = arraygetcitycountry.getcity(co.getCcode(),context);
                            //CityAdapter cityAdapter = new CityAdapter(context,arcity);
                            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arcity);
                            spnprocity.setAdapter(cityAdapter);

                            spnprocity.setVisibility(View.VISIBLE);

                            if (pd.isShowing()){

                                pd.dismiss();
                            }

                        }
                    }
                });

                dialog.show();


            }
        });



        spnprocity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strselectedcity = arcity.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnprogender.setAdapter(cgender);
        spnprogender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strselectedgender = argender.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnprotype.setAdapter(ctype);
        spnprotype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strselectedtype = artype.get(i);
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

        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i==0) {

                   // pd.show();
                }

                txtclick.setVisibility(View.VISIBLE);
                imgpro.setEnabled(true);

                edproname.setText(sp.getString("uname",""));
                edproname.setHint("Change name : ");
                edproname.setEnabled(true);
                edproname.setCursorVisible(true);

                edprophone.setText(sp.getString("uphone",""));
                edprophone.setHint("Change Phone no : ");
                edprophone.setEnabled(true);
                edprophone.setCursorVisible(true);

                edproadd.setText(sp.getString("uaddress",""));
                edproadd.setHint("Change Address : ");
                edproadd.setEnabled(true);
                edproadd.setCursorVisible(true);

                txtprogender.setText(sp.getString("ugender",""));
                txtprogender.setVisibility(View.GONE);
                txtprogender1.setText("Select Gender : ");
                spnprogender.setVisibility(View.VISIBLE);

                txtprocountry.setText(sp.getString("ucountry",""));
                txtprocountry.setVisibility(View.GONE);
                txtprocountry1.setText("Select Country : ");
                spnprocountry.setVisibility(View.VISIBLE);

                txtprocity.setText(sp.getString("ucity",""));
                txtprocity.setVisibility(View.GONE);
                txtprocity1.setText("Selet City : ");
                spnprocity.setVisibility(View.GONE);

                txtprouinstitute.setText(sp.getString("uinstitute",""));
                txtprouinstitute.setVisibility(View.GONE);
                txtprouinstitute1.setText("Select Institute : ");
                spnproinstitute.setVisibility(View.VISIBLE);

                txtprotype.setText(sp.getString("utype",""));
                txtprotype.setVisibility(View.GONE);
                txtprotype1.setText("Select User type : ");
                spnprotype.setVisibility(View.VISIBLE);

                btnupdateprofile.setVisibility(View.VISIBLE);
                btnprocancel.setVisibility(View.VISIBLE);
                btneditprofile.setVisibility(View.GONE);

            }
        });

        btnprocancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtclick.setVisibility(View.GONE);
                imgpro.setEnabled(false);

                edproname.setText(sp.getString("uname",""));
                edproname.setHint("Name : ");
                edproname.setEnabled(false);
                edproname.setCursorVisible(false);

                edprophone.setText(sp.getString("uphone",""));
                edprophone.setHint("Phone no : ");
                edprophone.setEnabled(false);
                edprophone.setCursorVisible(false);

                edproadd.setText(sp.getString("uaddress",""));
                edproadd.setHint("Address : ");
                edproadd.setEnabled(false);
                edproadd.setCursorVisible(false);

                txtprogender.setText(sp.getString("ugender",""));
                txtprogender1.setText("Gender : ");
                spnprogender.setVisibility(View.GONE);
                txtprogender.setVisibility(View.VISIBLE);

                txtprocountry.setText(sp.getString("ucountry",""));
                txtprocountry1.setText("Country : ");
                spnprocountry.setVisibility(View.GONE);
                txtprocountry.setVisibility(View.VISIBLE);


                txtprocity.setText(sp.getString("ucity",""));
                txtprocity1.setText("City : ");
                spnprocity.setVisibility(View.GONE);
                txtprocity.setVisibility(View.VISIBLE);


                txtprouinstitute.setText(sp.getString("uinstitute",""));
                txtprouinstitute1.setText("Institute : ");
                spnproinstitute.setVisibility(View.GONE);
                txtprouinstitute.setVisibility(View.VISIBLE);

                txtprotype.setText(sp.getString("utype",""));
                txtprotype1.setText("User type : ");
                spnprotype.setVisibility(View.GONE);
                txtprotype.setVisibility(View.VISIBLE);

                btnupdateprofile.setVisibility(View.GONE);
                btnprocancel.setVisibility(View.GONE);
                btneditprofile.setVisibility(View.VISIBLE);

            }
        });


        btnupdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();


                strname = edproname.getText().toString();
                strphone = edprophone.getText().toString();
                stradd = edproadd.getText().toString();

                if (strname.length()==0 || strphone.length()==0 || stradd.length()==0){

                    Toast.makeText(context,"Fill all the fields",Toast.LENGTH_LONG).show();
                }else {

                    if (UtilClass.isNetworkAvailable(context)) {

                        if (picturePath.length()==0 && filename.length()==0){

                            strimgurl = "";
                            insertdata();

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
                                    strimgurl = taskSnapshot.getDownloadUrl().toString();
                                    insertdata();

                                    if (pd.isShowing()){

                                        pd.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    strimgurl = "";
                                    insertdata();
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

    void insertdata(){

        if (strselectedgender==null || strselectedgender.length()==0){

            strselectedgender = spnprogender.getText().toString();
        }
        if (strselectedcountry==null  || strselectedcountry.length()==0){

            strselectedcountry = spnprocountry.getText().toString();
        }
        if (strselectedcity==null || strselectedcity.length()==0){

            strselectedcity = spnprocity.getText().toString();
        }
        if (strselectedtype==null || strselectedtype.length()==0){

            strselectedtype = spnprotype.getText().toString();
        }
        if (strselectedinsti==null || strselectedinsti.length()==0){

            strselectedinsti = spnproinstitute.getText().toString();
        }
        if (strimgurl==null || strimgurl.length()==0){

            strimgurl = sp.getString("uimg","");
        }

        mFirebaseDatabase.child(sp.getString("userid", "")).child("username").setValue(strname);
        mFirebaseDatabase.child(sp.getString("userid", "")).child("userphone").setValue(strphone);
        mFirebaseDatabase.child(sp.getString("userid", "")).child("useraddress").setValue(stradd);
        mFirebaseDatabase.child(sp.getString("userid", "")).child("usergender").setValue(strselectedgender);
        mFirebaseDatabase.child(sp.getString("userid", "")).child("usercountry").setValue(strselectedcountry);
        mFirebaseDatabase.child(sp.getString("userid", "")).child("usercity").setValue(strselectedcity);
        mFirebaseDatabase.child(sp.getString("userid", "")).child("usertype").setValue(strselectedtype);
        mFirebaseDatabase.child(sp.getString("userid", "")).child("userinstitute").setValue(strselectedinsti);
        mFirebaseDatabase.child(sp.getString("userid", "")).child("userimg").setValue(strimgurl);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("uname", strname);
        editor.putString("uimg",strimgurl);
        editor.putString("uphone", strphone);
        editor.putString("ugender", strselectedgender);
        editor.putString("utype", strselectedtype);
        editor.putString("ucity", strselectedcity);
        editor.putString("ucountry", strselectedcountry);
        editor.putString("uinstitute", strselectedinsti);
        editor.putString("uaddress", stradd);

        editor.commit();
        imgpro.setClickable(false);

        txtclick.setVisibility(View.GONE);

        edproname.setText(sp.getString("uname", ""));
        edproname.setHint("Name : ");
        edproname.setEnabled(false);
        edproname.setCursorVisible(false);

        edprophone.setText(sp.getString("uphone", ""));
        edprophone.setHint("Phone no : ");
        edprophone.setEnabled(false);
        edprophone.setCursorVisible(false);

        edproadd.setText(sp.getString("uaddress", ""));
        edproadd.setHint("Address : ");
        edproadd.setEnabled(false);
        edproadd.setCursorVisible(false);

        txtprogender.setText(sp.getString("ugender", ""));
        txtprogender1.setText("Gender : ");
        spnprogender.setVisibility(View.GONE);
        txtprogender.setVisibility(View.VISIBLE);

        txtprocountry.setText(sp.getString("ucountry", ""));
        txtprocountry1.setText("Country : ");
        spnprocountry.setVisibility(View.GONE);
        txtprocountry.setVisibility(View.VISIBLE);

        txtprocity.setText(sp.getString("ucity", ""));
        txtprocity1.setText("City : ");
        spnprocity.setVisibility(View.GONE);
        txtprocity.setVisibility(View.VISIBLE);


        txtprouinstitute.setText(sp.getString("uinstitute", ""));
        txtprouinstitute1.setText("Institute : ");
        spnproinstitute.setVisibility(View.GONE);
        txtprouinstitute.setVisibility(View.VISIBLE);

        txtprotype.setText(sp.getString("utype", ""));
        txtprotype1.setText("User type : ");
        spnprotype.setVisibility(View.GONE);
        txtprotype.setVisibility(View.VISIBLE);

        btnupdateprofile.setVisibility(View.GONE);
        btnprocancel.setVisibility(View.GONE);
        btneditprofile.setVisibility(View.VISIBLE);

        Intent i = new Intent(context, Main2Activity.class);
        context.startActivity(i);
        ((Activity)context).finish();

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
            imgpro.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}

package com.example.ashish.thebookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashish.helper.PermissionGiven;
import com.example.ashish.helper.UtilClass;
import com.example.ashish.helper.Validation;
import com.example.ashish.pojo.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Login extends AppCompatActivity{

    EditText eduname,edupass;
    TextView txtlogin,txtreg;
    Button btnlogin;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    Users myuser;
    String personId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final ProgressDialog pd = new ProgressDialog(Login.this);
        pd.setTitle("Please wait...");
        pd.setCancelable(true);
        pd.setIndeterminate(false);



      /*  runOnUiThread(new Runnable() {
                          @Override
                          public void run() {

                              PermissionGiven.WriteExternalStorage(Login.this);
                              PermissionGiven.CALL_PER(Login.this);
                              PermissionGiven.Contact_Permission_Read(Login.this);
                              PermissionGiven.Contact_Permission_Write(Login.this);

                          }
                      });*/

      PermissionGiven.multipermission(Login.this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseInstance.getReference("app_title").setValue("TheBookStore");


        eduname = (EditText)findViewById(R.id.eduname);
        edupass = (EditText)findViewById(R.id.edupass);
        txtlogin = (TextView)findViewById(R.id.txtlogin);
        txtreg = (TextView)findViewById(R.id.txtreg);

        btnlogin = (Button)findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();

                final String name = eduname.getText().toString();
                final String pass = edupass.getText().toString();

                if (name.length()==0){

                    eduname.setError("Please enter Email");
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                }else if (pass.length()==0){

                    edupass.setError("Please enter name");
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                }else if (!Validation.emailValidator(name)){

                    eduname.setError("Please enter valid email");
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }

                }else{

                    if (UtilClass.isNetworkAvailable(Login.this)){


                        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {



                                    Users users = postSnapshot.getValue(Users.class);


                                    if (users.getUseremail().equals(name) && users.getUserpassword().equals(pass) ){

                                        myuser = users;
                                        personId = postSnapshot.getKey();
                                        Log.e("key",personId);
                                    }
                                }
                                if (myuser!=null) {

                                    SharedPreferences sp = getSharedPreferences("users",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("uname",myuser.getUsername());
                                    editor.putString("uemail",myuser.getUseremail());
                                    editor.putString("uphone",myuser.getUserphone());
                                    editor.putString("ugender",myuser.getUsergender());
                                    editor.putString("utype",myuser.getUsertype());
                                    editor.putString("ucity",myuser.getUsercity());
                                    editor.putString("ucountry",myuser.getUsercountry());
                                    editor.putString("uinstitute",myuser.getUserinstitute());
                                    editor.putString("uaddress",myuser.getUseraddress());
                                    editor.putString("uimg",myuser.getUserimg());
                                    editor.putString("userid",personId);
                                    editor.commit();

                                    Toast.makeText(Login.this, myuser.getUsername() + " " + myuser.getUseremail(), Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Login.this, Main2Activity.class);
                                    startActivity(i);
                                    finish();
                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                }
                                else{

                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    Toast.makeText(Login.this,"Login Fail",Toast.LENGTH_LONG).show();
                                    //error massage

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }) ;

                    }
                    else{

                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        Toast.makeText(Login.this,"No Internet, Please connect to the Internet",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent i = new Intent(Login.this,Login.class);
                startActivity(i);*/
            }
        });

        txtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

       /* eduname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String email = eduname.getText().toString();
                if (!Validation.emailValidator(email)){

                    eduname.setError("Please enter valid email");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionGiven.WriteExternalStorage(Login.this);
        PermissionGiven.CALL_PER(Login.this);
        PermissionGiven.Contact_Permission_Read(Login.this);
        PermissionGiven.Contact_Permission_Write(Login.this);
    }*/
}

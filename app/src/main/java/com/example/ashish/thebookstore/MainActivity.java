package com.example.ashish.thebookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashish.helper.UtilClass;
import com.example.ashish.helper.Validation;
import com.example.ashish.pojo.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText edname,edemail,edphone,edpass,edrepass;
    Button btnsubmit;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String userId;
    TextView txtlogin,txtreg;
    boolean val = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edname = (EditText)findViewById(R.id.edname);
        edemail = (EditText)findViewById(R.id.edemail);
        edphone = (EditText)findViewById(R.id.edphone);
        edpass = (EditText)findViewById(R.id.edpass);
        edrepass = (EditText)findViewById(R.id.edrepass);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        txtlogin = (TextView)findViewById(R.id.txtlogin);
        txtreg = (TextView)findViewById(R.id.txtreg);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseInstance.getReference("app_title").setValue("TheBookStore");

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

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edname.getText().toString();
                final String email = edemail.getText().toString();
                String phone = edphone.getText().toString();
                String pass = edpass.getText().toString();
                String repass = edrepass.getText().toString();

                if (name.length()==0 ){

                    edname.setError("Please enter name");
                }else if (email.length()==0){

                    edemail.setError("Please enter Email");

                }else if (phone.length()==0){

                    edphone.setError("Please enter phone number");

                }else if (pass.length()==0){

                    edpass.setError("Please enter Password");


                }else if (!pass.equals(repass)){

                    edrepass.setError("Password doesnot match");

                }else if (!Validation.emailValidator(email)){

                    edemail.setError("Please enter valid email");

                }else if (!Validation.isValidMobile(phone)){

                    edname.setError("Please enter valid phone");

                }else{

                    if (UtilClass.isNetworkAvailable(MainActivity.this)){

                        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Users users = postSnapshot.getValue(Users.class);
                                    if (users.getUseremail().equals(email)){
                                        val = false;

                                    }


                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        if (val) {

                            Users users = new Users();
                            users.setUsername(name);
                            users.setUseremail(email);
                            users.setUserphone(phone);
                            users.setUserpassword(pass);
                            userId = mFirebaseDatabase.push().getKey();
                            mFirebaseDatabase.child(userId).setValue(users);

                            Intent i = new Intent(MainActivity.this, Login.class);
                            startActivity(i);
                            finish();

                        }else{

                            Toast.makeText(MainActivity.this,"User Already Registered",Toast.LENGTH_LONG).show();

                        }
                    }else{

                        Toast.makeText(MainActivity.this,"No Internet, Please connect to the Internet",Toast.LENGTH_LONG).show();
                    }


                }


            }
        });

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        txtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        edemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String email = edemail.getText().toString();
                if (!Validation.emailValidator(email)){

                    edemail.setError("Please enter valid email");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}

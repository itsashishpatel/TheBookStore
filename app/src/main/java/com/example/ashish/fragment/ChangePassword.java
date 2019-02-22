package com.example.ashish.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ashish.helper.UtilClass;
import com.example.ashish.pojo.Users;
import com.example.ashish.thebookstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



@SuppressLint("ValidFragment")
public class ChangePassword extends Fragment {

    Context context;
    EditText edopass,ednpass,edrnpass;
    Button btnchange;
    SharedPreferences sp;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public ChangePassword(Context context){

        this.context = context;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.changepass,container,false);

        edopass = (EditText)view.findViewById(R.id.edopass);
        ednpass = (EditText)view.findViewById(R.id.ednpass);
        edrnpass = (EditText)view.findViewById(R.id.edrnpass);
        btnchange = (Button)view.findViewById(R.id.btnchange);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mFirebaseInstance.getReference("app_title").setValue("TheBookStore");



        final ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("Please wait...");
        pd.setCancelable(true);
        pd.setIndeterminate(false);

        sp = context.getSharedPreferences("users",context.MODE_PRIVATE);

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.show();

                final String stropass = edopass.getText().toString();
                final String strnpass = ednpass.getText().toString();
                String strrnpass = edrnpass.getText().toString();

                if (stropass.length()==0 || strnpass.length()==0 || strrnpass.length()==0){

                    Toast.makeText(context,"Fill all the fields",Toast.LENGTH_LONG).show();
                }else if (!strnpass.equals(strrnpass)){

                    Toast.makeText(context,"Password does not match",Toast.LENGTH_LONG).show();
                }else{

                    if (UtilClass.isNetworkAvailable(context)){

                        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                    Users users = postSnapshot.getValue(Users.class);

                                    if (users.getUseremail().equals(sp.getString("uemail", "")) && users.getUserpassword().equals(stropass)){

                                        if (postSnapshot.getKey().equals(sp.getString("userid", ""))){

                                            mFirebaseDatabase.child(sp.getString("userid", "")).child("userpassword").setValue(strnpass);
                                            Toast.makeText(context,"Password Change successfully",Toast.LENGTH_LONG).show();
                                            edopass.setText("");
                                            edrnpass.setText("");
                                            ednpass.setText("");

                                            if (pd.isShowing()){

                                                pd.dismiss();
                                            }

                                        }
                                        else{

                                            Toast.makeText(context,"Password does not match",Toast.LENGTH_LONG).show();
                                            edopass.setText("");
                                        }


                                    }

                                }
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


            }
        });



        return view;
    }
}

package com.example.ashish.thebookstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        final ProgressDialog pd = new ProgressDialog(SplashScreen.this);
        pd.setTitle("Please wait...");
        pd.setCancelable(true);
        pd.setIndeterminate(false);




        SharedPreferences spuser = getSharedPreferences("users", Context.MODE_PRIVATE);
        final String suser = spuser.getString("uemail","");

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if (suser.length()==0) {
                    pd.show();

                    Intent i = new Intent(SplashScreen.this, Login.class);
                    startActivity(i);
                    finish();
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }

                }else{

                    pd.show();
                    Intent i = new Intent(SplashScreen.this, Main2Activity.class);
                    startActivity(i);
                    finish();
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }

                }
            }
        }, SPLASH_TIME_OUT);
    }


}

package com.example.ashish.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashish.helper.ContactHelper;
import com.example.ashish.helper.WhatsappShare;
import com.example.ashish.thebookstore.R;


@SuppressLint("ValidFragment")
public class
        Contactus extends Fragment {

    Context context;

    public static String FACEBOOK_URL = "";
    public static String FACEBOOK_PAGE_ID = "";

    TextView tcall,temail,tcall2;
    ImageView fbimg,imgwhatsapp;


    public Contactus(Context context){

        this.context = context;

        ContactHelper.insertContact(context.getContentResolver(),
                "Ashish Patel", "+17148572594");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contactus,container,false);

        tcall = (TextView)view.findViewById(R.id.call);
        tcall2 = (TextView)view.findViewById(R.id.call2);
        temail = (TextView)view.findViewById(R.id.email);
        fbimg = (ImageView)view.findViewById(R.id.fbimg);
        imgwhatsapp = (ImageView)view.findViewById(R.id.imgwhatsapp);

        imgwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                WhatsappShare whatsappShare = new WhatsappShare();
                whatsappShare.openWhatsApptext("+17148572594",context);

            }
        });
        fbimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfb();
            }
        });
        tcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callphone();
            }
        });

        tcall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callphone2();
            }
        });

        temail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail(new String[]{"ashishpatel@gmail.com"},"about inquiry");
            }
        });

        return view;
    }

    void callphone(){

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "7148572594"));
        startActivity(intent);
    }

    void callphone2(){

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8183579021"));
        startActivity(intent);
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(Intent.createChooser(intent,"Choose email"));

    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    void openfb(){

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(context);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);

    }

}

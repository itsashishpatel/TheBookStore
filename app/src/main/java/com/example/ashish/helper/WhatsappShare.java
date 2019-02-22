package com.example.ashish.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class WhatsappShare {


   /* private void sendMessageToWhatsAppContact(String number, String file, Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + URLEncoder.encode(CommonStrings.SHARING_APP_MSG, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public void openWhatsApp(String number, String file, Context context) {

       /* String path = Environment.getExternalStorageDirectory()+ "/Images/Test.jpg";

        Log.d("path ",path);*/

       if(number!=null && number.length()!=0) {
           number = number.replaceAll(" ","");
           Log.e("phnumber",number);
       }

        File imgFile = new File(file);
        Uri apkURI;

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M){
            apkURI = FileProvider.getUriForFile(
                   context,
                    context.getApplicationContext()
                            .getPackageName() + ".provider", imgFile);
        }
        else{

            apkURI = Uri.fromFile(imgFile);
        }


        if (imgFile.exists()){

            Log.d("true","true");
        }
        else{
            Log.d("false","true");

        }

        //Log.d("img path",imgFile.getAbsolutePath());



        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp",context);
        if (isWhatsappInstalled) {



            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("application/pdf");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.putExtra(Intent.EXTRA_STREAM, apkURI);
            // sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+number) + "@s.whatsapp.net");//phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            try{

                context.startActivity(sendIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(context, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            context.startActivity(goToMarket);
        }
    }

    public void openWhatsApptext(String number, Context context) {

       /* String path = Environment.getExternalStorageDirectory()+ "/Images/Test.jpg";

        Log.d("path ",path);*/

        if(number!=null && number.length()!=0) {
            number = number.replaceAll(" ","");
            Log.e("phnumber",number);
        }

      /*  File imgFile = new File(file);
        Uri apkURI;

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M){
            apkURI = FileProvider.getUriForFile(
                    context,
                    context.getApplicationContext()
                            .getPackageName() + ".provider", imgFile);
        }
        else{

            apkURI = Uri.fromFile(imgFile);
        }*/


      /*  if (imgFile.exists()){

            Log.d("true","true");
        }
        else{
            Log.d("false","true");

        }
*/
        //Log.d("img path",imgFile.getAbsolutePath());



        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp",context);
        if (isWhatsappInstalled) {



            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Hi");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "How may I help you?");
            // sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91"+number) + "@s.whatsapp.net");//phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            try{

                context.startActivity(sendIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(context, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            context.startActivity(goToMarket);
        }
    }

    public boolean whatsappInstalledOrNot(String uri, Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }



}

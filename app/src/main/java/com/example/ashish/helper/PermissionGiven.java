package com.example.ashish.helper;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class PermissionGiven {
    public static final int STORAGE_PERMISSION_READ = 7;
    public static final int STORAGE_PERMISSION_WRITE = 8;
    public static final int CAMERAPERMISSION = 12;
    public static final int RECORD_AUDIO_PERMISISSION=9;
    public static final int Contact_permission_read=10;
    public static final int Contact_permission_write=11;
    public static final int CALL_PHONE = 1;
    public static final int RequestPermissionCode = 15;

    public static void multipermission(Activity activity){

        ActivityCompat.requestPermissions(activity, new String[]
                {

                        WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        READ_CONTACTS,
                        WRITE_CONTACTS

                }, RequestPermissionCode);
    }

    public static void WriteExternalStorage(Activity activity){
        try {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_READ);
                return;
            }

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_WRITE);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void CameraOption(Activity activity){
        try {

            if (ActivityCompat.checkSelfPermission(activity, CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{CAMERA}, CAMERAPERMISSION);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void RECORD_AUDIO(Activity activity){
        try {

            if (ActivityCompat.checkSelfPermission(activity, RECORD_AUDIO) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{RECORD_AUDIO}, RECORD_AUDIO_PERMISISSION);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void CALL_PER(Activity activity){
        try {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Contact_Permission_Read(Activity activity){
        try {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, Contact_permission_read);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Contact_Permission_Write(Activity activity){
        try {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_CONTACTS}, Contact_permission_write);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void PermissionResultAccept(Activity activity, int requestCode, int grantResults){
        if (requestCode == PermissionGiven.STORAGE_PERMISSION_READ) {
            if (grantResults == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
            } else {
                Toast.makeText(activity,"No permission to read external storage.", Toast.LENGTH_SHORT).show();
                //cc.ToastMessae("No permission to read external storage.");
            }
        } else if (requestCode == PermissionGiven.STORAGE_PERMISSION_WRITE) {
            if (grantResults == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
            } else {
                //cc.ToastMessae("No permission to write external storage.");
                Toast.makeText(activity,"No permission to write external storage.", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == PermissionGiven.CAMERAPERMISSION) {
            if (grantResults == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
            } else {
                //cc.ToastMessae("No permission to write external storage.");
                Toast.makeText(activity,"No permission to camera options.", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == PermissionGiven.RECORD_AUDIO_PERMISISSION) {
            if (grantResults == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
            } else {
                //cc.ToastMessae("No permission to write external storage.");
                Toast.makeText(activity,"No permission to audio recording.", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == PermissionGiven.Contact_permission_read) {
            if (grantResults == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
            } else {
                //cc.ToastMessae("No permission to write external storage.");
                Toast.makeText(activity,"No permission to audio recording.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == PermissionGiven.Contact_permission_write) {
            if (grantResults == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
            } else {
                //cc.ToastMessae("No permission to write external storage.");
                Toast.makeText(activity,"No permission to audio recording.", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == PermissionGiven.CALL_PHONE) {
            if (grantResults == PackageManager.PERMISSION_GRANTED) {
                //permission granted  start reading
            } else {
                //cc.ToastMessae("No permission to write external storage.");
                Toast.makeText(activity,"No permission to audio recording.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

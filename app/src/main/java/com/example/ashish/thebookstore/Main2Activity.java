package com.example.ashish.thebookstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashish.fragment.Addbooks;
import com.example.ashish.fragment.BookLists;
import com.example.ashish.fragment.ChangePassword;
import com.example.ashish.fragment.Contactus;
import com.example.ashish.fragment.UpdateProfile;

import com.example.ashish.helper.PermissionGiven;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences spuser;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ProgressDialog pd = new ProgressDialog(Main2Activity.this);
        pd.setTitle("Please wait...");
        pd.setCancelable(true);
        pd.setIndeterminate(false);
        pd.show();

        setContentView(R.layout.activity_main2);

        if (pd.isShowing()){

            pd.dismiss();
        }


       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               toolbar = (Toolbar) findViewById(R.id.toolbar);
               setSupportActionBar(toolbar);
               toolbar.setTitle("Home");

       /* Arraygetcitycountry a = new Arraygetcitycountry();
        ArrayList<String> ar = a.getcity("AD",Main2Activity.this);

       for (int i = 0;i<ar.size();i++){

           Log.e("cityarray",ar.get(i));
       }
*/

      /*  runOnUiThread(new Runnable() {
            @Override
            public void run() {

                PermissionGiven.WriteExternalStorage(Main2Activity.this);
                PermissionGiven.CALL_PER(Main2Activity.this);
                PermissionGiven.Contact_Permission_Read(Main2Activity.this);
                PermissionGiven.Contact_Permission_Write(Main2Activity.this);

            }
        });*/
               PermissionGiven.multipermission(Main2Activity.this);

               FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
               fab.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                       emailIntent.setData(Uri.parse("mailto: ashishpatel@gmail.com"));
                       startActivity(Intent.createChooser(emailIntent, "Send feedback"));
                   }
               });

               drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
               toggle = new ActionBarDrawerToggle(Main2Activity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
               drawer.addDrawerListener(toggle);
               toggle.syncState();

               NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
               navigationView.setNavigationItemSelectedListener(Main2Activity.this);

               View headerView = navigationView.getHeaderView(0);
               ImageView ivuser = (ImageView)headerView.findViewById(R.id.iv);
               TextView tvuname = (TextView)headerView.findViewById(R.id.txtname);
               TextView tvuemail = (TextView)headerView.findViewById(R.id.txtemail);
               spuser = getSharedPreferences("users", Context.MODE_PRIVATE);
               String strimg = spuser.getString("uimg","");
               String strname = spuser.getString("uname","");
               String stremail = spuser.getString("uemail","");

               if (strimg.length()==0){



               }else{
                   Picasso.with(Main2Activity.this).load(strimg).resize(100,100).into(ivuser);

               }

               tvuname.setText(strname);
               tvuemail.setText(stremail);

               Fragment f1 = new BookLists(Main2Activity.this,1,"home");
               FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
               ft.replace(R.id.fragmentcontainer,f1);
               ft.commit();
               hideKeyboard();
           }
       });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            FragmentManager fm = getSupportFragmentManager();
            int cou = fm.getBackStackEntryCount();
            if (cou==0){
                new AlertDialog.Builder(Main2Activity.this).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Main2Activity.super.onBackPressed();


                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setMessage("Do youo want to exit?").create().show();



            }else{

                fm.popBackStack();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            new AlertDialog.Builder(Main2Activity.this).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SharedPreferences spuser = getSharedPreferences("users", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spuser.edit();
                    editor.clear();
                    editor.commit();
                    Intent i1 = new Intent(Main2Activity.this,Login.class);
                    startActivity(i1);
                    Main2Activity.this.finish();


                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setMessage("Do youo want to exit?").create().show();



            return true;
        }else if (id ==R.id.editpro){

            toolbar.setTitle("Edit Profile");
            Fragment f1 = new UpdateProfile(Main2Activity.this);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentcontainer,f1);
            ft.commit();

        }
        else if (id ==R.id.changepass){

            toolbar.setTitle("Change Password");
            Fragment f1 = new ChangePassword(Main2Activity.this);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentcontainer,f1);
            ft.commit();

        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navhome) {

            toolbar.setTitle("Books");
            Fragment f1 = new BookLists(Main2Activity.this,1,"home");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentcontainer,f1);
            ft.commit();
            // Handle the camera action
        }else if (id == R.id.navnew ) {


        } else if (id == R.id.navinsti) {

            toolbar.setTitle("Search book");
            Fragment f1 = new BookLists(Main2Activity.this,1,"insti");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentcontainer,f1);
            ft.commit();

        } else if (id == R.id.navcity) {

            toolbar.setTitle("Search Book");

            Fragment f1 = new BookLists(Main2Activity.this,1,"city");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentcontainer,f1);
            ft.commit();

        } else if (id == R.id.navpost) {

            toolbar.setTitle("Post Book");

            Fragment f1 = new Addbooks(Main2Activity.this);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentcontainer,f1);
            ft.commit();

        } else if (id == R.id.navmybook) {

            toolbar.setTitle("My Books");

            Fragment f1 = new BookLists(Main2Activity.this,0,"my");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentcontainer,f1);
            ft.commit();

        } else if (id == R.id.navmessage) {

        } else if (id == R.id.navcontactus) {

            toolbar.setTitle("Contact Us");

            Fragment f1 = new Contactus(Main2Activity.this);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentcontainer,f1);
            ft.commit();

        } else if (id == R.id.navfeedback) {


            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto: ashishpatel@gmail.com"));
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

 /*   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionGiven.WriteExternalStorage(Main2Activity.this);
        PermissionGiven.CALL_PER(Main2Activity.this);
        PermissionGiven.Contact_Permission_Read(Main2Activity.this);
        PermissionGiven.Contact_Permission_Write(Main2Activity.this);



    }*/

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }







}

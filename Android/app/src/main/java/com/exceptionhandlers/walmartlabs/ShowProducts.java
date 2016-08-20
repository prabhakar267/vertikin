package com.exceptionhandlers.walmartlabs;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class ShowProducts extends AppCompatActivity implements Constants{

    ListView lv;
    ProgressBar pb;
    String id,query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        lv = (ListView) findViewById(R.id.list);
        pb = (ProgressBar) findViewById(R.id.pb);

        Intent i = getIntent();
        id = i.getStringExtra(PRODUCT_ID);
        query = i.getStringExtra(PRODUCT_QUERY);

        new Book_RetrieveFeed().execute();

        setTitle(query);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public class Book_RetrieveFeed extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... urls) {
            try {
                String uri = "http://api.walmartlabs.com/v1/search?apikey=55x8ep9hz9c5rd957hnnwt2r&query=" +
                        query +
                        "&categoryid="+id;
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

        @Override
        protected void onPostExecute(String Result) {
            try {
                JSONObject YTFeed = new JSONObject(String.valueOf(Result));
                JSONArray YTFeedItems = YTFeed.getJSONArray("items");
                Log.e("response", YTFeedItems + " ");
                pb.setVisibility(View.GONE);
                lv.setAdapter(new Custom_list(ShowProducts.this,YTFeedItems));
            } catch (Exception e) {
                Log.e("dvrev",e.getMessage()+" ");
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }


}

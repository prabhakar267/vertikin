package com.exceptionhandlers.walmartlabs;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Swati garg on 21-06-2015.
 */


public class suggestions_fragment extends Fragment {
    ListView lv;
    EditText itemName;
    Button searchText;

    ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);


        lv = (ListView) rootView.findViewById(R.id.list);
        pb = (ProgressBar) rootView.findViewById(R.id.pb);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_show_products, container, false);
        new RetrieveFeed("earphones").execute();
        return rootView;
    }

    public class RetrieveFeed extends AsyncTask<String, Void, String> {

        String que;
        RetrieveFeed(String que){
            this.que = que;
        }



        protected String doInBackground(String... urls) {
            try {
                String uri = "http://api.walmartlabs.com/v1/search?apiKey=55x8ep9hz9c5rd957hnnwt2r&query="+que;
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
                lv.setAdapter(new Custom_list(activity,YTFeedItems));
            } catch (Exception e) {
                Log.e("dvrev",e.getMessage()+" ");
                e.printStackTrace();
            }
        }
    }

}

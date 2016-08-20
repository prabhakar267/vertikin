package com.exceptionhandlers.walmartlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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


public class category_fragment extends Fragment implements View.OnClickListener, Constants {
    LinearLayout accessri,bags,boys,girls,juniors;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.category_fragment, container, false);

        accessri = (LinearLayout) rootView.findViewById(R.id.accessories);
        bags = (LinearLayout) rootView.findViewById(R.id.bags);
        boys = (LinearLayout) rootView.findViewById(R.id.boys);
        girls = (LinearLayout) rootView.findViewById(R.id.girls);
        juniors = (LinearLayout) rootView.findViewById(R.id.junior);

        accessri.setOnClickListener(this);
        bags.setOnClickListener(this);
        boys.setOnClickListener(this);
        girls.setOnClickListener(this);
        juniors.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {

        Boolean cli = false;
        String query="ACCESSORIES",id="5438_426265";
        switch (view.getId()){


            case R.id.accessories : cli = true;
                query = "ACCESSORIES";
                id="5438_426265";
                break;

            case R.id.bags : cli = true;
                query = "BAGS";
                id="5438_1045799";
                break;
            case R.id.boys : cli = true;
                query = "BOYS";
                id="5438_133199";
                break;
            case R.id.girls : cli = true;
                query = "GIRL";
                id="5438_133202";
                break;
            case R.id.junior : cli = true;
                query = "JUNIOR";
                id="5438_133201";
                break;

        }

        if(cli){

            Intent i = new Intent(activity,ShowProducts.class);
            i.putExtra(PRODUCT_ID,id);
            i.putExtra(PRODUCT_QUERY,query);
            startActivity(i);
        }

    }
}

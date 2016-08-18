package com.exceptionhandlers.walmartlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class test_fragment extends Fragment {


    static Activity activity;

    public test_fragment() {}

    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_main, container, false);

        Button b = (Button) v.findViewById(R.id.click);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final File file = new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath(), "data.txt");
                Scanner in = null;
                String whatToSend="";
                try {
                    in = new Scanner(file);
                    while (in.hasNext()) { // iterates each line in the file
                        String line = in.nextLine();
                        if(line.length()!=0)
                        whatToSend+= process(line)+"\n";
                    }

                    in.close(); // don't forget to close resource leaks

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Log.e("final string",whatToSend+" ");


                String[] words = whatToSend.split(" ");
                Map<String, Integer> map = new HashMap<>();
                for (String w : words) {
                    Integer n = map.get(w);
                    n = (n == null) ? 1 : ++n;
                    map.put(w, n);
                }

                JSONObject ob = new JSONObject(map);

                Log.e("string is",ob.toString()+" ");

                /*FileInputStream fis = null;
                try {
                    //String path = Environment.getExternalStorageDirectory().toString();
                    fis = activity.openFileInput("data.txt");
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.e("ERROR",e.getMessage() +" ");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            }
        });


        return v;
    }


    String process(String line){

        String procc="";

        if(line==null)
            return "";

        int len = line.length();
        for(int i=0;i<len;){

            if(i+4 < len && line.charAt(i)=='[' && line.charAt(i+1)=='D' && line.charAt(i+2)=='e' && line.charAt(i+3)=='l'
                    && line.charAt(i+4)==']'){
                i = i+5;
                if(procc.length()!=0)
                procc = procc.substring(0,procc.length()-1);
            }else if(i+4 < len && line.charAt(i)=='[' && line.charAt(i+1)=='S' && line.charAt(i+2)=='y' && line.charAt(i+3)=='m'
                    && line.charAt(i+4)==']') {
                i = i + 5;
            }else if(i+3 < len && line.charAt(i)=='[' && line.charAt(i+1)=='S' && line.charAt(i+2)=='h' && line.charAt(i+3)==']') {
                i = i + 4;
            }else {
                procc += line.charAt(i);
                i++;
            }
        }

        return procc;

    }



/*
    public class getcitytask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {


            try {
                String uri = Constants.apilink +
                        "all-cities.php";
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                String readStream = Utils.readStream(con.getInputStream());
                Log.e("here", readStream + " ");
                return readStream;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String result) {


            if (result == null) {
                Toast.makeText(activity, "No result", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject ob = new JSONObject(result);
                JSONArray ar = ob.getJSONArray("cities");
                pb.setVisibility(View.GONE);
                FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
                List<Friend> friends = new ArrayList<>();
                for (int i = 0; i < ar.length(); i++) {


                    double color = Math.random();
                    int c = (int) (color * 100) % 8;


                    int colo;
                    switch (c) {
                        case 0:
                            colo = R.color.sienna;
                            break;
                        case 1:
                            colo = R.color.saffron;
                            break;
                        case 2:
                            colo = R.color.green;
                            break;
                        case 3:
                            colo = R.color.pink;
                            break;
                        case 4:
                            colo = R.color.orange;
                            break;
                        case 5:
                            colo = R.color.saffron;
                            break;
                        case 6:
                            colo = R.color.purple;
                            break;
                        case 7:
                            colo = R.color.blue;
                            break;
                        default:
                            colo = R.color.blue;
                            break;
                    }

                    String dr = ar.getJSONObject(i).optString("image", "yolo");

                    friends.add(new Friend(

                            ar.getJSONObject(i).getString("id"),
                            dr,
                            ar.getJSONObject(i).getString("name"), colo,
                            ar.getJSONObject(i).getString("lat"),
                            ar.getJSONObject(i).getString("lng"),

                            "Know More", "View on Map", "Fun Facts", "View Website"));




                }


                lv.setAdapter(new FriendsAdapter(activity, friends, settings));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Friend f = (Friend) lv.getAdapter().getItem(position);
                        Toast.makeText(activity, f.getNickname(), Toast.LENGTH_SHORT).show();


                        Intent i = new Intent(activity,FinalCityInfo.class);
                        i.putExtra("id_", f.getId());
                        i.putExtra("name_", f.getNickname());
                        i.putExtra("image_",f.getAvatar());
                        startActivity(i);


                    }
                });


            } catch (JSONException e1) {
                e1.printStackTrace();
                Log.e("heer", e1.getMessage() + " ");
            }
        }

    }
*/







    //collegename autocomplete
/*
    class tripautocomplete extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {


            String readStream = null;
            try {
                String uri = Constants.apilink +
                        "city/autocomplete.php?search=" + nameyet.trim();
                Log.e("executing",uri+" ");
                URL url = new URL(uri);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                readStream = Utils.readStream(con.getInputStream());
                Log.e("executing",readStream);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return readStream;



        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("YO", "Done");
            JSONArray arr;
            final ArrayList list, list1;
            try {
                arr = new JSONArray(result);
                Log.e("erro",result+" ");

                list = new ArrayList<String>();
                list1 = new ArrayList<String>();
                list2 = new ArrayList<String>();
                for (int i = 0; i < arr.length(); i++) {
                    try {
                        list.add(arr.getJSONObject(i).getString("name"));
                        list1.add(arr.getJSONObject(i).getString("id"));
                        list2.add(arr.getJSONObject(i).optString("image","http://i.ndtvimg.com/i/2015-12/delhi-pollution-traffic-cars-afp_650x400_71451565121.jpg"));
                        Log.e("adding","aff");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("error ", " " + e.getMessage());
                    }
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                        (activity.getApplicationContext(), R.layout.spinner_layout, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cityname.setThreshold(1);
                cityname.setAdapter(dataAdapter);
                cityname.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        Log.e("jkjb", "uihgiug" + arg2);

                        cityid = list1.get(arg2).toString();
                        Intent i = new Intent(activity,FinalCityInfo.class);
                        i.putExtra("id_", cityid);
                        i.putExtra("name_", list.get(arg2).toString());
                        i.putExtra("image_",list2.get(arg2).toString());
                        startActivity(i);

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("erro",e.getMessage()+" ");
            }

        }
    }
*/




    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.activity = (Activity) activity;
    }

}

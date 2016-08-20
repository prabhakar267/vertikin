package com.exceptionhandlers.walmartlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                sendNotification();
            }
        }, 30000);


        Button b = (Button) v.findViewById(R.id.click);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                WifiManager m_wm = (WifiManager)activity.getSystemService(Context.WIFI_SERVICE);
                String m_wlanMacAdd = m_wm.getConnectionInfo().getMacAddress();

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

                Map<String, String > map_final = new HashMap<>();

                map_final.put("user_data",whatToSend);
                map_final.put("gcm_id",m_wlanMacAdd);

                JSONObject ob = new JSONObject(map_final);
                Log.e("string is",ob.toString()+" ");

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


    void sendNotification(){


        Intent intent = new Intent(activity, MainActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(activity, (int) System.currentTimeMillis(), intent, 0);

        Bitmap notifIcon = BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon);
// build notification
// the addAction re-use the same intent to keep the example short
        String longText="Seems like you want to buy \"earphones\". Check it out at Walmart!\n \nWas " +
                "this suggestion helpful?";
        Notification n  = new Notification.Builder(activity)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(notifIcon)
                .setContentText("Seems like you want to buy \"earphones\". Check it out at Walmart!")
                .setContentTitle("Walmart")
                .setContentIntent(pIntent)
                .setStyle(new Notification.BigTextStyle().bigText(longText))
                .setAutoCancel(true)
                .addAction(R.drawable.ic_check_black_24dp, "Helpful", pIntent)
                .addAction(R.drawable.ic_cancel_black_24dp, "Not helpful", pIntent)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);

        notificationManager.notify(100, n);

    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.activity = (Activity) activity;
    }

}

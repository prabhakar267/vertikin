package com.exceptionhandlers.walmartlabs;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class help_fragment extends Fragment {

    static Activity activity;

    public help_fragment() {}

    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_help, container, false);


        Button b = (Button) v.findViewById(R.id.click);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               sendNotification();
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

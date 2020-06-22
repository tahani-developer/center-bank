package com.falconssoft.centerbank;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.falconssoft.centerbank.Models.notification;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class AlertScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList <notification> notificationArrayList;
    LinearLayoutManager layoutManager;
    NotificationManager notificationManager;
    static int id=1;
    TextView mainText;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_main_screen);
        recyclerView = findViewById(R.id.recycler);
        mainText=findViewById(R.id.textView);
        mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentapiVersion = Build.VERSION.RELEASE;

                if (Double.parseDouble(currentapiVersion.substring(0,1) )>=8) {
                    // Do something for 14 and above versions

//                                show_Notification("Thank you for downloading the Points app, so we'd like to add 30 free points to your account");
                    show_Notification("Check  app, Recive new Check 1234");


                } else {

                    notification(" Recive new Check, click to show detail");

                }
            }
        });
        notificationArrayList=new ArrayList<>();
        fillListNotification();
        fillListNotification();
        fillListNotification();
        layoutManager = new LinearLayoutManager(AlertScreen.this);
        layoutManager.setOrientation(VERTICAL);
        runAnimation(recyclerView,0);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final NotificatioAdapter notificationAdapter = new NotificatioAdapter(AlertScreen.this, notificationArrayList);
        recyclerView.setAdapter(notificationAdapter);


        Toast.makeText(AlertScreen.this, "Saved", Toast.LENGTH_SHORT).show();
    }

    private void fillListNotification() {

        notification one=new notification();
        one.setAmount_check("1500 JD");
        one.setDate("21-6-2020");
        one.setSource("Companey Source");
        notificationArrayList.add(one);
        notificationArrayList.add(one);
        notificationArrayList.add(one);


    }
    private void runAnimation(RecyclerView recyclerView, int type) {
        Context context=recyclerView.getContext();
        LayoutAnimationController controller=null;
        if(type==0)
        {
            controller= AnimationUtils.loadLayoutAnimation(context,R.anim.layout_filldown);
            NotificatioAdapter notificationAdapter = new NotificatioAdapter(AlertScreen.this, notificationArrayList);
            recyclerView.setAdapter(notificationAdapter);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String detail){

        Intent intent=new Intent(getApplicationContext(),AlertScreen.class);
        String CHANNEL_ID="MYCHANNEL";

        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_HIGH);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText("POINT APP Notification ......")
                .setContentTitle("Point Gift From Point App")
                .setStyle(new Notification.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" ")
                        .setSummaryText(""))
//                .setContentIntent(pendingIntent)
//                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setChannelId(CHANNEL_ID)
//                .setSmallIcon(R.drawable.gift)
                .build();


        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


    }
    private void notification (String detail){// this to use
//        final Intent intent = new Intent(this, MainActivity.class);
//        intent.setData(Uri.parse("data"));
//        intent.putExtra("key", "clicked");
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent), 0);
//        try {
//            // Perform the operation associated with our pendingIntent
//            pendingIntent.send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
        NotificationCompat.Builder nbuilder=new NotificationCompat.Builder(AlertScreen.this)
                .setContentTitle("Check APP Notification ......")
                .setContentText("New Check... click to show details ")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(detail)
                        .setBigContentTitle(" New Check ").setSummaryText(""))
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id,nbuilder.build());


    }
}
/*
Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
mBuilder.setSound(alarmSound);
*/

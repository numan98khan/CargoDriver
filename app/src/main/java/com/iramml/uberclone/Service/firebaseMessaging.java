package com.iramml.uberclone.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iramml.uberclone.Activities.CustommerCall;
import com.iramml.uberclone.Model.Pickup;
import com.iramml.uberclone.R;

import java.util.HashMap;
import java.util.Map;

public class firebaseMessaging extends FirebaseMessagingService{

    // Below here is for custom notifications including onMessageReceived and sendNotification
    private static int count = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//Here notification is recieved from server
        try {
            Log.d("DEBUG", "Notification Builder : " + remoteMessage.getData().get("body"));
            Pickup pickup = new Gson().fromJson(remoteMessage.getData().get("body"), Pickup.class);
            sendNotification(remoteMessage.getData().get("title"), pickup);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendNotification(String title, Pickup dataLoad) {
        Intent intent = new Intent(getApplicationContext(), CustommerCall.class);
        //you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Add Any key-value to pass extras to intent
//        Log.d("DEBUG", "Latitude data : " + dataLoad.getLastLocation().latitude);
        intent.putExtra("lat", dataLoad.coord(0));
        intent.putExtra("lng", dataLoad.coord(1));
        intent.putExtra("lat_end", dataLoad.coord(2));
        intent.putExtra("lng_end", dataLoad.coord(3));
        intent.putExtra("rider", dataLoad.getID());
        // Have to explicitly get the token as string
        intent.putExtra("token", dataLoad.getToken().getToken());

        Log.d("sendNotification pick", dataLoad.getPickup_id().toString());

        intent.putExtra("pickup_id", dataLoad.getPickup_id());

        Log.d("DEEEBUUUG", dataLoad.getToken().getToken());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //For Android Version Orio and greater than orio.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel("Sesame", "Sesame", importance);
//            mChannel.setDescription(dataLoad.get("message"));
            mChannel.setDescription("message");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotifyManager.createNotificationChannel(mChannel);
        }
        //For Android Version lower than oreo.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Seasame");
        mBuilder.setContentTitle(title)
//                .setContentText(dataLoad.get("message"))
                .setContentText("message")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_foreground))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(Color.parseColor("#FFD600"))
                .setContentIntent(pendingIntent)
                .setChannelId("Sesame")
                .setPriority(NotificationCompat.PRIORITY_LOW);

        mNotifyManager.notify(count, mBuilder.build());
        count++;
    }



    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }
    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }
}

package gbpec.comida.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import gbpec.comida.Config;
import gbpec.comida.R;
import gbpec.comida.SessionManager;
import gbpec.comida.SplashScreen;
import gbpec.comida.utils.NotificationUtils;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    SessionManager sessionManager;
    String parsedDistance;

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        //
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        sessionManager = new SessionManager(getApplicationContext());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        HashMap<String, String> user = sessionManager.getUserDetails();


        //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
        //You can change as per the requirement.

        //message will contain the Push Messageif

       if (user.get(sessionManager.USER_TYPE).equals("ngo") ) {
            String message = remoteMessage.getData().get("message");
            //imageUri will contain URL of the image to be displayed with Notification
            //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
            //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
            String TrueOrFlase = remoteMessage.getData().get("is_background");
            String type = remoteMessage.getData().get("Validity");
            String Latitude = remoteMessage.getData().get("Latitude");
            String Longitude = remoteMessage.getData().get("Longitude");
            Location loc1 = new Location("");

            double lat1 = Double.parseDouble(Latitude);
            double long1 = Double.parseDouble(Longitude);
            double lat2 = Double.parseDouble(user.get(sessionManager.USER_LATITUDE));
            double long2 = Double.parseDouble(user.get(sessionManager.USER_LONGITUDE));

            double dist = distance(lat1, long1, lat2, long2);




                sendNotification(message, Double.toString(dist));
        }



    }
    private void sendNotification(String messageBody,String type) {
        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.not_logo))/*Notification icon image*/
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(messageBody)
                .addAction(R.drawable.accept_icon, "Accept", pendingIntent) // #0
                .addAction(R.drawable.open_icon, "Open", pendingIntent)  // #1
                .setContentText(type)
                .setStyle(inboxStyle)/*Notification with Image*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent);
        SharedPreferences prefs = getSharedPreferences(MyFirebaseMessagingService.class.getSimpleName(), Context.MODE_PRIVATE);
        int notificationNumber = prefs.getInt("notificationNumber", 0);
        SharedPreferences.Editor editor = prefs.edit();
        notificationNumber++;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationNumber /* ID of notification */, notificationBuilder.build());
        editor.putInt("notificationNumber", notificationNumber);
        editor.commit();




    }

    /*
    *To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Aut
            // o-generated catch block
            e.printStackTrace();
            return null;

        }
    }




    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");


            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);

            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), SplashScreen.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515* 1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void getDistanceOnRoad(String latitude, String longitude,
                                     String prelatitute, String prelongitude) {

        String url ="http://maps.googleapis.com/maps/api/directions/json?origin=" + latitude + "," + longitude + "&destination=" + prelatitute + "," + prelongitude + "&sensor=false&units=metric&mode=driving";
        String distance;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               showJSON(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
    private void   showJSON(String response){


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("routes");
            JSONObject routes = array.getJSONObject(0);
            JSONArray legs = routes.getJSONArray("legs");
            JSONObject steps = legs.getJSONObject(0);
            JSONObject distance = steps.getJSONObject("distance");
            parsedDistance =distance.getString("text");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

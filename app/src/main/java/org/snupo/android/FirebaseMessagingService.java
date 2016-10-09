package org.snupo.android;

/**
 * Created by minim on 2016-09-12.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {

        JSONObject object = new JSONObject(remoteMessage.getData());
		Log.i("recieved data", object.toString());
     //   Log.i("JSON_OBJECT", object.toString());
        try
        {
            jsonParser(object);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    private void jsonParser(JSONObject received) throws JSONException
    {
        String title = received.getString("title");
        Log.i("title", title);
        String user_id = received.getString("user_name");
        Log.i("user_id", user_id);
        String event_type = received.getString("event_type");
        Log.i("eventtype", event_type);
        String mid= received.getString("mid");
        Log.i("mid", mid);
        String document_srl = received.getString("document_srl");
        Log.i("document_srl", document_srl);
        String comment_srl = "";
        Log.i("comment_srl", comment_srl);
        String message = getMessage(user_id,mid, title,event_type);

        if(event_type.equals("ND"))
        {
            sendPushNotification(user_id, document_srl, comment_srl, message);
        }
        else
        {
            comment_srl = received.getString("comment_srl");
            sendPushNotification(user_id, document_srl, comment_srl,message);
        }

		return;
    }

    private String getMessage (String user_id, String mid, String title, String event_type)
    { //Message Context @JaeDong
        switch(event_type) {
            case "ND" :
                return (user_id + "님이 " + BrowserTitleConverter.midToBrowserTitle(mid) + " 게시판에 글을 썼어요");
            case "NC" :
                return (user_id+"님이 " + title+"에 댓글을 남겼어요.");
            case "MDC" :
                return (user_id+"님이 내가 쓴 글에 댓글을 남겼어요.");
            case "MCC" :
                return (user_id+"님이 내가 쓴 댓글에 댓글을 달았어요.");
            case "TC" :
                return (user_id+"님이 회원님을 태그하였습니다.");
            case "TD" :
                return (user_id+"님이 회원님을 태그하였습니다.");
        }
        return "오류가 발생하였습니다.";
    }

    private void sendPushNotification(String user_id, String document_srl,
                                      String comment_srl, String message) {

     //   Log.i(TAG, "message: " + user_id);

        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferenceUtil.putSharedBoolean(getApplicationContext(), "first", false);
        SharedPreferenceUtil.putSharedString(getApplicationContext(), "document_srl", document_srl);
        SharedPreferenceUtil.putSharedString(getApplicationContext(), "comment_srl", comment_srl);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT
        |PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.snupomain)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.snupomain_high))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(255, 500, 2000)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);

        notificationManager.notify(0 , notificationBuilder.build());
    }

}
package fun.dooit.wifi_mac.service;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import fun.dooit.wifi_mac.MainActivity;
import fun.dooit.wifi_mac.R;
import fun.dooit.wifi_mac.bean.NotificationBean;


public class FCMFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM";


    public FCMFirebaseMessagingService() {
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.d(TAG, "onDeletedMessages() called");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        String title = "";
        String content = "";
        String sound;
        String notifType = "";
        int badge = 0;

        NotificationBean bean = new NotificationBean();
        Map<String, String> notifData = remoteMessage.getData();

        //前景 Data message
        if (notifData.size() > 0) {
            Log.d(TAG, "Message data payload: " + notifData);
            badge = notifData.containsKey("badge") ? Integer.parseInt(notifData.get("badge")) : 0;
            bean.setType(notifData.containsKey("sinyiPN") ? notifData.get("sinyiPN") : "");
            bean.setTitle(notifData.containsKey("title") ? notifData.get("title") : "");
            bean.setContent(notifData.containsKey("body") ? notifData.get("body") : "");
        }


        //背景 Notification message
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        if (notification != null) {
            title = notification.getTitle();
            content = notification.getBody();
            bean.setTitle(title);
            bean.setContent(content);
        }


        this.sendNotification(bean, badge);
    }

    private void sendNotification(NotificationBean bean, final int badge) {
        Log.d(TAG, "sendNotification() called with: notificationBean = [" + bean + "], badge = [" + badge + "]");
        if (bean == null || TextUtils.isEmpty(bean.getContent())) {
            Log.d(TAG, "推播訊息為空");
            return;
        }

       /* if (badge > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BadgeUtil.setBadgeCount(FCMFirebaseMessagingService.this, badge);
                }
            }).start();
        }*/


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notifBean", bean);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        int iconRecID = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
        Bitmap iconBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        long when = System.currentTimeMillis();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(iconRecID)
                .setLargeIcon(iconBmp)
                .setAutoCancel(true)
                .setWhen(when)
                .setContentTitle(bean.getTitle())
                .setContentText(bean.getContent())
                .setContentIntent(pendingIntent);

        //style-長篇文字
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBuilder(notificationBuilder);
        bigTextStyle.setBigContentTitle(bean.getTitle());
        bigTextStyle.bigText(bean.getContent());

        //style-圖片
        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.banner_fail);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(bitmap);
        bigPictureStyle.setSummaryText("setSummaryText");
        bigPictureStyle.setBigContentTitle("setBigContentTitle");
        bigPictureStyle.bigLargeIcon(iconBmp);
        bigPictureStyle.setBuilder(notificationBuilder);*/

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, bigTextStyle.build());

    }
}

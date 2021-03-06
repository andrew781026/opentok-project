package andrew.com.riko.www.doctorapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import andrew.com.riko.www.doctorapplication.dao.TaskDAO;
import andrew.com.riko.www.doctorapplication.model.Task;

// 此 service 用來接收 message
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "web-FirebaseMsgService";
    private static int counter = 0;
    private TaskDAO taskDAO = new TaskDAO(this);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // 訊息有2種模式 , 1 : 只包含文字的 notification , 2: 有 key , value pair 的 map

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            this.sendNotification(remoteMessage.getNotification(),remoteMessage.getData());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    private Task getTask(Map<String,String> datas){

        int missionId = Integer.valueOf(datas.get("mission_id")) ;
        String title = datas.get("mission_type");
        int age = counter + 20;
        String name = datas.get("name");
        String description = datas.get("description");

        Task task = new Task(missionId,title,age,name,description);
        return task ;
    }

    private void sendNotification(RemoteMessage.Notification notification, Map<String,String> datas) {

        Intent intent ;
        String missionType = datas.get("mission_type");

        if ( "掛號".equalsIgnoreCase(missionType) ){
            taskDAO.create(  getTask(datas) );
            intent = new Intent("android.intent.action.andrew.doctor.appointment");
        }else if ( "諮詢".equalsIgnoreCase(missionType) ){
            taskDAO.create(  getTask(datas) );
            intent = new Intent("android.intent.action.andrew.doctor.advice");
        }else {
            intent = new Intent(this,MainActivity.class);
        }

        //  將資料放入 intent
        for ( String key : datas.keySet() ){
            intent.putExtra(key,datas.get(key));
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(notification.getTitle())
                        .setContentText(notification.getBody())
                        .setSmallIcon(R.drawable.ic_home_black_24dp)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(counter++ /* ID of notification */, notificationBuilder.build());
    }


}

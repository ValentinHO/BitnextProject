package utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.valen.proyectobitnext.MainActivity;
import com.example.valen.proyectobitnext.R;
import com.example.valen.proyectobitnext.SecurityCode;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by valen on 07/08/2016.
 */
public class MyFireBaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            Log.d("Mensaje", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        else
        {
            String from = remoteMessage.getFrom();
            Map data = remoteMessage.getData();

            sendNotification(remoteMessage.getData().get("message"));
        }
    }


    private void sendNotification(String messageBody) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")

        Intent intent = new Intent(this, SecurityCode.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Bitnext Alertas")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
        //.setContentIntent(pendingIntent);


        notificationManager.notify(1, notificationBuilder.build());
    }
}

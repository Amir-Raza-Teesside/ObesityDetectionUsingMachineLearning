package uk.ac.tees.aad.obesity2.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import uk.ac.tees.aad.obesity2.MainActivity;
import uk.ac.tees.aad.obesity2.R;

public class MyService extends Service {

    private Integer AlarmHour;
    private Integer AlarmMinute;
    Ringtone ringtone;
    Timer t = new Timer();
    private  static final String channelId = "My notification";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        AlarmHour = intent.getIntExtra("HOURS",0);
        AlarmMinute = intent.getIntExtra("MINUTES",0);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(),RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));





        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);




        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Ss")
                .setContentText("mytext")
                .setSmallIcon(R.drawable.logos)

                .addAction(R.drawable.ic_launcher_foreground,getString(R.string.app_name),pendingIntent).build();


        startForeground(1,notification);

        NotificationChannel notificationChannel = new NotificationChannel(channelId,"Alarm", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);



        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if((Calendar.getInstance().getTime().getHours()  == AlarmHour)
                        && (Calendar.getInstance().getTime().getMinutes() == AlarmMinute+2))
                {

                    ringtone.play();
                }
                else{
                    ringtone.stop();
                }
            }
        },0,200);




        return super.onStartCommand(intent, flags, startId);



    }



    private void CreateNotification()
    {

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);




        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Ss")
                .setContentText("mytext")
                .setSmallIcon(R.drawable.ic_launcher_foreground)

                .addAction(R.drawable.ic_launcher_foreground,getString(R.string.app_name),pendingIntent).build();


        startForeground(1,notification);

        NotificationChannel notificationChannel = new NotificationChannel(channelId,"Alarm", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);


    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        t.cancel();
        super.onDestroy();
    }
}
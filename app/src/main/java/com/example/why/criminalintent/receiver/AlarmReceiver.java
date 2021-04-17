package com.example.why.criminalintent.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.why.criminalintent.R;
import com.example.why.criminalintent.service.LongRunningService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //设置通知内容并在onReceive()这个函数执行时开启
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setContentTitle("再不处理就爆炸了，注意注意！！")
                .setContentText("注意注意！！该收拾你的陋习了")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        manager.notify(1,notification);


//        //再次开启LongRunningService这个服务，从而可以
//        Intent i = new Intent(context, LongRunningService.class);
//        context.startService(i);
    }


}
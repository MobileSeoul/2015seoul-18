package com.eatinghabit.sehyunpark.eatinghabits;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sehyunpark on 2015-10-25.
 */
    public class PendingRecever extends BroadcastReceiver {

    NotificationManager nm;

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, PedometerCloseActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

            Log.d("TAG", "리시버 중 ");

            Notification.Builder builder = new Notification.Builder(context);

            // 작은 아이콘 이미지.
            builder.setSmallIcon(R.drawable.zzz);

            // 알림이 출력될 때 상단에 나오는 문구.
            builder.setTicker("만보기가 초기화 되셨습니다.");

            // 알림 출력 시간.
            builder.setWhen(System.currentTimeMillis());

            // 알림 제목.
            builder.setContentTitle("만보기가 초기화 되셨습니다.");


            builder.setContentIntent(pendingIntent);

            // 알림 내용.
            builder.setContentText("안녕히 주무세요");


            // 행동 최대3개 등록 가능.
            builder.addAction(R.mipmap.ic_launcher, "Show", pendingIntent);
            builder.addAction(R.mipmap.ic_launcher, "Hide", pendingIntent);
            builder.addAction(R.mipmap.ic_launcher, "Remove", pendingIntent);
            // 알림 터치시 반응 후 알림 삭제 여부.
            builder.setAutoCancel(true);

            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(123455, builder.build());

        }

    }
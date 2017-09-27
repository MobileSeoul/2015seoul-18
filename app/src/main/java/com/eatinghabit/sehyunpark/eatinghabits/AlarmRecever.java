package com.eatinghabit.sehyunpark.eatinghabits;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by sehyunpark on 2015-10-25.
 */
    public class AlarmRecever extends BroadcastReceiver {
    public Vibrator mVib;
    Notification.Builder no;
    NotificationManager nm;
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, AlarmIntro.class), PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder builder = new Notification.Builder(context);

            // 작은 아이콘 이미지.
            builder.setSmallIcon(R.drawable.alarm);

            // 알림이 출력될 때 상단에 나오는 문구.
            builder.setTicker("아침 드실 시간이예요!");

            // 알림 출력 시간.
            builder.setWhen(System.currentTimeMillis());

            // 알림 제목.
            builder.setContentTitle("아침 드실 시간이예요!");



            // 알림 내용.
            builder.setContentText("오늘 하루도 화이팅!");

            // 알림시 사운드, 진동, 불빛을 설정 가능.
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);

            // 알림 터치시 반응.
            builder.setContentIntent(pendingIntent);


            // 알림 터치시 반응 후 알림 삭제 여부.
            builder.setAutoCancel(true);

            // 우선순위.
            //builder.setPriority(NotificationCompat.PRIORITY_MAX);

            // 행동 최대3개 등록 가능.
            builder.addAction(R.mipmap.ic_launcher, "Show", pendingIntent);
            builder.addAction(R.mipmap.ic_launcher, "Hide", pendingIntent);
            builder.addAction(R.mipmap.ic_launcher, "Remove", pendingIntent);

            // 고유ID로 알림을 생성.
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(123456, builder.build());

            mVib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            mVib.vibrate(new long[]{
                    300, 50, 500, 50,
                    1000,
                    300, 50, 500, 50,
                    1000,
                    300, 50, 500, 50,
                    1000,
                    300, 50, 500, 50,
                    1000,
                    300, 50, 500, 50,
                    1000
            },-1);


        }
        public void cancleVib(){
            mVib.cancel();
            nm.cancelAll();
        }
    }
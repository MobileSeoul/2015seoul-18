package com.eatinghabit.sehyunpark.eatinghabits;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import java.util.Calendar;

public class PedometerActivity extends AppCompatActivity implements SensorEventListener {


    Intent intentMyService;
    BroadcastReceiver receiver;
    PendingIntent pendingIntent;
    AnimatedCircleLoadingView animatedCircleLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        
        intentMyService = new Intent(this, SensorService.class);

        receiver = new StepReceiver();
        Log.v("TAG", "pedoActivity onCreate");

        startService(intentMyService);
        Intent alarmIntent = new Intent(this, PendingRecever.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        start(23, 58);
        animatedCircleLoadingView = (AnimatedCircleLoadingView) findViewById(R.id.circle_loading_view);
        animatedCircleLoadingView.startIndeterminate();
        startPercentMockThread();
        animatedCircleLoadingView.stopOk();
    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(26);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5500);
                    finish();
                }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        };
        new Thread(runnable2).start();
    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("TAG", "pedoActivity onResume");
        try {
            IntentFilter intentFilter = new IntentFilter("stepcount");
            registerReceiver(receiver, intentFilter);

        } catch (Exception e) { }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("TAG", "pedoActivity onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("TAG", "pedoActivity onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("TAG", "pedoActivity onStop");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.v("TAG", "pedoActivity onPause");
        unregisterReceiver(receiver);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("TAG", "pedoActivity onDestroy");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class StepReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("stepcount")) { }
        }
    }


    public void start(int hour, int minute) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        int interval = 60000 * 60 * 24;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        Log.d("TAG","리시버 시작");
    }
}


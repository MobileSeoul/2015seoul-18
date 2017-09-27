package com.eatinghabit.sehyunpark.eatinghabits;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by jj on 2015-10-24.
 */

public class SensorService extends Service implements SensorEventListener {

    private SensorManager sensorManager = null;
    private Sensor accelerometerSensor = null;

    private float x, y, z;
    private float lastX, lastY, lastZ;
    private float speed;
    private long lastTime;

    public static int step;
    public static boolean isStart;

    private static final int SHAKE_THRESHOLD = 450;

    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastTime = System.currentTimeMillis();

        String text = null;
        if( step == -1) {
            step = 0;
        }else {
            try {
                File file = getFileStreamPath("stepInfo.txt");
                if (file.exists()) {

                } else {
                    WriteTextFile("stepInfo.txt", "0");
            }
                FileInputStream fis = new FileInputStream(file);
                Reader in = new InputStreamReader(fis);
                int size = fis.available();
                char[] buffer = new char[size];
                in.read(buffer);
                in.close();

                text = new String(buffer);


            } catch (Exception e) { }

            if (text != "") {
                text.trim();
                step = Integer.parseInt(text);
            } else {
                step = 0;
            }
        }
        isStart = true;

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);

            if (gabOfTime > 100) {
                lastTime = currentTime;

                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime
                        * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Intent intent = new Intent("stepcount");
                    sendBroadcast(intent);
                    step++;
                    Log.v("STEP", "STEP: " + step);
                }
                lastX = x;
                lastY = y;
                lastZ = z;
            }
        } else if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            Intent intent = new Intent("stepcount");
            sendBroadcast(intent);
            step++;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (accelerometerSensor != null)
            sensorManager.registerListener(this, accelerometerSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (sensorManager != null)
            sensorManager.unregisterListener(this);
        isStart = false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean WriteTextFile(String strFileName, String strBuf) {
        try {
            File file = getFileStreamPath(strFileName);
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(strBuf);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}


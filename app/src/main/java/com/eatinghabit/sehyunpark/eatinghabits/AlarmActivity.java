package com.eatinghabit.sehyunpark.eatinghabits;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;
import com.fenjuly.library.ArrowDownloadButton;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sehyunpark on 2015-10-25.
 */

    public class AlarmActivity extends FragmentActivity implements TimePickerDialogFragment.TimePickerDialogHandler {


    Bundle extraBundle;
    Intent intent;

    int count = 0;
    int progress = 0;
    ArrowDownloadButton button;
    int Hour;
    int Minute;
    private PendingIntent pendingIntent;
    public static Context mContext;


        /*
         * 통지 관련 맴버 변수
         */


        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_alarm);

            mContext = this;
            TimePickerBuilder dpb = new TimePickerBuilder()
                    .setFragmentManager(getSupportFragmentManager())
                    .setStyleResId(R.style.BetterPickersDialogFragment_Light);
            dpb.show();

            button = (ArrowDownloadButton)findViewById(R.id.arrow_download_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((count % 2) == 0) {
                        button.startAnimating();
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progress = progress + 1;
                                        button.setProgress(progress);
                                    }
                                });
                            }
                        }, 800, 20);
                    } else {
                        extraBundle = new Bundle();
                        extraBundle.putInt("hour",Hour);
                        extraBundle.putInt("minute",Minute);

                        intent = new Intent();
                        intent.putExtras(extraBundle);
                        setResult(RESULT_OK, intent);
                        start(Hour, Minute);


                        finish();

                    }

                    count++;

                }

            });

            Intent alarmIntent = new Intent(this, AlarmRecever.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);









        }
    public void start(int hour, int minute) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
       // int interval = 6000 * 60 *24;
        int interval = 60000 * 60 * 24;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

            // manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
            Toast.makeText(this, "추가 완료", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
        Hour = hourOfDay;
        Minute = minute;
        //("" + hourOfDay + ":" + String.format("%02d", minute));
    }
}


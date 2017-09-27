package com.eatinghabit.sehyunpark.eatinghabits;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.eatinghabit.sehyunpark.eatinghabits.extra.ListViewMultiChartActivity;
import com.eatinghabit.sehyunpark.eatinghabits.interfaces.OnProgressBarListener;
import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Timer;
import java.util.TimerTask;

import me.drakeet.materialdialog.MaterialDialog;


public class EatingTimeActivity extends ActionBarActivity implements View.OnClickListener {
    private Timer timer;
    private Timer timer1;
    private Timer timer2;
    private Timer timer3;
    private Timer timer4;
    private Timer timer5;

    private int time = 0;
    private TextView tv_time;
    private String timeStr;

    private com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar bnp;
    private com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar bnp1;
    private com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar bnp2;
    private com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar bnp3;
    private com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar bnp4;
    private String str;
    private MaterialDialog mMaterialDialog;
    private boolean destroyKey = false;
    int mAppWidgetId;
    private Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eatingtime);

        Bundle mExtras = getIntent().getExtras();
        if (mExtras != null) {
             mAppWidgetId = mExtras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }
        mainContext = getApplicationContext();

       // value.startTime = System.currentTimeMillis();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        bnp = (com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar)findViewById(R.id.numberbar1);
        bnp1 = (com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar)findViewById(R.id.numberbar2);
        bnp2 = (com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar)findViewById(R.id.numberbar3);
        bnp3 = (com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar)findViewById(R.id.numberbar4);
        bnp4 = (com.eatinghabit.sehyunpark.eatinghabits.extra.NumberProgressBar)findViewById(R.id.numberbar5);


            bnp.setOnProgressBarListener(new OnProgressBarListener() {
                @Override
                public void onProgressChange(int current, int max) {
                    if(current == max) {
                        Toast.makeText(getApplicationContext(), getString(R.string.finish), Toast.LENGTH_SHORT).show();
                        bnp.setProgress(0);
                    }
                }
            });
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bnp.incrementProgressBy(1);
                        }
                    });
                }
            }, 1000, 15000);

            bnp1.setOnProgressBarListener(new OnProgressBarListener() {
                @Override
                public void onProgressChange(int current, int max) {
                    if (current == max) {
                        Toast.makeText(getApplicationContext(), getString(R.string.finish), Toast.LENGTH_SHORT).show();
                        bnp1.setProgress(0);
                    }
                }
            });
            timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bnp1.incrementProgressBy(1);
                        }
                    });
                }
            }, 20000, 20000);

            bnp2.setOnProgressBarListener(new OnProgressBarListener() {
                @Override
                public void onProgressChange(int current, int max) {
                    if (current == max) {
                        Toast.makeText(getApplicationContext(), getString(R.string.finish), Toast.LENGTH_SHORT).show();
                        bnp2.setProgress(0);
                    }
                }
            });
            timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bnp2.incrementProgressBy(1);
                        }
                    });
                }
            }, 25000, 25000);

            bnp3.setOnProgressBarListener(new OnProgressBarListener() {
                @Override
                public void onProgressChange(int current, int max) {
                    if (current == max) {
                        Toast.makeText(getApplicationContext(), getString(R.string.finish), Toast.LENGTH_SHORT).show();
                        bnp3.setProgress(0);
                    }
                }
            });
            timer3 = new Timer();
            timer3.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bnp3.incrementProgressBy(1);
                        }
                    });
                }
            }, 30000, 30000);

            bnp4.setOnProgressBarListener(new OnProgressBarListener() {
                @Override
                public void onProgressChange(int current, int max) {
                    if (current == max) {
                        Toast.makeText(getApplicationContext(), getString(R.string.finish), Toast.LENGTH_SHORT).show();
                        bnp4.setProgress(0);
                    }
                }
            });
            timer4 = new Timer();
            timer4.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bnp4.incrementProgressBy(1);
                        }
                    });
                }
            }, 30000, 30000);

            bnp.setOnClickListener(this);
            bnp1.setOnClickListener(this);
            bnp2.setOnClickListener(this);
            bnp3.setOnClickListener(this);
            bnp4.setOnClickListener(this);

        tv_time = (TextView) findViewById(R.id.tv_time);
        timer5 = new Timer();
        timer5.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        updateTimeSting();
                    }
                });
            }
        }, 1000, 1000);


            animateProgress(bnp, bnp1, bnp2, bnp3, bnp4);




    }
/*
    @Override
    public void onResume() {
        super.onResume();
        if(value.curruntTime < value.startTime + 10) {
            value.resumeTime = System.currentTimeMillis();
            resumeTimeCount(value.curTime(), value.prvTime());

            value.curruntTime = 0;
            value.resumeTime = 0;
        }
    }

    private void resumeTimeCount(long time,long time2){
        String s ="";
        String s2 ="";
        int t = (int)(time /1000);
        int t2 = (int)(time2 /1000);
        s = s.format("%d분 %d초",t/60,t%60);
        s2 = s.format("%d분 %d초",t2/60,t2%60);
        Toast.makeText(getApplicationContext(),"이전 : "+s2+"\n그후 : "+s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        value.curruntTime = System.currentTimeMillis();
        value.tempTime = value.prvTime();
    }

    static class value {
        static long startTime;
        static long curruntTime;
        static long resumeTime;
        static long tempTime;

        static long prvTime(){
            return (curruntTime - startTime);
        }
        static long curTime(){
            long temp = resumeTime - curruntTime;
            return temp;
        }
    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.numberbar1 :
                Toast.makeText(getApplicationContext(),"식사 시간입니다. 맛있는 식사하세요^^",Toast.LENGTH_SHORT).show();
                break;
            case R.id.numberbar2 :
                Toast.makeText(getApplicationContext(),"포만감 향상 -> 과식 예방",Toast.LENGTH_SHORT).show();
                break;
            case R.id.numberbar3 :
                Toast.makeText(getApplicationContext(),"뇌 활성화 -> 집중력, 판단력 상승",Toast.LENGTH_SHORT).show();
                break;
            case R.id.numberbar4 :
                Toast.makeText(getApplicationContext(),"소화촉진 -> 위장병 위험 감소",Toast.LENGTH_SHORT).show();
                break;
            case R.id.numberbar5 :
                Toast.makeText(getApplicationContext(),"기억력 향상 -> 치매 예방",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(getApplicationContext(), ListViewMultiChartActivity.class);
        if(!destroyKey) {
            mMaterialDialog = new MaterialDialog(new ContextThemeWrapper(EatingTimeActivity.this, R.style.MyAlertDialog))
                    .setTitle("")
                    .setMessage("식사를 마치시겠습니까?")
                    .setPositiveButton("예", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            timer.cancel();
                            timer1.cancel();
                            timer2.cancel();
                            timer3.cancel();
                            timer4.cancel();
                            timer5.cancel();
                            destroyKey = true;

                            str =    bnp.getProgress() +"#"
                                    + bnp1.getProgress() +"#"
                                    + bnp2.getProgress() +"#"
                                    + bnp3.getProgress() +"#"
                                    + bnp4.getProgress();

                            WriteTextFile("eatingtime.txt",str);

                            startActivity(intent);
                            Intent resultValue = new Intent();
                            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                            setResult(RESULT_OK, resultValue);
                            onBackPressed();

                        }
                    })
                    .setNegativeButton("아니요", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }
                    });
            mMaterialDialog.show();
        }
        else{
            super.onBackPressed();
        }

    }

    private void updateTimeSting(){
        timeStr = String.format("시간 : %02d 분 %02d 초", time / 60, time % 60);
        tv_time.setText(timeStr);
    }

    public void animateProgress(View progress1, View progress2, View progress3, View progress4, View progress5) {
        final PropertyAction prog1 = PropertyAction.newPropertyAction(progress1).interpolator(new DecelerateInterpolator()).translationX(-200).duration(550).alpha(0.4f).build();
        final PropertyAction prog2 = PropertyAction.newPropertyAction(progress2).interpolator(new DecelerateInterpolator()).translationX(-300).duration(350).alpha(0.4f).build();
        final PropertyAction prog3 = PropertyAction.newPropertyAction(progress3).interpolator(new DecelerateInterpolator()).translationX(-400).duration(350).alpha(0.4f).build();
        final PropertyAction prog4 = PropertyAction.newPropertyAction(progress4).interpolator(new DecelerateInterpolator()).translationX(-450).duration(350).alpha(0.4f).build();
        final PropertyAction prog5 = PropertyAction.newPropertyAction(progress5).interpolator(new DecelerateInterpolator()).translationX(-500).duration(350).alpha(0.4f).build();
        Player.init().
                animate(prog1).
                then().
                animate(prog2).
                then().
                animate(prog3).
                then().
                animate(prog4).
                then().
                animate(prog5).
                play();
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

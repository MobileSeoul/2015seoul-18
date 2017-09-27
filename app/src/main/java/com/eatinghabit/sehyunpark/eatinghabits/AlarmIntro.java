package com.eatinghabit.sehyunpark.eatinghabits;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;

public class AlarmIntro extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
       // addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.alarmintro4));
        addSlide(SampleSlide.newInstance(R.layout.alarmintro2));
        addSlide(SampleSlide.newInstance(R.layout.alarmintro));
        addSlide(SampleSlide.newInstance(R.layout.alarmintro3));
        addSlide(SampleSlide.newInstance(R.layout.alarmintro5));

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(123456);

        setDoneText("확인");
        setSkipText("홈");
        setTitleColor(getResources().getColor(R.color.colorPrimaryText));
    }

    private void loadMainActivity(){
        finish();
    }

    @Override
    public void onSkipPressed() {
        finish();
    }

    @Override
    public void onDonePressed() {
        finish();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}

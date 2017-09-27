package com.eatinghabit.sehyunpark.eatinghabits;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PedometerCloseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometerclose);

        SensorService.step = -1;

        finish();
    }
}


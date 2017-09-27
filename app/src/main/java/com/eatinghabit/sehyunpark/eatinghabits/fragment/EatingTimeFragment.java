package com.eatinghabit.sehyunpark.eatinghabits.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eatinghabit.sehyunpark.eatinghabits.EatingTimeActivity;
import com.eatinghabit.sehyunpark.eatinghabits.R;
import com.eatinghabit.sehyunpark.eatinghabits.interfaces.RecyclerViewOnClickListenerHack;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2015-07-24.
 */
public class EatingTimeFragment extends android.support.v4.app.Fragment implements RecyclerViewOnClickListenerHack, View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected static final String TAG = "LOG";

    private com.github.clans.fab.FloatingActionButton fab1;
    private final long FINSH_INTERVAL_TIME = 1200;
    private long PressedTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){

        }
        else{

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_eatingtime,container,false);

        fab1 = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.menu_item1);

        fab1.setOnClickListener(this);

        return view;
    }



    public void onClickListener(View view, int position) {

    }
    public void onLongPressClickListener(View view, int position) {

    }

    public  static  class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvoclh) {
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(),e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onLongPressClickListener(cv,
                                rv.getChildPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return(true);
                }
            });


        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

    @Override
    public void onClick(View v){
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - PressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            return;
        }
        else {
            PressedTime = tempTime;

            final Intent intent;
            Timer t = new Timer();
            YoYo.with(Techniques.Wave).duration(1000).playOn(fab1);
            intent = new Intent(getActivity(), EatingTimeActivity.class);
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 1000);
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}

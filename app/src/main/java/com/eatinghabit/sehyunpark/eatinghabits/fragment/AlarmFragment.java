package com.eatinghabit.sehyunpark.eatinghabits.fragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.eatinghabit.sehyunpark.eatinghabits.AlarmActivity;
import com.eatinghabit.sehyunpark.eatinghabits.AlarmRecever;
import com.eatinghabit.sehyunpark.eatinghabits.MainActivity;
import com.eatinghabit.sehyunpark.eatinghabits.R;
import com.eatinghabit.sehyunpark.eatinghabits.interfaces.RecyclerViewOnClickListenerHack;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Calendar;


/**
 * Created by Administrator on 2015-07-24.
 */
public class AlarmFragment extends android.support.v4.app.Fragment implements RecyclerViewOnClickListenerHack, View.OnClickListener
{
    private Calendar calendar = Calendar.getInstance();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected static final String TAG = "LOG";
    protected ProgressBar mPbLoad;
    protected boolean isLastItem;

    private TextView textView;
    private com.github.clans.fab.FloatingActionButton fab1;
    private com.github.clans.fab.FloatingActionButton fab2;
    private FloatingActionMenu menu;

    private final static int ALARM_ACTIVITY = 1000;
    private PendingIntent pendingIntent;

    private FrameLayout mFrameLayout;
    private Toolbar mToolbar;
    private CoordinatorLayout mCoordinatorLayout;
    private boolean animationKey = true;

    private View viewMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            animationKey = savedInstanceState.getBoolean("key");
        }
        if(getArguments() != null){

        }
        else{

        }

        Intent alarmIntent = new Intent(getActivity(), AlarmRecever.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);
        mToolbar = ((MainActivity) getActivity()).getmToolbar();
        mCoordinatorLayout = ((MainActivity) getActivity()).getmCoordinatorLayout();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_alarm,container,false);
        String strBuf = "시간을 설정해 주세요!";


        if(((MainActivity)getActivity()).ReadTextFile("text.txt") != null) {
            strBuf = ((MainActivity)getActivity()).ReadTextFile("text.txt");
        }

        viewMain = (FrameLayout) view.findViewById(R.id.fl_alarm);
        viewMain.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(menu.isOpened())
                    menu.close(true);
                return false;
            }
        });

        textView = (TextView) view.findViewById(R.id.alarm_text);
        textView.setText(strBuf);


        fab1 = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.menu_item1);
        fab2 = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.menu_item2);

        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        menu = (FloatingActionMenu) view.findViewById(R.id.fabMapMenu);
        menu.setClosedOnTouchOutside(true);

        mFrameLayout = (FrameLayout) view.findViewById(R.id.fl_alarm);

        if(animationKey) {
            ((MainActivity) getActivity()).animateSampleOne(mToolbar, mFrameLayout, menu, mCoordinatorLayout);
            animationKey = false;
        }
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
        Intent intent;
        switch (v.getId()) {
            case R.id.menu_item1:
                intent = new Intent(getActivity(), AlarmActivity.class);
                getActivity().startActivityForResult(intent, ALARM_ACTIVITY);
                break;

            case R.id.menu_item2:
                cancel();
                textView.setText("시간을 설정해 주세요!");
                String strBuf = "시간을 설정해 주세요!";
                ((MainActivity)getActivity()).WriteTextFile("text.txt", strBuf);
                Toast.makeText(getActivity(), "취소되었습니다.", Toast.LENGTH_SHORT);
                break;

            default:
                break;
        }
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    @Override
    public void onResume() {
                super.onResume();
                menu.close(true);
            }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
                outState.putBoolean("key", animationKey);

    }

}


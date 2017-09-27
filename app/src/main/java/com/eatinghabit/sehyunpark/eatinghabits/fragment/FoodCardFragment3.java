package com.eatinghabit.sehyunpark.eatinghabits.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.eatinghabit.sehyunpark.eatinghabits.FoodInnerActivity;
import com.eatinghabit.sehyunpark.eatinghabits.MainActivity;
import com.eatinghabit.sehyunpark.eatinghabits.R;
import com.eatinghabit.sehyunpark.eatinghabits.adapters.FoodAdapter;
import com.eatinghabit.sehyunpark.eatinghabits.domain.Food;
import com.eatinghabit.sehyunpark.eatinghabits.interfaces.RecyclerViewOnClickListenerHack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-07-24.
 */
public class FoodCardFragment3 extends FoodCardFragment implements RecyclerViewOnClickListenerHack{

    private RecyclerView mRecyclerView;
    private List<Food> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mList = savedInstanceState.getParcelableArrayList("mList");
        }
        else{
            mList = ( (MainActivity) getActivity() ).getSetChatList(3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_food,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                } else {

                }

            }
        });


        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        LinearLayoutManager lim = new LinearLayoutManager(getActivity());
        lim.setOrientation(LinearLayoutManager.VERTICAL);
       
        mRecyclerView.setLayoutManager(lim);

        FoodAdapter adaptert = new  FoodAdapter(getActivity(),mList);

        mRecyclerView.setAdapter(adaptert);

        createCustomAnimation(view);

        return view;
    }

    @Override
    public void onClickListener(View view, int position) {

        Intent intent = new Intent(getActivity(), FoodInnerActivity.class);
        intent.putExtra("food", mList.get( position ));

        // TRTANSITIONS
            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){

                View tvName = view.findViewById( R.id.tv_name );
                View tvDay = view.findViewById( R.id.tv_day );

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        Pair.create(tvName, "element2"),
                        Pair.create(tvDay, "element3"));
                getActivity().startActivity(intent, options.toBundle() );
            }
        else{
                getActivity().startActivity(intent);
            }

    }
    public void onLongPressClickListener(View view, int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, mList.get(position).getName());
        startActivity(intent);
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
                    View cv = rv.findChildViewUnder(e.getX(),e.getY());

                    boolean callContextMenuStatus = false;
                    if( cv instanceof CardView){
                        float x = ((RelativeLayout)((CardView) cv).getChildAt(0)).getChildAt(1).getX();
                        float w = ((RelativeLayout)((CardView) cv).getChildAt(0)).getChildAt(1).getWidth();
                        float y;
                        float h = ((RelativeLayout)((CardView) cv).getChildAt(0)).getChildAt(1).getHeight();

                        Rect rect = new Rect();
                        ((RelativeLayout)((CardView) cv).getChildAt(0)).getChildAt(1).getGlobalVisibleRect( rect );
                        y = rect.top;

                        if( e.getX() >= x && e.getX() <= w + x && e.getRawY() >= y && e.getRawY() <= h + y ){
                            callContextMenuStatus = true;
                        }
                    }

                    if(cv != null && mRecyclerViewOnClickListenerHack != null && !callContextMenuStatus){
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildPosition(cv));
                    }
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mList", (ArrayList<Food>) mList);
    }

}

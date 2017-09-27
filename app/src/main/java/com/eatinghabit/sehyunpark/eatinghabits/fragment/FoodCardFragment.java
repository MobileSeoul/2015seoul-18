package com.eatinghabit.sehyunpark.eatinghabits.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.eatinghabit.sehyunpark.eatinghabits.FoodInnerActivity;
import com.eatinghabit.sehyunpark.eatinghabits.MainActivity;
import com.eatinghabit.sehyunpark.eatinghabits.R;
import com.eatinghabit.sehyunpark.eatinghabits.adapters.FoodAdapter;
import com.eatinghabit.sehyunpark.eatinghabits.domain.Food;
import com.eatinghabit.sehyunpark.eatinghabits.interfaces.RecyclerViewOnClickListenerHack;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-07-24.
 */
public class FoodCardFragment extends android.support.v4.app.Fragment implements RecyclerViewOnClickListenerHack{
    private RecyclerView mRecyclerView;
    private List<Food> mList;

    private FloatingActionMenu menu;

    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mList = savedInstanceState.getParcelableArrayList("mList");
        }
        else{
            mList = ( (MainActivity) getActivity() ).getSetChatList(1);
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
        createCustomAnimation(view);

        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        LinearLayoutManager lim = new LinearLayoutManager(getActivity());
        lim.setOrientation(LinearLayoutManager.VERTICAL);
       
        mRecyclerView.setLayoutManager(lim);

        FoodAdapter adaptert = new  FoodAdapter(getActivity(),mList);

        mRecyclerView.setAdapter(adaptert);

        return view;
    }

    protected void createCustomAnimation(View view) {
        menu = (FloatingActionMenu) view.findViewById(R.id.fab_food);

        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu.getMenuIconView().setImageResource(menu.isOpened()
                        ? R.drawable.cook : R.drawable.cook_red);
            }
        });
        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menu.setIconToggleAnimatorSet(set);

        fab1 = (FloatingActionButton) view.findViewById(R.id.menu_item1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.menu_item2);
        fab3 = (FloatingActionButton) view.findViewById(R.id.menu_item3);

        fab1.setOnClickListener( ((MainActivity)getActivity()).clicked );
        fab2.setOnClickListener(((MainActivity)getActivity()).clicked);
        fab3.setOnClickListener(((MainActivity)getActivity()).clicked);

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mList", (ArrayList<Food>) mList);
    }

}

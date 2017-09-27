package com.eatinghabit.sehyunpark.eatinghabits.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.eatinghabit.sehyunpark.eatinghabits.R;
import com.eatinghabit.sehyunpark.eatinghabits.fragment.AlarmFragment;
import com.eatinghabit.sehyunpark.eatinghabits.fragment.EatingTimeFragment;
import com.eatinghabit.sehyunpark.eatinghabits.fragment.FoodCardFragment;
import com.eatinghabit.sehyunpark.eatinghabits.fragment.FoodCardFragment2;
import com.eatinghabit.sehyunpark.eatinghabits.fragment.FoodCardFragment3;


/**
 * Created by Administrator on 2015-08-02.
 */
public class TabsAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] titles = {"알람","밥시간","초보자","숙련자","고수"};
    //private int icons = R.drawable.alarm;
    private int[] icons = {R.drawable.alarm,R.drawable.eating,R.drawable.level1
                              ,R.drawable.level2,R.drawable.level3};
    private int heightIcon;


    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);

        mContext = c;
        double scale = c.getResources().getDisplayMetrics().density;
        heightIcon = ( int ) ( 24 * scale + 0.5f );

    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        if (position == 0) {
            frag = new AlarmFragment();
        } else if (position == 1) {
            frag = new EatingTimeFragment();
        }  else if (position == 2) {
            frag = new FoodCardFragment();
        }else if (position == 3) {
            frag = new FoodCardFragment2();
        }else if (position == 4) {
            frag = new FoodCardFragment3();
        }


        Bundle b = new Bundle();
        b.putInt("position", position);

        frag.setArguments(b);

        return frag;
    }

    @Override
    public int getCount() { return titles.length; }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable d = mContext.getResources().getDrawable(icons[position]);
        d.setBounds( 0, 0, heightIcon, heightIcon);

        ImageSpan is = new ImageSpan( d );

        SpannableString sp = new SpannableString( " ");
        sp.setSpan( is, 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        return ( sp );
         //return ( titles[position] );
    }
}

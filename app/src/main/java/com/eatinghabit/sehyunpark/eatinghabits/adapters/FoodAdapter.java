package com.eatinghabit.sehyunpark.eatinghabits.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eatinghabit.sehyunpark.eatinghabits.R;
import com.eatinghabit.sehyunpark.eatinghabits.domain.Food;
import com.eatinghabit.sehyunpark.eatinghabits.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;

/**
 * Created by Administrator on 2015-07-25.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    private Context mContext;
    private List<Food> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private float scale;
    private int width;
    private int height;

    protected boolean withAnimation;

    public FoodAdapter(Context c, List<Food> l){ this( c, l, true); }

    public FoodAdapter(Context c, List<Food> l, boolean wa){
        mContext = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        withAnimation = wa;
        scale = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
        height = (width / 16) * 9;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;

            v = mLayoutInflater.inflate(R.layout.item_food, viewGroup, false);

        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        myViewHolder.tvName.setText(mList.get(position).getName());
        myViewHolder.tvDay.setText(mList.get(position).getKal());

    }

    @Override
    public int getItemCount() { return mList.size(); }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public TextView tvDay;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);

        }
    }

}

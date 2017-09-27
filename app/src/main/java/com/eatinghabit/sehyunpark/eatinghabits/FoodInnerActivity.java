package com.eatinghabit.sehyunpark.eatinghabits;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eatinghabit.sehyunpark.eatinghabits.domain.Food;
import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import at.markushi.ui.CircleButton;
import me.drakeet.materialdialog.MaterialDialog;


public class FoodInnerActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private Food food;
    private Drawer navigationDrawerLeft;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private MaterialDialog mMaterialDialog;
    private TextView tvDescription;
    private TextView tvDescription2;
    private TextView tvDescription3;
    private ViewGroup mRoot;

    private float scale;
    private int width;

    private CircleButton cb1;
    private CircleButton cb2;
    private CircleButton cb3;

    private String url1;
    private String url2;
    private String url3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TRTANSITIONS
            if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){


                TransitionInflater inflater = TransitionInflater.from( this );
                Transition transition = inflater.inflateTransition( R.transition.transitions );

                getWindow().setSharedElementEnterTransition(transition);

                Transition transition1 =  getWindow().getSharedElementEnterTransition();
                transition1.addListener(new Transition.TransitionListener(){
                    @Override
                    public void onTransitionStart(Transition transition) {

                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        TransitionManager.beginDelayedTransition(mRoot, new Slide());

                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {

                    }

                    @Override
                    public void onTransitionPause(Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(Transition transition) {

                    }
                });
            }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_innerfood);



        if (savedInstanceState != null) {
            food = savedInstanceState.getParcelable("food");
        } else {
            if(getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getParcelable("food") != null){
                food = getIntent().getExtras().getParcelable("food");
            }else{
                Toast.makeText(this , "Filed", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        url1 = "http://cook.miznet.daum.net/search/search.do?q=" + food.getName();
        url2 = "http://terms.naver.com/search.nhn?query=" + food.getName();
        url3 = "http://www.yorirecipe.com/bbs/search.php?sfl=wr_subject%7C%7Cwr_content&sop=and&stx=" + food.getName();



        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById( R.id.collapsing_toolbar );
        mCollapsingToolbarLayout.setTitle(food.getName());

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Main Activity");

        mRoot = ( ViewGroup ) findViewById(R.id.ll_tv_description);
        tvDescription = (TextView) findViewById(R.id.tv_description1);
        tvDescription2 = (TextView) findViewById(R.id.tv_description2);
        tvDescription3 = (TextView) findViewById(R.id.tv_description3);

        animateSampleText(tvDescription,tvDescription2,tvDescription3);

        TextView tvName = (TextView) findViewById(R.id.tv_name);
        TextView tvDay = (TextView) findViewById(R.id.tv_day);
        Button btPhone = (Button) findViewById(R.id.bt_phone);

        btPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog = new MaterialDialog(new ContextThemeWrapper(FoodInnerActivity.this, R.style.MyAlertDialog))
                        .setTitle("검색하기")
                        .setMessage("검색하시겠습니까?")
                        .setPositiveButton("예", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_WEB_SEARCH);
                                intent.putExtra(SearchManager.QUERY, food.getName());
                                startActivity(intent);
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
        });

        scale = this.getResources().getDisplayMetrics().density;
        width = this.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);


        tvName.setText(food.getName());
        tvDay.setText(food.getSubname());
        tvDescription.setText("\nKal : " + food.getKal() + "\n" + "분류 : " + food.getCategory() + "\n        " + food.getCategory2() + "\n\n");




        tvDescription3.setText("\n" + "맛있는 밥 많이 드시고 만수무강 하세요.\n\n");
        navigationDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                .build();





            // FAB
            FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent msg = new Intent(Intent.ACTION_SEND);

                    msg.addCategory(Intent.CATEGORY_DEFAULT);

                    msg.putExtra(Intent.EXTRA_SUBJECT, "레시피\n"+food.getName()+"\n\n다음\n - " +url1 +
                    "\n\n네이버\n - " + url2 +
                    "\n\n요리레시피\n - " + url3);

                    msg.putExtra(Intent.EXTRA_TEXT, "\n\n\n                         맛있게 드세요 - 밥힘");

                   msg.putExtra(Intent.EXTRA_TITLE, "제목");

                    msg.setType("text/plain");

                    startActivity(Intent.createChooser(msg, "공유"));
                }
            });
        cb1 = (CircleButton) findViewById(R.id.cb1);
        cb2 = (CircleButton) findViewById(R.id.cb2);
        cb3 = (CircleButton) findViewById(R.id.cb3);
        cb1.setOnClickListener(clicked);
        cb2.setOnClickListener(clicked);
        cb3.setOnClickListener(clicked);

        animateSampleCircle(cb1,cb2,cb3);


        }
    public View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            String url = "";
            switch (v.getId()) {
                case R.id.cb1:
                    intent.setAction(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, url1);
                    startActivity(intent);
                    break;
                case R.id.cb2:
                    intent.setAction(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, url2);
                    startActivity(intent);
                    break;
                case R.id.cb3:
                    intent.setAction(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, url3);
                    startActivity(intent);
                    break;
            }
        }
    };




        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putParcelable("food", food);
        }

        @Override
        public void onBackPressed() {


                super.onBackPressed();

        }

    public void animateSampleText(View text1, View text2, View text3) {
        final PropertyAction prog1 = PropertyAction.newPropertyAction(text1).interpolator(new DecelerateInterpolator()).translationX(-200).duration(550).alpha(0.4f).build();
        final PropertyAction prog2 = PropertyAction.newPropertyAction(text2).interpolator(new DecelerateInterpolator()).translationX(-300).duration(350).alpha(0.4f).build();
        final PropertyAction prog3 = PropertyAction.newPropertyAction(text3).interpolator(new DecelerateInterpolator()).translationX(-400).duration(350).alpha(0.4f).build();
        Player.init().
                animate(prog1).
                then().
                animate(prog2).
                then().
                animate(prog3).
                play();
    }

    public void animateSampleCircle(View cb1, View cb2, View cb3) {
        final PropertyAction prog1 = PropertyAction.newPropertyAction(cb1).scaleX(0).scaleY(0).duration(550).interpolator(new AccelerateDecelerateInterpolator()).build();
        final PropertyAction prog2 = PropertyAction.newPropertyAction(cb2).scaleX(0).scaleY(0).duration(550).interpolator(new AccelerateDecelerateInterpolator()).build();
        final PropertyAction prog3 = PropertyAction.newPropertyAction(cb3).scaleX(0).scaleY(0).duration(550).interpolator(new AccelerateDecelerateInterpolator()).build();

        Player.init().
                animate(prog1).
                then().
                animate(prog2).
                then().
                animate(prog3).
                play();
    }












}


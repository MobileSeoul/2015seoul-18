package com.eatinghabit.sehyunpark.eatinghabits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eatinghabit.sehyunpark.eatinghabits.adapters.TabsAdapter;
import com.eatinghabit.sehyunpark.eatinghabits.domain.Food;
import com.eatinghabit.sehyunpark.eatinghabits.domain.Person;
import com.eatinghabit.sehyunpark.eatinghabits.domain.RealmFood;
import com.eatinghabit.sehyunpark.eatinghabits.extra.SlidingTabLayout;
import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;
import com.eftimoff.androidplayer.listeners.PlayerEndListener;
import com.eftimoff.androidplayer.listeners.PlayerStartListener;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
{


    private String timeSearch;
    private Toolbar mToolbar;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //private EditText mEditFile;
    private FloatingActionMenu menuFood;
    private CoordinatorLayout mCoordinatorLayout;

    public Context mContext;


    private Realm realm;
    private List<Food> listFood;
    private List<RealmFood> foods;

    //Drawer
    private Drawer navigationDrawerLeft;
    //private Drawer navigationDrawerRight;
    private AccountHeader headerNavigationLeft;
    private int mItemDrawerSelected;
    private int mProfileDrawerSelected;
    private List<Person> listProfile;
    private List<PrimaryDrawerItem> listCatefories;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //REALM DB
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.deleteRealm(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);
        try {
            loadViliges();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (savedInstanceState != null) {
            listFood = savedInstanceState.getParcelableArrayList("listFood");
            mItemDrawerSelected = savedInstanceState.getInt("mItemDrawerSelected", 0);
            mProfileDrawerSelected = savedInstanceState.getInt("mProfileDrawerSelected", 0);

        }

        //mEditFile = (EditText)this.findViewById(R.id.editFile);

        File file = getFileStreamPath("text.txt");
        if( file.isFile() == false ) {
            String strBuf = ReadTextAssets("text.txt");
            WriteTextFile("text.txt", strBuf);
        }
        else{

        }
        mContext = this;
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_main);
        // TOOL BAR
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("  한국인의 밥힘");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.mdtp_white));
        mToolbar.setLogo(R.drawable.eatinghabit_logo);

        //  TABS
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        //mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);




//Drawer
        headerNavigationLeft = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        Person aux = getPersonByEmail(listProfile, (ProfileDrawerItem) iProfile);
                        headerNavigationLeft.setBackgroundRes(aux.getBackground());

                        if (iProfile.getName().startsWith("만보기")) {
                            if(SensorService.isStart) {
                                pStopDialog();
                            }else {
                                pStartDialog();
                            }
                        }
                        if (iProfile.getName().startsWith("걸음수")) {
                            if(SensorService.isStart) {
                                Toast.makeText(getApplicationContext(), "걸음수 : " + SensorService.step , Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "만보기를 실행하세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (iProfile.getName().startsWith("칼로리")) {
                            if(SensorService.isStart) {
                                Toast.makeText(getApplicationContext(), "칼로리 : " +  SensorService.step * 0.03 +"Kcal", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "만보기를 실행하세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (iProfile.getName().startsWith("동작")) {
                            if(SensorService.isStart) {
                                Toast.makeText(getApplicationContext(), "만보기가 동작 중 입니다", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "만보기를 실행하세요", Toast.LENGTH_SHORT).show();
                            }
                        }

                        return false;
                    }
                })
                .build();

        listProfile = getSetProfileList();
        if(listProfile != null && listProfile.size() > 0){
            for( int i = 0; i < listProfile.size(); i++ ){
                headerNavigationLeft.addProfile(listProfile.get(i).getProfile(), i);
            }
            headerNavigationLeft.setBackgroundRes( listProfile.get(0).getBackground());
        }

        //NAVIGATION DRAWER Start - Left
        navigationDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.START)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        if(i != 6) {
                            mViewPager.setCurrentItem(i);
                        }
                        else{
                            GuideDialog();
                        }
                        return false;
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick : " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        listCatefories = getSetCategoryList();
        if(listCatefories != null && listCatefories.size() > 0){
            for( int i = 0; i < listCatefories.size(); i++ ){
                navigationDrawerLeft.addItem( listCatefories.get(i) );
            }
            SectionDrawerItem sd = new SectionDrawerItem();
            sd.withName("사용설명서");
            sd.setDivider(true);
            PrimaryDrawerItem p = new PrimaryDrawerItem();
            p.setBadgeBackgroundResource(R.color.primary);
            p.setName("사용설명서 보기");
            p.setTextColor(R.color.primary_dark);

            navigationDrawerLeft.addItem(sd);
            navigationDrawerLeft.addItem(p);
            navigationDrawerLeft.setSelection(mItemDrawerSelected);
        }

    }

    private List<PrimaryDrawerItem> getSetCategoryList(){
        String[] names = new String[]{"알람", "식사시간", "레시피-하","레시피-중","레시피-상" };
        int [] iconsSelected = {
                R.drawable.alarm,
                R.drawable.eating,
                R.drawable.level1,
                R.drawable.level2,
                R.drawable.level3 };
        int [] icons = {
                R.drawable.alarm_red,
                R.drawable.eating_red,
                R.drawable.level1_red,
                R.drawable.level2_red,
                R.drawable.level3_red };

        List<PrimaryDrawerItem> list = new ArrayList<>();

        for(int i = 0; i < names.length; i++){
            PrimaryDrawerItem aux = new PrimaryDrawerItem();
            aux.setName(names[i]);

            aux.setIcon(getResources().getDrawable(icons[i]));
            aux.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            aux.setSelectedIcon(getResources().getDrawable(iconsSelected[i]));
            aux.setSelectedTextColor(getResources().getColor(R.color.primary));

            list.add(aux);
        }

        return(list);
    }

    private Person getPersonByEmail( List<Person> list , ProfileDrawerItem p){
        Person aux = null;
        for(int i = 0; i< list.size(); i++){
            aux = list.get(i);
            break;
        }
        return aux;
    }

    public List<Person> getSetProfileList(){
        String[] name = {"만보기", "걸음수", "칼로리", "동작"};
        String[] email = {"만보기", "몇걸음 걸었을까요?", "나의 운동량은?", "만보기 확인하기!"};
        int[] photo = {R.drawable.footsteps,R.drawable.count, R.drawable.vegetarian, R.drawable.check};
        int background = R.color.primary_dark;
        List<Person> list = new ArrayList<>();

        for(int i=0; i< name.length; i++){
            ProfileDrawerItem aux = new ProfileDrawerItem();
            aux.setName(name[i]);
            aux.setEmail(email[i]);
            aux.setIcon(getResources().getDrawable(photo[i]));
            aux.setTextColor(getResources().getColor(R.color.primary_text));
            aux.setSelectedColor(getResources().getColor(R.color.colorAccent2));
            Person p = new Person();
            p.setProfile(aux);
            p.setBackground(background);

            list.add(p);
        }
        return(list);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mItemDrawerSelected", mItemDrawerSelected);
        outState.putInt("mProfileDrawerSelected", mProfileDrawerSelected);
        outState.putParcelableArrayList("listFood", (ArrayList<Food>) listFood);
        outState = navigationDrawerLeft.saveInstanceState(outState);
        outState = headerNavigationLeft.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extraBundle;

        if(resultCode == RESULT_OK &&  requestCode == 1000) {
            extraBundle = data.getExtras();

            int hour = extraBundle.getInt("hour");
            int minute = extraBundle.getInt("minute");

                TextView textView = (TextView) findViewById(R.id.alarm_text);
                String str = "매일 아침은 " + hour + " : " + String.format("%02d", minute) + " !!";
                textView.setText(str);
                WriteTextFile("text.txt", str);

        }
    }

    @Override
    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
            if(navigationDrawerLeft.isDrawerOpen()) {
                navigationDrawerLeft.closeDrawer();
            }
        else {
                if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
                    super.onBackPressed();
                } else {
                    backPressedTime = tempTime;
                    Toast.makeText(getApplicationContext(), "한번더누르시면종료됩니다.", Toast.LENGTH_SHORT).show();
                }
            }

    }

            public String ReadTextAssets(String strFileName) {

                String text = null;
                try {
                    InputStream is = getAssets().open(strFileName);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    text = new String(buffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return text;
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

            public String ReadTextFile(String strFileName) {
                String text = null;
                try {
                    File file = getFileStreamPath(strFileName);
                    FileInputStream fis = new FileInputStream(file);
                    Reader in = new InputStreamReader(fis);
                    int size = fis.available();
                    char[] buffer = new char[size];
                    in.read(buffer);
                    in.close();

                    text = new String(buffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return text;
            }

    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public CoordinatorLayout getmCoordinatorLayout() {
        return mCoordinatorLayout;
    }

    public void animateSampleOne(View toolbar, View activityMainMobileNumberLayout, View activityMainPinkFab, View activityMainheaderLayout) {
        final PropertyAction fabAction = PropertyAction.newPropertyAction(activityMainPinkFab).scaleX(0).scaleY(0).duration(750).interpolator(new AccelerateDecelerateInterpolator()).build();
        final PropertyAction headerAction = PropertyAction.newPropertyAction(activityMainheaderLayout).interpolator(new DecelerateInterpolator()).translationY(-200).duration(550).alpha(0.4f).build();
        final PropertyAction bottomAction = PropertyAction.newPropertyAction(activityMainMobileNumberLayout).translationY(500).duration(750).alpha(0f).build();
        Player.init().
                animate(headerAction).
                then().
                animate(fabAction).
                then().
                animate(bottomAction).
                play();

    }

    public PlayerStartListener playerStartListener = new PlayerStartListener() {
        @Override
        public void onStart() {
//            Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_SHORT).show();
        }
    };
    public PlayerEndListener playerEndListener = new PlayerEndListener() {
        @Override
        public void onEnd() {
//            Toast.makeText(getApplicationContext(), "End", Toast.LENGTH_SHORT).show();
        }
    };

    //Realm

    public List<Food> getSetChatList(int category){

        List<Food> listAux = new ArrayList<>();
        int size;
        size = loadCategory(category);

        for(int i = 0; i<size; i++){

            Food c = new Food();
            c.setLevel(foods.get(i).getLevel());
            c.setName(foods.get(i).getName());
            c.setSubname(foods.get(i).getSubname());
            c.setCategory(foods.get(i).getCategory());
            c.setCategory2(foods.get(i).getCategory2());
            c.setTime(foods.get(i).getTime());
            c.setKal(foods.get(i).getKal());
            c.setImage(foods.get(i).getImage());
            c.setDescription(foods.get(i).getDescription());

            listAux.add(c);
        }

        return(listAux);
    }

    public int loadCategory(int category){

        int i;
        RealmResults<RealmFood> result2 = realm.where(RealmFood.class)
                .equalTo("level", category)
                .findAll();
        foods = result2;
        i = foods.size();
        return i;
    }


    public void loadViliges() throws IOException {
        loadJsonFromStream();
    }

    private void loadJsonFromStream() throws IOException {

        InputStream stream = getAssets().open("food.json");
        realm.beginTransaction();
        try {

            realm.createAllFromJson(RealmFood.class, stream);
            realm.commitTransaction();
        } catch (IOException e) {
            realm.cancelTransaction();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

    }

    public View.OnClickListener clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            menuFood = (FloatingActionMenu) findViewById(R.id.fab_food);
            switch (v.getId()) {
                case R.id.menu_item1:
                    mViewPager.setCurrentItem(4);
                    break;
                case R.id.menu_item2:
                    mViewPager.setCurrentItem(3);
                    break;
                case R.id.menu_item3:
                    mViewPager.setCurrentItem(2);
                    break;
            }

            menuFood.close(true);

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();

        try{
            String str = SensorService.step+"";
            str.trim();
            File file = getFileStreamPath("stepInfo.txt");
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(str);
            out.close();

        } catch (Exception e) { e.printStackTrace(); }

    }

    // Dialog
    private void GuideDialog(){
        new MaterialDialog.Builder(this)
                .title("사용설명서")
                .content("아침밥 알람\n" +
                        "시간을 설정 할시 아침식사를 알리는 진동메세지로 여러분의 힘찬하루를 응원합니다." +
                        "\n\n" +
                        "식사시간\n" +
                        "식사시간을 체크하여 여러분의 건강을 분석합니다. 여유로운 식사하세요. 건강에 매우 좋습니다." +
                        "\n\n" +
                        "집밥 레시피\n" +
                        "오늘 당신의 하루는 어땟나요? \n" +
                        "오늘 하루 고생하셨습니다. 집밥 레시피로 건강한 저녁식사 맛있게 드세요." +
                        "\n\n" +
                        "만보기\n" +
                        "1주일에 5차례 하루 30분씩 걷기가 건강의 필수요건이라고 합니다. 하루의 건강을 체크하세요.\n")
                .show();
    }

    private void pStartDialog(){
        new MaterialDialog.Builder(this)
                .title("만보기")
                .content("만보기 서비스를 실행하시겠습니까?")
                .positiveText("네")
                .negativeText("아니요")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        intent = new Intent(getApplicationContext(), PedometerActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
    private void pStopDialog(){
        final Intent intent = new Intent(getApplicationContext(), SensorService.class);

        new MaterialDialog.Builder(this)
                .title("만보기")
                .content("만보기 서비스를 종료하시겠습니까?")
                .positiveText("네")
                .negativeText("아니요")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        try{
                            String str = 0+"";
                            str.trim();
                            File file = getFileStreamPath("stepInfo.txt");
                            FileOutputStream fos = new FileOutputStream(file);
                            Writer out = new OutputStreamWriter(fos, "UTF-8");
                            out.write(str);
                            out.close();

                        } catch (Exception e) { e.printStackTrace(); }
                        stopService(intent);
                    }
                })
                .show();
    }

}





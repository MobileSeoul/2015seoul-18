package com.eatinghabit.sehyunpark.eatinghabits.extra;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eatinghabit.sehyunpark.eatinghabits.R;
import com.eatinghabit.sehyunpark.eatinghabits.extra.listitem.BarChartItem;
import com.eatinghabit.sehyunpark.eatinghabits.extra.listitem.ChartItem;
import com.eatinghabit.sehyunpark.eatinghabits.extra.listitem.LineChartItem;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehyunpark on 2015-10-28.
 */
public class ListViewMultiChartActivity extends Base implements View.OnClickListener{

    private ArrayList<Integer> prg = new ArrayList<>();
    private ArrayList<Integer> realTime = new ArrayList<>();
    private String str = null;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_listview_chart);

        b1 = (Button) findViewById(R.id.chart_button1); b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.chart_button2); b2.setOnClickListener(this);
        b3 = (Button) findViewById(R.id.chart_button3); b3.setOnClickListener(this);
        b4 = (Button) findViewById(R.id.chart_button4); b4.setOnClickListener(this);
        b5 = (Button) findViewById(R.id.chart_button5); b5.setOnClickListener(this);
        b6 = (Button) findViewById(R.id.chart_button6); b6.setOnClickListener(this);

        String str = null;
        String[] str2 = new String[5];
        int k = 0;
        try {
            str = ReadTextFile("eatingtime.txt");
            str2 = str.split("#");
            for(int i = 0; i <str2.length; i++){
                int j = Integer.parseInt(str2[i]);
                if(j != 0){
                    prg.add(j);
                }
                else{
                    prg.add(1);
                }
            }
            realTime.add(prg.get(0) * 15);
            realTime.add(prg.get(1) * 20);
            realTime.add(prg.get(2) * 25);
            realTime.add(prg.get(3) * 25);
            realTime.add(prg.get(4) * 30);

            showHealthState(realTime.get(0));

            prg.add(realTime.get(0) / 35);
            prg.add(realTime.get(0) / 30);

        }catch (Exception e){


        }
        ListView lv = (ListView) findViewById(R.id.listView1);
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();

        list.add(new LineChartItem(generateDataLine(1), getApplicationContext()));
        list.add(new BarChartItem(generateDataBar(1), getApplicationContext()));
        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);

    }

    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 2; // we have 3 different item-types
        }
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 7; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "요일 별 건강상태체크");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 0; i < 7; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }

        LineDataSet d2 = new LineDataSet(e2, "구현 중입니다. ");
        d2.setLineWidth(2.5f);
        d2.setCircleSize(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);
        sets.add(d2);

        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 6; i++) {
            //entries.add(new BarEntry((int) (1 * 70) + 30, i));
            entries.add(new BarEntry((int) prg.get(i+1), i));
        }

        BarDataSet d = new BarDataSet(entries, "건강상태");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getHealth(), d);

        return cd;
    }


    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("월");
        m.add("화");
        m.add("수");
        m.add("목");
        m.add("금");
        m.add("토");
        m.add("일");

        return m;
    }

    private ArrayList<String> getHealth() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("포만감");
        m.add("면역력");
        m.add("소화촉진");
        m.add("기억력");
        m.add("인슐린");
        m.add("BMI지수");

        return m;
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


    private void showHealthState(int time){
        String str = null;
        if(time < 300)
        {
            str = "당신의 식사시간은 한국인의 8% 중 한 분 입니다. 조금 더 여유로운 식사는 어떨까요?";
        }
        else if(time < 600)
        {
            str = "당신의 식사시간은 한국인의 44% 중 한 분 입니다. 여유로운 식사는 하루를 여유롭게 만듭니다.";
        }
        else if(time < 900)
        {
            str = "당신의 식사시간은 한국인의 36.2% 중 한 분 입니다. 만수무강하십시오.";
        }
        else{
            str = "당신의 식사시간은 한국인의 10% 중 한 분 입니다. 여유로운 식사를 하시군요! 언제나 행복하시길 바랍니다.";
        }
            new AlertDialogWrapper.Builder(this)
                    .setTitle("식사시간")
                    .setMessage(str)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chart_button1 :
                stietyDialog();
                    break;
            case R.id.chart_button2 :
                ImmunityDialog();
                break;
            case R.id.chart_button3 :
                digestionDialog();
                break;
            case R.id.chart_button4 :
                memoryDialog();
                break;
            case R.id.chart_button5 :
                insulinDialog();
                break;
            case R.id.chart_button6 :
                BMIDialog();
                break;
        }
    }

    private void stietyDialog(){
        new MaterialDialog.Builder(this)
                .title("포만감")
                .content("음식을 많이 씹으면 씹을수록, 신경히스타민이라는 뇌내 다이어트 물질이 많이 분비된다. 이 물질을 만복중추를 자극하고 식욕을 억제하는 역할을 합니다. 더욱이 내장이 낀 쓸데 없는 지방을 연소하는 작용도 한다니 다이어트가 필요한 사람이라면 입에 한 숟갈 떠 넣은 밥을 정성 들여 꼭꼭 오래 씹어 보세요. 그 이외에도 음식을 오래 씹어 먹으면 수액(침)과 소화를 돕는 각종 효소의 분비가 왕성해져 만복감을 쉽게 느낄 수 있습니다."
                )
                .show();
    }

    private void ImmunityDialog(){

        new MaterialDialog.Builder(this)
                .title("면역력")
                .content("꼭꼭 씹어 먹어야 면역력도 높아지고, 키도 쑥쑥 자란다는 사실은 의학적으로 맞는 말입니다." +
                                "\n귀밑샘에서 분비되는 파로틴이라는 침샘 호르몬 때문입니다. \n" +
                                "이 파로틴(parotin)에는 " +
                                "\n1) 뼈의 성장을 촉진하고 치아를 단단하게 하고 " +
                                "\n2) 백혈구 수를 증가시켜 면역력을 높이며" +
                        "3) 혈당을 떨어뜨리고\n" +
                                "4) 혈관벽을 탄력있게 만들어 주는 등 \n우리 몸에 이로운 역할을 합니다. "
                )
                .show();
    }

    private void digestionDialog(){

        new MaterialDialog.Builder(this)
                .title("소화촉진")
                .content("식사 중 먹는 음식은 우선 입안에서 충분히 씹어 위로 넘기면\n" +
                        "위에서는 이렇게 잘게 잘라진 음식물을\n" +
                        "소화효소와 위 연동 운동으로 더 잘게 부수어서\n" +
                        "소장으로 넘기게 되요.\n" +
                        "그래서 만약에 충분히 음식을 씹지 않고\n" +
                        "대충 넘기게 되면 위는 음식물을 씹는 작용까지\n" +
                        "대신하게 되어 음식물이 위에 머무는 시간이\n" +
                        "증가하게 되는 악순환이 벌어져요.\n" +
                        "그래서 흔히 발생하는\n" +
                        "소화 불량, 복부 팽만감을 느끼게 하는\n" +
                        "원인이 된다고합니다." +
                                "건강한 하루 되세요."
                        )
                .show();
    }

    private void memoryDialog(){

        new MaterialDialog.Builder(this)
                .title("기억력")
               .content("치아와 뇌에는 말초신경과 중추신경을 연결하는 강력한 신경네트워크가 존재합니다. `씹는 행위`는 아래턱에 붙어 있는 저작근을 신축운동으로 해서 아래턱운동을 하게 하고, 운동피질(대뇌반구에서 중심부 앞쪽에 있는 신피질 영역으로 수의적 근육운동을 통제)을 크게 자극합니다. 씹는 행위를 통해 뇌의 혈류가 늘어나고 뇌가 활성화되고 또한 미각과 후각을 더욱 자극하여 결과적으로 뇌를 폭넓게 자극하는 것이 됩니다."
               )
                .show();
    }

    private void insulinDialog(){

        new MaterialDialog.Builder(this)
                .title("인슐린")
                .content("천천히 꼭꼭 씹을수록, 혈당이 천천히 올라가 암세포를 활성화 시키는 인슐린 분비를 낮춰주기에 살이 덜 찝니다. 인슐린 혈당수치가 높으면, 지방합성이 높아집니다. 빠른 식사는 인슐린 분비를 촉진하여 당뇨병, 해독작용, 성인병에 좋지 않습니다. 건강한 식사 하시기 바랍니다."
                )
                .show();
    }

    private void BMIDialog(){

        new MaterialDialog.Builder(this)
                .title("BMI지수")
                .content("식사 시간이 짧을수록 이상지질혈증(고지혈증)이 발생할 확률이 높아 심혈관계 질환의 위험도가 높아집니다.\n\n습관과 각종 건강 지표를 비교 분석한 결과 식사시간이 짧을수록 체질량지수가 높아 비만의 위험이 커지고 혈액에 존재하는 중성지방수치를 높여 이상지질혈증의 위험을 높이는 것입니다.\n\n특히 혈액 내 중성지방수치가 높은 이상지질혈증은 혈액의 점도를 높이고 중성지방이 혈관 벽에 쌓여 혈액의 흐름을 막으면 동맥경화, 급성심근경색, 뇌졸중 등을 유발해 생병까지 위협할 수 있는 위험인자입니다.\n\n빠른 식습관은 식사 양을 많게 해 비만 위험을 높이고 이를 통해 중성지방 증가, HDL 콜레스테롤 저하와 같은 이상지질혈증을 초래해 혈관에 노폐물이 쌓이게 할 위험이 있고 이는 고혈압, 당뇨뿐만 아니라 급성심근경색, 뇌혈관질환, 뇌졸중 등의 위험을 높여 건강을 위협할 수 있습니다\n\n")
                .show();
    }
}

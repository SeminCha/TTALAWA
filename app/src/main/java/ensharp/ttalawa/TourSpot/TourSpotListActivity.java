package ensharp.ttalawa.TourSpot;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ensharp.ttalawa.DBAdapter.SpotsDbAdapter;
import ensharp.ttalawa.R;

public class TourSpotListActivity extends ActionBarActivity implements TourSpotRecyclerAdapter.OnItemClickListener{

    public static Context mmContext;
    private static boolean change;

    SpotsDbAdapter dbAdapter;
    private TextView activityNameTxt;
    public static TourSpotRecyclerAdapter adapter;
    private ArrayList<String> spotList;
    //    요청코드정의
    public static final int REQUEST_CODE_SPOTINFO = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourspotlist);
        activityNameTxt = (TextView) findViewById(R.id.activityName);
        activityNameTxt.setText("추천 관광 명소");

        RecyclerView mTimeRecyclerView = (RecyclerView) findViewById(R.id.tourSpotRecyclerView);
        mTimeRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mTimeRecyclerView.setLayoutManager(layoutManager);

        adapter = new TourSpotRecyclerAdapter(getDataset());
        adapter.setOnItemClickListener(this);
        mTimeRecyclerView.setAdapter(adapter);

        mmContext = this;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getBaseContext(), TourInfoActivity.class);
        intent.putExtra("관광명소",adapter.getItem(position).getSpotName());
        startActivityForResult(intent,REQUEST_CODE_SPOTINFO);

//        startActivityForResult(intent, REQUEST_CODE_SPOTINFO);
//        Toast.makeText(this, "다른액티비티로 "+adapter.getItem(position).getSpotName(), Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

//        if (requestCode == REQUEST_CODE_SPOTINFO) {
            Toast toast = Toast.makeText(getBaseContext(), "onActivityResult() 메소드가 호출됨. 요청코드 : " + requestCode + ", 결과코드 : " + resultCode, Toast.LENGTH_LONG);
            toast.show();

            if (resultCode == RESULT_OK) {
//                String name = intent.getExtras().getString("name");
//                toast = Toast.makeText(getBaseContext(), "응답으로 전달된 name : " + name, Toast.LENGTH_LONG);
//                toast.show();
            }
//        }
    }

    private ArrayList<TourData> getDataset() {

        getDbTourSpot();

        ArrayList<TourData> dataset = new ArrayList<>();

        for(int i=0;i<spotList.size();i++){
            if(i<=5) {
                dataset.add(new TourData(spotList.get(i).toString(), "도심 코스"));
            }else if(i<=14){
                dataset.add(new TourData(spotList.get(i).toString(),"고궁 코스"));
            }else if(i<=19){
                dataset.add(new TourData(spotList.get(i).toString(),"동대문 & 대학로 코스"));
            }else if(i<=21){
                dataset.add(new TourData(spotList.get(i).toString(),"여의도 코스"));
            }else if(i<=24){
                dataset.add(new TourData(spotList.get(i).toString(),"상암 코스"));
            }
        }

        return dataset;
    }

    private void getDbTourSpot(){
        this.dbAdapter = new SpotsDbAdapter(this);

        dbAdapter.open();
        Cursor result = dbAdapter.fetchAllSpots();
        result.moveToFirst();
        String spotName = "";

        spotList = new ArrayList();
        while (!result.isAfterLast()) {
            spotName = result.getString(3);
            spotList.add(spotName);
            result.moveToNext();
        }

        result.close();
        dbAdapter.close();
    }


    @Override
    public void onBackPressed(){
        Intent resultIntent = new Intent();

        // 응답을 전달하고 이 액티비티를 종료합니다.
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    public void btn_Back_Click(View v) {
        setResult(0);
        finish();
    }

    public static void toastA(String position){
        Toast.makeText(mmContext,position,Toast.LENGTH_LONG).show();
        if(change)//이미지변화 조건 -> sharedpreference 비교
        {
            TourSpotRecyclerAdapter.refresh(Integer.valueOf(position));
            adapter.notifyDataSetChanged();
        }
    }
    public static void toastB(){
        Toast.makeText(mmContext,"B",Toast.LENGTH_LONG).show();
    }
}

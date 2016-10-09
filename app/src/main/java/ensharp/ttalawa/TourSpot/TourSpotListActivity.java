package ensharp.ttalawa.TourSpot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ensharp.ttalawa.DBAdapter.SpotsDbAdapter;
import ensharp.ttalawa.R;

public class TourSpotListActivity extends ActionBarActivity implements TourSpotRecyclerAdapter.OnItemClickListener {
    public static String tempBool = "false";
    public static String positionIndex="-1";
    public static Context mmContext;
    public static ArrayList<String> originalDataList;

    SpotsDbAdapter dbAdapter;
    public TextView activityNameTxt;
    public static TourSpotRecyclerAdapter adapter;
    public static RecyclerView mTimeRecyclerView;

    //    요청코드정의
    public static final int REQUEST_CODE_SPOTINFO = 1001;

    public PopupWindow pwindo;
    public Button btnYes;
    public Button btnNo;
    public static TourSpotListActivity instance;

//    public TourSpotListActivity() {
//    }
//
//    public static TourSpotListActivity getInstance()
//    {
//        if (instance == null) {
//            instance = new TourSpotListActivity();
//        }
//        return instance;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourspotlist);
        activityNameTxt = (TextView) findViewById(R.id.activityName);
        activityNameTxt.setText("추천 관광 명소");
        originalDataList = new ArrayList();

        //리사이클러뷰
        mTimeRecyclerView = (RecyclerView) findViewById(R.id.tourSpotRecyclerView);
        mTimeRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mTimeRecyclerView.setLayoutManager(layoutManager);
        adapter = new TourSpotRecyclerAdapter(getDataset()); //데이터를 삽입
        adapter.setOnItemClickListener(this);
        mTimeRecyclerView.setAdapter(adapter);

        mmContext = this;


    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getBaseContext(), TourInfoActivity.class);
        intent.putExtra("관광명소", adapter.getItem(position).getSpotName());
        startActivityForResult(intent, REQUEST_CODE_SPOTINFO);

//        startActivityForResult(intent, REQUEST_CODE_SPOTINFO);
//        Toast.makeText(this, "다른액티비티로 "+adapter.getItem(tempPosition).getSpotName(), Toast.LENGTH_SHORT).show();
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


    public ArrayList<TourData> getDataset() {

        //리스트가 비어있을경우만 db접촉
        if(originalDataList.size()==0)
        {
            getDbTourSpot();
        }

        ArrayList<TourData> dataset = new ArrayList<>();

        Log.i("왜 안돼", String.valueOf(positionIndex));
        int number = Integer.parseInt(positionIndex);
        for (int i = 0; i < originalDataList.size(); i++) {
            ////////////////////////
            if (number == i) {

                dataset.add(new TourData(originalDataList.get(i).toString(), "도심 코스", tempBool));


            }
            //////////////////////////////
            else if (i <= 5) {
                dataset.add(new TourData(originalDataList.get(i).toString(), "도심 코스", "0"));
            } else if (i <= 14) {
                dataset.add(new TourData(originalDataList.get(i).toString(), "고궁 코스", "0"));
            } else if (i <= 19) {
                dataset.add(new TourData(originalDataList.get(i).toString(), "동대문 & 대학로 코스", "0"));
            } else if (i <= 21) {
                dataset.add(new TourData(originalDataList.get(i).toString(), "여의도 코스", "0"));
            } else if (i <= 24) {
                dataset.add(new TourData(originalDataList.get(i).toString(), "상암 코스", "0"));
            }
        }
        return dataset;
    }

    //db에서 데이터 가져오기
    public void getDbTourSpot()
    {
        this.dbAdapter = new SpotsDbAdapter(this);
        dbAdapter.open();
        Cursor result = dbAdapter.fetchAllSpots();
        result.moveToFirst();
        String spotName = "";

        while (!result.isAfterLast()) {
            spotName = result.getString(3);
            originalDataList.add(spotName);
            result.moveToNext();
        }
        result.close();
        dbAdapter.close();
    }


    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();

        // 응답을 전달하고 이 액티비티를 종료합니다.
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void btn_Back_Click(View v) {
        setResult(0);
        finish();
    }


    public static class TempClass {
        //팝업알림창
        public void checkPopUp(String position) {

            //positionIndex 받아옴
            positionIndex=position;

            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(mmContext);
            alert_confirm.setMessage("인증하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'YES'
                            //////////////////////////////GPS인증 코드 ////////////////////////////////
                            //인증이 된다면
//                            adapter = new TourSpotRecyclerAdapter(getDataset()); //데이터를 삽입
//                            adapter.setOnItemClickListener(Temp);
//                            mTimeRecyclerView.setAdapter(adapter);


                        }
                    }).setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 'No'
                            return;
                        }
                    });
            AlertDialog alert = alert_confirm.create();
            alert.show();
        }
    }


}
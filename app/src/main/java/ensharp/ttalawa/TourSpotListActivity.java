package ensharp.ttalawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TourSpotListActivity extends ActionBarActivity implements TourSpotRecyclerAdapter.OnItemClickListener{

    private ListView listview_tourspot;
    SpotsDbAdapter dbAdapter;
    ArrayList<String> tourspotList;
    private TextView activityNameTxt;
//    TourSpotListViewAdapter tourSpotAdapter;
    private TourSpotRecyclerAdapter adapter;

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
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, adapter.getItem(position).getSpotName(), Toast.LENGTH_SHORT).show();
    }

    private ArrayList<TourData> getDataset() {
        ArrayList<TourData> dataset = new ArrayList<>();

        dataset.add(new TourData("경복궁", "도심"));
        dataset.add(new TourData("남산타워", "도심"));
        dataset.add(new TourData("상암1", "상암"));
        dataset.add(new TourData("상암2", "상암"));
        dataset.add(new TourData("상암3", "상암"));

        return dataset;
    }

    //ListView의 아이템 하나가 클릭되는 것을 감지하는 Listener객체 생성 (Button의 OnClickListener와 같은 역할)
    AdapterView.OnItemClickListener tourspotListListener = new AdapterView.OnItemClickListener() {

        //ListView의 아이템 중 하나가 클릭될 때 호출되는 메소드
        //첫번째 파라미터 : 클릭된 아이템을 보여주고 있는 AdapterView 객체(여기서는 ListView객체)
        //두번째 파라미터 : 클릭된 아이템 뷰
        //세번째 파라미터 : 클릭된 아이템의 위치(ListView이 첫번째 아이템(가장위쪽)부터 차례대로 0,1,2,3.....)
        //네번재 파리미터 : 클릭된 아이템의 아이디(특별한 설정이 없다면 세번째 파라이터인 position과 같은 값)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub

            switch (position) {
                case 0:
                    //클릭된 아이템의 위치를 이용하여 데이터인 문자열을 Toast로 출력
                    Toast.makeText(TourSpotListActivity.this, tourspotList.get(position), Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    //클릭된 아이템의 위치를 이용하여 데이터인 문자열을 Toast로 출력
                    Toast.makeText(TourSpotListActivity.this, tourspotList.get(position), Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    @Override
    public void onBackPressed(){
        Intent resultIntent = new Intent();

        // 응답을 전달하고 이 액티비티를 종료합니다.
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}

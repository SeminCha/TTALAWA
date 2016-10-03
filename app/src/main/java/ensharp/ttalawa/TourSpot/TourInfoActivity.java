package ensharp.ttalawa.TourSpot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import ensharp.ttalawa.R;


public class TourInfoActivity extends AppCompatActivity{

    private TextView activityNameTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourinfo);

        // 현재 활성화된 액티비티를 시작하게 한 인텐트 호출
        Intent intent = getIntent();
        int temp=intent.getIntExtra("관광명소",-1);
        Log.d("test", "temp"+temp);
        setTourInfoView(temp);

        //탭 레이아웃
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TourInfoPagerAdapter adapter = new TourInfoPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setTourInfoView(int temp){
        activityNameTxt = (TextView) findViewById(R.id.activityName);
        switch(temp) {
            case 1:
                activityNameTxt.setText("덕수궁 돌담길");
                break;
            case 2:
                activityNameTxt.setText("명동");
                break;
            case 3:
                activityNameTxt.setText("남산골 한옥마을");
                break;
            case 4:
                activityNameTxt.setText("숭례문");
                break;
            case 5:
                activityNameTxt.setText("남산공원");
                break;
            case 6:
                activityNameTxt.setText("N서울타워");
                break;
            case 7:
                activityNameTxt.setText("경복궁");
                break;
            case 8:
                activityNameTxt.setText("광화문 광장");
                break;
            case 9:
                activityNameTxt.setText("종묘");
                break;
            case 10:
                activityNameTxt.setText("보신각 터");
                break;
            case 11:
                activityNameTxt.setText("쌈지길");
                break;
            case 12:
                activityNameTxt.setText("인사동");
                break;
            case 13:
                activityNameTxt.setText("창덕궁과 후원");
                break;
            case 14:
                activityNameTxt.setText("창경궁");
                break;
            case 15:
                activityNameTxt.setText("북촌 한옥 마을");
                break;
            case 16:
                activityNameTxt.setText("흥인지문");
                break;
            case 17:
                activityNameTxt.setText("동대문 패션타운");
                break;
            case 18:
                activityNameTxt.setText("대학로");
                break;
            case 19:
                activityNameTxt.setText("마로니에 공원");
                break;
            case 20:
                activityNameTxt.setText("낙산 공원");
                break;
            case 21:
                activityNameTxt.setText("63빌딩");
                break;
            case 22:
                activityNameTxt.setText("여의도 공원");
                break;
            case 23:
                activityNameTxt.setText("평화의 공원");
                break;
            case 24:
                activityNameTxt.setText("하늘 공원");
                break;
            case 25:
                activityNameTxt.setText("MBC 월드 방송 테마파크");
                break;


        }
    }

}

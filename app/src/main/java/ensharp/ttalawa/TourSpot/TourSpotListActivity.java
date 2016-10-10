package ensharp.ttalawa.TourSpot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ensharp.ttalawa.DBAdapter.SpotsDbAdapter;
import ensharp.ttalawa.MainActivity;
import ensharp.ttalawa.R;

public class TourSpotListActivity extends ActionBarActivity implements TourSpotRecyclerAdapter.OnItemClickListener {

    public static Context mmContext;

    SpotsDbAdapter dbAdapter;
    private TextView activityNameTxt;
    private TourSpotRecyclerAdapter adapter;
    private ArrayList<String> spotList;
    //    요청코드정의
    public static final int REQUEST_CODE_SPOTINFO = 1001;
    LocationManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourspotlist);
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        intent.putExtra("관광명소", adapter.getItem(position).getSpotName());
        startActivityForResult(intent, REQUEST_CODE_SPOTINFO);

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

        for (int i = 0; i < spotList.size(); i++) {
            if (i <= 5) {
                dataset.add(new TourData(spotList.get(i).toString(), "도심 코스"));
            } else if (i <= 14) {
                dataset.add(new TourData(spotList.get(i).toString(), "고궁 코스"));
            } else if (i <= 19) {
                dataset.add(new TourData(spotList.get(i).toString(), "동대문 & 대학로 코스"));
            } else if (i <= 21) {
                dataset.add(new TourData(spotList.get(i).toString(), "여의도 코스"));
            } else if (i <= 24) {
                dataset.add(new TourData(spotList.get(i).toString(), "상암 코스"));
            }
        }

        return dataset;
    }

    private void getDbTourSpot() {
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

    public void getMyLocation() {
        Location myLocation;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "단말기 설정에서 '위치 서비스'사용을 허용해주세요", Toast.LENGTH_SHORT).show();
            //mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        } else {
            myLocation = getLastKnownLocation();
        }
    }

    private Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        Location bestLocation = null;

        while (bestLocation == null) {
            manager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            List<String> providers = manager.getProviders(true);

            for (String provider : providers) {
                Location l = manager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
            }
        }
        return bestLocation;
    }

    public static void tourVisitCheck(int position) {

        if (!MainActivity.isOnGps()) {
            buildAlertMessage("GPS");
        } else {
            Location location = MainActivity.getMyLocation();
            if(checkPosition(location, position - 1)) {
                buildAlertMessage("성공");
            } else {
                buildAlertMessage("실패");
            }
        }
    }

    private static void buildAlertMessage(String mode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mmContext);

        if (mode.equals("GPS")) {
            builder.setMessage("방문 인증 서비스를 이용하시려면, 단말기 설정에서 '위치 서비스' 사용을 허용해주세요.")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (mode.equals("성공")) {
            builder.setMessage("즐거운 라이딩 되셨나요? \n해당 관광지 방문이 확인되었습니다!")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (mode.equals("실패")) {
            builder.setMessage("지금 계신 곳은 해당 관광지가 아니네요!\n조금만 더 근처로 가주세요.")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public static boolean checkPosition(Location myLocation, int btnId) {
        boolean result = false;
        switch (btnId) {
            // 아래<내 위도<위  && 왼< 내 경도 < 오
            //덕수궁 돌담길
            case 0:
                if ((37.564624854689285 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.567592826189404)
                        && (126.9731006026268 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.97722818702461))
                    result = true;
                else
                    result = false;
                break;
            //명동
            case 1:
                if ((37.55887221292253 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.56563527516861)
                        && (126.98163840919732 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.98919687420128))
                    result = true;
                else
                    result = false;
                break;
            //남산골 한옥마을
            case 2:
                if ((37.556816622126604 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.560001248750986)
                        && (126.99206583201884 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.99540384113789))
                    result = true;
                else
                    result = false;
                break;
            //숭례문
            case 3:
                if ((37.55900749613088 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.56037679289365)
                        && (126.97447389364243 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.97586327791213))
                    result = true;
                else
                    result = false;
                break;
            //남산 공원 -> 그냥 갔다온 것으로 처리할 계획
            case 4:
//                if ((37.55719324547463 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.56184307132696)
//                        && (127.01541882008314 <= myLocation.getLongitude() && myLocation.getLongitude() <= 127.020458355546))
//                    result = true;
//                else
//                    result = false;
//                break;
                result = true;
                break;
            //N 서울타워 -> 그냥 갔다온 것으로 처리할 계획
            case 5:
//                if ((37.55719324547463 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.56184307132696)
//                        && (127.01541882008314 <= myLocation.getLongitude() && myLocation.getLongitude() <= 127.020458355546))
//                    result = true;
//                else
//                    result = false;
//                break;
                result = true;
                break;
            // 경복궁
            case 6:
                if ((37.575461045726954 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.58413773751325)
                        && (126.97321258485316 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.98086861521006))
                    result = true;
                else
                    result = false;
                break;
            //광화문 광장 -> 그냥 갔다온 것으로 처리할 계획
            case 7:
//                if ((37.55719324547463 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.56184307132696)
//                        && (127.01541882008314 <= myLocation.getLongitude() && myLocation.getLongitude() <= 127.020458355546))
//                    result = true;
//                else
//                    result = false;
//                break;
                result = true;
                break;
            //종묘
            case 8:
                if ((37.570641198876515 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57763197419156)
                        && (126.9913285598159 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.99681971222161))
                    result = true;
                else
                    result = false;
                break;
            //보신각터
            case 9:
                if ((37.56954580811005 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57014532528512)
                        && (126.98288295418023 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.9844014197588))
                    result = true;
                else
                    result = false;
                break;
            //쌈지길
            case 10:
                if ((37.57399051795766 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57446377916004)
                        && (126.9845362007618 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.98515411466359))
                    result = true;
                else
                    result = false;
                break;
            //인사동 -> 그냥 갔다온 것으로 처리할 계획
            case 11:
//                if ((37.573988126399875 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57446377916004)
//                        && (126.9845362007618 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.98515411466359))
//                    result = true;
//                else
//                    result = false;
//                break;
                result = true;
                break;
            //창덕궁과 후원 -> 그냥 갔다온 것으로 처리할 계획
            case 12:
//                if ((37.57399051795766 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57446377916004)
//                        && (126.9845362007618 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.98515411466359))
//                    result = true;
//                else
//                    result = false;
//                break;
                result = true;
                break;
            //창경궁
            case 13:
                if ((37.57736439805643 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57987590899944)
                        && (126.99317190796138 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.99693202972412))
                    result = true;
                else
                    result = false;
                break;
            //북촌 한옥마을 -> 그냥 갔다온 것으로 처리할 계획
            case 14:
//                if ((37.57399051795766 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57446377916004)
//                        && (126.9845362007618 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.98515411466359))
//                    result = true;
//                else
//                    result = false;
//                break;
                result = true;
                break;
            //흥인지문
            case 15:
                if ((37.5708729243104 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.571249210864345)
                        && (127.00948141515256 <= myLocation.getLongitude() && myLocation.getLongitude() <= 127.0098227262497))
                    result = true;
                else
                    result = false;
                break;
            //동대문 패션타운
            case 16:
                if ((37.56551462073463 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.56859815720111)
                        && (127.00809840112925 <= myLocation.getLongitude() && myLocation.getLongitude() <= 127.0121143385768))
                    result = true;
                else
                    result = false;
                break;
            //대학로  -> 그냥 갔다온 것으로 처리할 계획
            case 17:
//                if ((37.57736439805643 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57987590899944)
//                        && (126.99317190796138 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.99693202972412))
//                    result = true;
//                else
//                    result = false;
//                break;
                result = true;
                break;
            //마로니에 공원
            case 18:
                if ((37.57956237332758 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.580887982510106)
                        && (127.00223609805109 <= myLocation.getLongitude() && myLocation.getLongitude() <= 127.00342163443565))
                    result = true;
                else
                    result = false;
                break;
            //낙산 공원
            case 19:
                if ((37.578051803726865 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.58304998477044)
                        && (127.58183760263979 <= myLocation.getLongitude() && myLocation.getLongitude() <= 127.00897280126815))
                    result = true;
                else
                    result = false;
                break;
            //63스퀘어
            case 20:
                if ((37.51858117991359 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.52097048883535)
                        && (126.93862482905388 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.94096840918066))
                    result = true;
                else
                    result = false;
                break;
            //여의도 공원 -> 그냥 갔다온 것으로 처리할 계획
            case 21:
//                if ((37.51858117991359 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.52097048883535)
//                        && (126.93862482905388 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.94096840918066))
//                    result = true;
//                else
//                    result = false;
//                break;
                result = true;
                break;
            //MBC 월드 방송 테마 파크
            case 22:
                if ((37.5783669400379 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.58196327893457)
                        && (126.88821807503699 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.8927989527583))
                    result = true;
                else
                    result = false;
                break;

            //평화의 공원 -> 그냥 갔다온 것으로 처리할 계획
            case 23:
//                if ((37.51858117991359 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.52097048883535)
//                        && (126.93862482905388 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.94096840918066))
//                    result = true;
//                else
//                    result = false;
//            break;
                result = true;
                break;
            //하늘 공원
            case 24:
                if ((37.56437902438309 <= myLocation.getLatitude() && myLocation.getLatitude() <= 37.57195713465197)
                        && (126.88073504716156 <= myLocation.getLongitude() && myLocation.getLongitude() <= 126.88957259058952))
                    result = true;
                else
                    result = false;
                break;

            default:
                break;
        }
        return result;
    }
}
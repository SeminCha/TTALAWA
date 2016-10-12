package ensharp.ttalawa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapTapi;

import java.util.ArrayList;

import ensharp.ttalawa.TourSpot.TourInfoActivity;

/**
 * Created by Semin on 2016-07-25.
 */
public class StationListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    TMapTapi tmaptapi;
    TelephonyManager telephonyManager;
    String networkoper;

    // ListViewAdapter의 생성자
    public StationListViewAdapter() {

    }


    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final String stationName;
        final double stationLatitude;
        final double stationLongitude;
        final Context context = parent.getContext();
        String convertString;
        TmapAuthentication();
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_nearbystation, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView numberTextView = (TextView) convertView.findViewById(R.id.placenumber);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.placename);
        TextView distanceView = (TextView) convertView.findViewById(R.id.placedistance);
        //TextView distanceUnitView = (TextView) convertView.findViewById(R.id.placedistanceunit);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        numberTextView.setText(listViewItem.getStationNumber());

        nameTextView.setText(listViewItem.getStationName());

        if (listViewItem.getDistance() < 1000) {
            distanceView.setText(String.valueOf(Math.round(listViewItem.getDistance())) + " " + "m");
        } else {
            convertString = String.format("%.1f", listViewItem.getDistance() * 0.001) + " " + "km";
            distanceView.setText(convertString);
        }

        stationName = listViewItem.getStationName();
        stationLatitude = listViewItem.getStationLatitude();
        stationLongitude = listViewItem.getStationLongitude();


        Button directNavigation = (Button) convertView.findViewById(R.id.directNavigationBtn);

        // 가장 가까운 대여소
        if(pos == 0) {
            directNavigation.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    TmapNavigation(stationName + " 거치소", stationLatitude, stationLongitude);
                }
            });
            directNavigation.setVisibility(View.VISIBLE);
        } // 그 외
        else {
            directNavigation.setVisibility(View.GONE);
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String number, String name, String address, float distance, double latitude, double longitude) {
        ListViewItem item = new ListViewItem();
        item.setNumber(number);
        item.setName(name);
        item.setAddress(address);
        item.setDistance(distance);
        item.setLatitude(latitude);
        item.setLongitude(longitude);
        listViewItemList.add(item);
    }

    public class ListViewItem {

        private String stationNumber;
        private String stationName;
        private String stationAddress;
        private float placeDistance;
        private double stationLatitude;
        private double stationLongitude;

        public void setNumber(String number) {
            stationNumber = number;
        }

        public String getStationNumber() {
            return this.stationNumber;
        }

        public void setName(String name) {
            stationName = name;
        }

        public String getStationName() {
            return this.stationName;
        }

        public void setAddress(String address) {
            stationAddress = address;
        }

        public String getStationAddress() {
            return this.stationAddress;
        }

        public void setDistance(float distance) {
            placeDistance = distance;
        }

        public float getDistance() {
            return this.placeDistance;
        }

        public void setLatitude(double latitude) {
            stationLatitude = latitude;
        }

        public double getStationLatitude() {
            return this.stationLatitude;
        }

        public void setLongitude(double longitude) {
            stationLongitude = longitude;
        }

        public double getStationLongitude() {
            return this.stationLongitude;
        }
    }

    private void TmapNavigation(String spotName, double latitude, double longitude) {
        boolean isTmapApp_1 = appInstalledOrNot("com.skt.skaf.l001mtm091");
        boolean isTmapApp_2 = appInstalledOrNot("com.skt.tmap.ku");
        boolean isTmapApp_3 = appInstalledOrNot("com.skt.skaf.l001mtm092");

        if (isTmapApp_1 || isTmapApp_2 || isTmapApp_3) {
            tmaptapi.invokeRoute(spotName, (float) longitude, (float) latitude);
        } else {
            showTmapInstallDialog();
        }
    }


    private void TmapAuthentication() {
        tmaptapi = new TMapTapi(StationListActivity.mStationContext);
        tmaptapi.setSKPMapAuthentication("593851f2-df66-33d7-ae97-52ef7278295f");

        tmaptapi.setOnAuthenticationListener(new TMapTapi.OnAuthenticationListenerCallback() {

            @Override
            public void SKPMapApikeySucceed() {
                Log.i("키인증", "성공");
            }

            @Override
            public void SKPMapApikeyFailed(String errorMsg) {
                Log.i("키인증", "실패");
                Toast.makeText(StationListActivity.mStationContext, "네비게이션 안내 오류로 다음에 이용해 주시기 바랍니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showTmapInstallDialog() {

        telephonyManager = (TelephonyManager) TourInfoActivity.mContextTourInfo.getSystemService(Context.TELEPHONY_SERVICE);
        networkoper = telephonyManager.getNetworkOperatorName();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                TourInfoActivity.mContextTourInfo);

        alertDialogBuilder
                .setMessage("T map 설치가 필요합니다.")
                .setCancelable(false)
                .setPositiveButton("설치하기",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                if (networkoper.equals("SKTelecom")) {
                                    Log.i("통신사", "skt");
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://onesto.re/0000163382"));
                                    StationListActivity.mStationContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.skt.tmap.ku"));
                                    StationListActivity.mStationContext.startActivity(intent);
                                }
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();
        // 다이얼로그 보여주기
        alertDialog.show();
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = StationListActivity.mStationContext.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}





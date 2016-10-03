package ensharp.ttalawa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skp.Tmap.TMapTapi;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceSelectionListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        com.google.android.gms.location.LocationListener {

    GoogleMap mGoogleMap;
    MapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    private static final String LOG_TAG = "PlaceSelectionListener";
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final int REQUEST_SELECT_PLACE = 1000;

    private StationDbAdapter dbAdapter;
    private SpotsDbAdapter spotDbAdapter;

    HashMap markerMap;
    HashMap stationMarkerMap;
    HashMap stationRedMarkMap;
    HashMap tourSpotMarkerMap;
    HashMap neartourSpotStationMarkerMap;
    boolean isSelected;

    ArrayList<TourSpotMarkerItem> TourSpotList;
    ArrayList<MarkerItem> BackupStationList;
    boolean showTourSpot = true;

    LinearLayout stationInfoLayout;
    LinearLayout tourSpotInfoLayout;

    TMapTapi tmaptapi;
    Marker navigationMarker;

    RelativeLayout rentInfoLayout;
    RelativeLayout alarmSettingLayout;
    Toolbar alarmSettingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_main);
        setSupportActionBar(toolbar);

        // 버전이 6.0이상인 경우 허가사용을 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // 검색 기능 코드
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint("장소, 주소입력");
        autocompleteFragment.setBoundsBias(BOUNDS_MOUNTAIN_VIEW);

        stationInfoLayout = (LinearLayout) findViewById(R.id.stationInfoLayout);
        tourSpotInfoLayout = (LinearLayout) findViewById(R.id.tourSpotInfoLayout);
        alarmSettingLayout = (RelativeLayout) findViewById(R.id.alarmSettingLayout);
        alarmSettingToolbar = (Toolbar) findViewById(R.id.toolBar_alarmsetting);
        rentInfoLayout = (RelativeLayout) findViewById(R.id.rentInfoLayout);

        alarmButtonSetting();
        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //구글맵 인스턴스(지도)가 사용될 준비가 되면 this라는 콜백객체를 발생시킨다.
        mapFrag.getMapAsync(this);
    }

    //액티비티가 중지되거나 다시 시작될 때 현재위치표시를 새로고침 해주는 함수
    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);

        mapButtonSetting();
        TmapAuthentication();

        markerMap = new HashMap();
        stationMarkerMap = new HashMap();
        stationRedMarkMap = new HashMap();
        isSelected = false;
        tourSpotMarkerMap = new HashMap();
        neartourSpotStationMarkerMap = new HashMap();
        TourSpotList = new ArrayList();
        BackupStationList = new ArrayList();

        getStationMarkerItems();
        getTourSpotMarkerItems();
        //구글플레이 서비스를 초기화 시킴
        // 버전이 6.0이상인 경우 허가사용을 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
        // 6.0 마시멜로우 버전 이하일 경우 승인요구 없이 위치사용
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    //Google Play Service API로 위치정보를 처리하는 앱에서는 반드시 필요
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("내 위치 정보를 사용하려면, 단말기 설정에서 '위치 서비스' 사용을 허용해주세요.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Log.i(TAG, "구글과의 연결에 실패하였습니다.");
    }

    @Override
    public void onLocationChanged(Location location) {

        //현재 위치에 대한 마커를 띄워줌
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mLastLocation = location;
        //카메라를 옮김
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        // 위치 업데이트를 중지
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        //위치 사용 권한 체크(사용 권한이 없을 경우 -1)
        // 사용자가 권한 요청을 거부한 경우, 다시 권한을 요청해야 한다면, 조금 더 자세한 설명이 필요함.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 최초 권한 요청인지, 혹은 사용자에 의한 재요청인지 확인
            // 만약 값이 true라면, 요청하는 권한에 대해 좀 더 자세히 설명 가능
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // 이 쓰레드는 사용자의 응답을 기다린다. 사용자가 설명을 확인하고, 허가를 재요청하는 경우
                // 허가를 요청하는 prompt가 띄어짐
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // 최초로 권한을 요청하는 경우(첫실행)
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        //사용 권한이 이미 있을 경우
        else {
            return true;
        }
    }

    private void alarmButtonSetting() {
        Button alarmSetting = (Button) findViewById(R.id.alarmSettingBtn);
        ImageButton alarmSettingBack = (ImageButton) findViewById(R.id.alarmSettingBackBtn);

        alarmSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfoLayoutVisibility(stationInfoLayout, false);
                changeInfoLayoutVisibility(tourSpotInfoLayout, false);
                changeAlarmLayoutVisibility(true);
            }
        });

        alarmSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAlarmLayoutVisibility(false);
            }
        });
    }

    private void changeAlarmLayoutVisibility(boolean isVisible) {

        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_in);

        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_out);

        if (isVisible) {
            alarmSettingToolbar.setVisibility(View.VISIBLE);
            alarmSettingLayout.setVisibility(View.VISIBLE);
            alarmSettingLayout.startAnimation(slide_in);
            rentInfoLayout.setBackgroundColor(Color.WHITE);

        } else {
            alarmSettingToolbar.setVisibility(View.GONE);
            alarmSettingLayout.startAnimation(slide_out);
            alarmSettingLayout.setVisibility(View.GONE);
            rentInfoLayout.setBackgroundColor(Color.parseColor("#CCffffff"));
        }
    }


    private void mapButtonSetting() {

        Button myLocation = (Button) findViewById(R.id.myLocationBtn);
        Button stationList = (Button) findViewById(R.id.stationListBtn);
        Button tourSpotMarker = (Button) findViewById(R.id.tourSpotMarkerBtn);

        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = mGoogleMap.getMyLocation();

                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
            }
        });

        stationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng center = mGoogleMap.getCameraPosition().target;
                Intent intent = new Intent(MainActivity.this, StationListActivity.class);
                intent.putExtra("location", center);
                startActivityForResult(intent, 0);
            }
        });

        tourSpotMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Marker marker;
                if (!showTourSpot) {
                    //tourSpotMarkerMap = new HashMap();
                    for (TourSpotMarkerItem markerItem : TourSpotList) {
                        addTourSpotMarker(markerItem);
                    }
                    showTourSpot = true;
                } else {
                    for (int i = 0; i < TourSpotList.size(); i++) {
                        marker = (Marker) tourSpotMarkerMap.get("S" + TourSpotList.get(i).tourSpotName);
                        marker.remove();
                    }
                    uncheckNearStationMarker();
                    showTourSpot = false;
                }
            }
        });
    }

    //권한이 없을 경우 권한사용 동의창을 띄우고, "동의", "비동의"에 대한 콜백을 받는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            //현재 위치 사용권한에 대한 콜백을 받음
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // 만약 요청창이 그냥 꺼지면 result arrays는 empty상태가 된다.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //만약 동의를 받으면 하고자 하는 코드를 심으면 됨
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                    //사용자가 비동의 했을 경우
                } else {
                    Toast.makeText(this, "현재위치인식승인이 거부되었습니다.", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Marker selectedMarker;
        if (marker.equals(markerMap.get("searched"))) {
            uncheckNearStationMarker();
        } else if (marker.equals(tourSpotMarkerMap.get("S" + marker.getTitle()))) {
            changeSelectedMarker(null);
            insertInfoLayoutContent(marker, tourSpotInfoLayout);
            changeInfoLayoutVisibility(tourSpotInfoLayout, true);
            uncheckNearStationMarker();
            checkNearStationMarker(marker);
        } else if (marker.equals(neartourSpotStationMarkerMap.get(marker.getSnippet()))) {
            insertInfoLayoutContent(marker, stationInfoLayout);
            changeInfoLayoutVisibility(stationInfoLayout, true);
        } else {
            uncheckNearStationMarker();
            changeSelectedMarker(marker);
            insertInfoLayoutContent(marker, stationInfoLayout);
            changeInfoLayoutVisibility(stationInfoLayout, true);
            selectedMarker = (Marker) stationRedMarkMap.get("selected");
        }
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
        uncheckNearStationMarker();
        changeInfoLayoutVisibility(stationInfoLayout, false);
        changeInfoLayoutVisibility(tourSpotInfoLayout, false);
    }

    private void insertInfoLayoutContent(Marker marker, LinearLayout view) {

        // 거치소 정보 레이아웃에 대한 정보
        TextView stationName = (TextView) findViewById(R.id.stationNameTxt);
        TextView stationNumber = (TextView) findViewById(R.id.stationNumberTxt);
        TextView stationRack = (TextView) findViewById(R.id.stationRackTxt);
        TextView stationAddress = (TextView) findViewById(R.id.stationAddressTxt);
        Button stationNavigation = (Button) findViewById(R.id.stationNavigationBtn);

        // 관광명소 정보 레이아웃에 대한 정보
        TextView tourSpotName = (TextView) findViewById(R.id.tourSpotNameTxt);
        Button tourSpotNavigation = (Button) findViewById(R.id.tourSpotNavigationBtn);

        navigationMarker = marker;

        if (view == stationInfoLayout) {

            dbAdapter.open();
            Cursor result = dbAdapter.fetchStationByNumber(marker.getSnippet());
            result.moveToFirst();


            String addr_gu = "";
            String new_addr = "";
            String rack_count = "";

            while (!result.isAfterLast()) {
                addr_gu = result.getString(2);
                new_addr = result.getString(3);
                rack_count = result.getString(7);
                result.moveToNext();
            }

            stationName.setText(marker.getTitle());
            stationNumber.setText(marker.getSnippet());
            stationRack.setText(rack_count + "대");
            stationAddress.setText(addr_gu + " " + new_addr);

            stationNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TmapNavigation(navigationMarker, true);
                }
            });


        } else {
            tourSpotName.setText(marker.getTitle());

            tourSpotNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TmapNavigation(navigationMarker, false);
                }
            });

        }
    }

    private void TmapAuthentication() {
        tmaptapi = new TMapTapi(this);
        tmaptapi.setSKPMapAuthentication("593851f2-df66-33d7-ae97-52ef7278295f");

        tmaptapi.setOnAuthenticationListener(new TMapTapi.OnAuthenticationListenerCallback() {

            @Override
            public void SKPMapApikeySucceed() {
                Log.i("키인증", "성공");
            }

            @Override
            public void SKPMapApikeyFailed(String errorMsg) {
                Log.i("키인증", "실패");
                Toast.makeText(MainActivity.this, "네비게이션 안내 오류로 다음에 이용해 주시기 바랍니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void TmapNavigation(Marker marker, boolean isStation) {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String networkoper = telephonyManager.getNetworkOperatorName();
        boolean isTmapApp;

        //boolean isTmapApp = tmaptapi.isTmapApplicationInstalled();
        if (networkoper.equals("SKTelecom")) {
            Log.i("통신사", "skt");
            isTmapApp = appInstalledOrNot("com.skt.skaf.l001mtm091");
            if (isTmapApp) {
                if (isStation) {
                    tmaptapi.invokeRoute(marker.getTitle() + " " + "거치소", (float) marker.getPosition().longitude, (float) marker.getPosition().latitude);
                } else {
                    tmaptapi.invokeRoute(marker.getTitle(), (float) marker.getPosition().longitude, (float) marker.getPosition().latitude);
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://onesto.re/0000163382"));
                startActivity(intent);
            }
        } else if (networkoper.equals("KT") || networkoper.equals("olleh") || networkoper.matches(".*LG.*")) {
            Log.i("통신사", "kt나 lg");
            isTmapApp = appInstalledOrNot("com.skt.skaf.l001mtm092");
            if (isTmapApp) {
                if (isStation) {
                    tmaptapi.invokeRoute(marker.getTitle() + " " + "거치소", (float) marker.getPosition().longitude, (float) marker.getPosition().latitude);
                } else {
                    tmaptapi.invokeRoute(marker.getTitle(), (float) marker.getPosition().longitude, (float) marker.getPosition().latitude);
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.skt.tmap.ku"));
                startActivity(intent);
            }
        } else {
            Log.i("통신사", "없음");
            Toast.makeText(this, "현재 단말기로 네비게이션 서비스를 받으실 수 없습니다.", Toast.LENGTH_LONG).show();
        }

    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void changeInfoLayoutVisibility(LinearLayout view, boolean isVisible) {

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        if (isVisible) {
            // Prepare the View for the animation
            if (stationInfoLayout.getVisibility() == View.VISIBLE || tourSpotInfoLayout.getVisibility() == View.VISIBLE) {
                if (view == stationInfoLayout) {
                    stationInfoLayout.setVisibility(View.VISIBLE);
                    tourSpotInfoLayout.setVisibility(View.GONE);
                } else {
                    stationInfoLayout.setVisibility(View.GONE);
                    tourSpotInfoLayout.setVisibility(View.VISIBLE);
                }
            } else {
                if (view == stationInfoLayout) {
                    stationInfoLayout.setVisibility(View.VISIBLE);
                    stationInfoLayout.startAnimation(slide_up);
                } else {
                    tourSpotInfoLayout.setVisibility(View.VISIBLE);
                    tourSpotInfoLayout.startAnimation(slide_up);
                }
            }
        } else {
            // Prepare the View for the animation
            if (view.getVisibility() == View.VISIBLE) {
                view.startAnimation(slide_down);
                view.setVisibility(View.GONE);
            }
        }
    }

    private void changeSelectedMarker(Marker marker) {
        Marker temp;
        // 선택했던 마커 되돌리기
        if (isSelected) {
            temp = (Marker) stationRedMarkMap.get("selected");
            addMarker(temp, "green");
            temp.remove();
            isSelected = false;
        }
        // 선택한 마커 표시
        if (marker != null) {
            temp = (Marker) stationMarkerMap.get(marker.getSnippet());
            addMarker(temp, "red");
            temp.remove();
            isSelected = true;
        }
    }

    private void uncheckNearStationMarker() {

        Marker removeMarker;

        if (BackupStationList != null) {
            for (int i = 0; i < BackupStationList.size(); i++) {
                removeMarker = (Marker) neartourSpotStationMarkerMap.get(BackupStationList.get(i).contentNum);
                removeMarker.remove();
                addMarker(BackupStationList.get(i), "green");
            }
            BackupStationList.clear();
        }
    }

    @Override
    public void onPlaceSelected(Place place) {
        if (markerMap.containsKey("searched")) {
            Marker marker = (Marker) markerMap.get("searched");
            marker.remove();
        }
        changeSelectedMarker(null);
        uncheckNearStationMarker();
        changeInfoLayoutVisibility(stationInfoLayout, false);
        changeInfoLayoutVisibility(tourSpotInfoLayout, false);
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
        markerOptions.position(latLng);
        markerOptions.title(place.getName().toString());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yellowmarker)));
        markerMap.put("searched", mGoogleMap.addMarker(markerOptions));
        Marker marker = (Marker) markerMap.get("searched");
        changeInfoLayoutVisibility(stationInfoLayout, false);
        changeInfoLayoutVisibility(tourSpotInfoLayout, false);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
    }

    // 결과에 대한 함수
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Marker marker;

        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        } else if (resultCode == 0) {

        } else {
            if (stationMarkerMap.containsKey(String.valueOf(resultCode))) {
                marker = (Marker) stationMarkerMap.get(String.valueOf(resultCode));
                uncheckNearStationMarker();
                changeSelectedMarker(marker);
                insertInfoLayoutContent(marker, stationInfoLayout);
                changeInfoLayoutVisibility(stationInfoLayout, true);
                marker = (Marker) stationRedMarkMap.get("selected");
                Log.i("일반", "일반");
            } else if (neartourSpotStationMarkerMap.containsKey(String.valueOf(resultCode))) {
                marker = (Marker) neartourSpotStationMarkerMap.get(String.valueOf(resultCode));
                uncheckNearStationMarker();
                changeSelectedMarker(marker);
                insertInfoLayoutContent(marker, stationInfoLayout);
                changeInfoLayoutVisibility(stationInfoLayout, true);
                marker = (Marker) stationRedMarkMap.get("selected");
                Log.i("관광지근처", "관광지근처");
            } else {
                marker = (Marker) stationRedMarkMap.get(String.valueOf(resultCode));
                Log.i("선택됨", "선택됨");
            }

            CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
            mGoogleMap.animateCamera(center);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private void getStationMarkerItems() {

        this.dbAdapter = new StationDbAdapter(this);

        dbAdapter.open();
        Cursor result = dbAdapter.fetchAllStations();
        result.moveToFirst();
        String content_name = "";
        String coordinate_x = "";
        String coordinate_y = "";
        String content_num = "";

        ArrayList<MarkerItem> sampleList = new ArrayList();
        while (!result.isAfterLast()) {
            content_name = result.getString(1);
            content_num = result.getString(6);
            coordinate_x = result.getString(4);
            coordinate_y = result.getString(5);
            sampleList.add(new MarkerItem(Double.parseDouble(coordinate_x), Double.parseDouble(coordinate_y), content_name, content_num));
            result.moveToNext();
        }

        result.close();
        dbAdapter.close();

        for (MarkerItem markerItem : sampleList) {
            markerItem.setName(markerItem.getName().substring(4));
            addMarker(markerItem, "green");
        }
    }

    private void getTourSpotMarkerItems() {

        this.spotDbAdapter = new SpotsDbAdapter(this);

        //엑셀파일 데이터를 데이터베이스에 저장
        spotDbAdapter.open();
        Cursor temp = spotDbAdapter.fetchAllSpots();
        Log.w("testing!getCount!", String.valueOf(temp.getCount()));
        spotDbAdapter.close();

        spotDbAdapter.open();
        Cursor result = spotDbAdapter.fetchAllSpots();
        result.moveToFirst();
        String resultStr = "";
        while (!result.isAfterLast()) {
            String spotNum = result.getString(0);
            String spotMapX = result.getString(1);
            String spotMapY = result.getString(2);
            String spotTitle = result.getString(3);

            resultStr += spotNum + ", " + spotMapX + ", " + spotMapY + " , " + spotTitle + "\n";

            Log.w("resultStr:: ", spotNum + ", " + spotMapX + ", " + spotMapY + " , " + spotTitle);
            TourSpotList.add(new TourSpotMarkerItem(Double.parseDouble(spotMapX), Double.parseDouble(spotMapY), spotTitle));
            result.moveToNext();
        }

        result.close();
        spotDbAdapter.close();

        for (TourSpotMarkerItem markerItem : TourSpotList) {
            addTourSpotMarker(markerItem);
        }
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private void addMarker(Marker marker, String markerMode) {
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;
        String stationName = marker.getTitle();
        String stationNumber = marker.getSnippet();
        MarkerItem temp = new MarkerItem(latitude, longitude, stationName, stationNumber);
        addMarker(temp, markerMode);
        return;
    }

    private void addMarker(MarkerItem markerItem, String markerMode) {

        LatLng position = new LatLng(markerItem.getLatitude(), markerItem.getLongitude());
        String stationName = markerItem.getName();
        String stationNumber = markerItem.getNumber();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(stationName);
        markerOptions.position(position);
        markerOptions.snippet(stationNumber);

        if (markerMode.equals("red")) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("redmarker", 110, 180)));
            stationRedMarkMap.put("selected", mGoogleMap.addMarker(markerOptions));
        } else if (markerMode.equals("green")) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("greenmarker", 100, 165)));
            stationMarkerMap.put(stationNumber, mGoogleMap.addMarker(markerOptions));
        } else if (markerMode.equals("nearStation")) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("redmarker", 110, 180)));
            neartourSpotStationMarkerMap.put(stationNumber, mGoogleMap.addMarker(markerOptions));
        }

        return;
    }

    private void addTourSpotMarker(Marker marker) {
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;
        String tourSpotName = marker.getTitle();
        TourSpotMarkerItem temp = new TourSpotMarkerItem(latitude, longitude, tourSpotName);
        addTourSpotMarker(temp);
        return;
    }

    private void addTourSpotMarker(TourSpotMarkerItem markerItem) {
        LatLng position = new LatLng(markerItem.getLatitude(), markerItem.getLongitude());
        String tourSpotName = markerItem.gettourSpotName();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(tourSpotName);
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bluemarker)));
        tourSpotMarkerMap.put("S" + tourSpotName, mGoogleMap.addMarker(markerOptions));
    }


    public class MarkerItem {
        Double latitude;
        Double longitude;
        String contentName;
        String contentNum;


        public MarkerItem(Double latitude, Double longitude, String contentName, String contentNum) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.contentName = contentName;
            this.contentNum = contentNum;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return contentName;
        }

        public void setName(String contentName) {
            this.contentName = contentName;
        }

        public String getNumber() {
            return contentNum;
        }

        public void setNumber(String contentNum) {
            this.contentNum = contentNum;
        }
    }

    public class TourSpotMarkerItem {
        Double latitude;
        Double longitude;
        String tourSpotName;

        public TourSpotMarkerItem(Double latitude, Double longitude, String tourSpotName) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.tourSpotName = tourSpotName;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String gettourSpotName() {
            return tourSpotName;
        }

        public void settourSpotName(String tourSpotName) {
            this.tourSpotName = tourSpotName;
        }
    }

    private void checkNearStationMarker(Marker marker) {

        Marker removeMarker;

        switch (marker.getTitle()) {

            //311, 312
            case "덕수궁 돌담길":
                BackupStationList.add(new MarkerItem(37.5666321, 126.9774684, "서울광장 옆", "311"));
                BackupStationList.add(new MarkerItem(37.5646485, 126.9767202, "시청역 1번출구 뒤", "312"));
                break;
            //321, 322
            case "명동":
                BackupStationList.add(new MarkerItem(37.5651703, 126.9846938, "한국외환은행 본점 앞", "321"));
                BackupStationList.add(new MarkerItem(37.5640689, 126.9863523, "명동성당 앞", "322"));
                break;
            //336
            case "남산골 한옥마을":
                BackupStationList.add(new MarkerItem(37.5625785, 126.9929071, "티마크 호텔 앞", "336"));
                break;
            //313, 324
            case "숭례문":
                BackupStationList.add(new MarkerItem(37.556828, 126.9719665, "서울역 광장 파출소 옆", "313"));
                BackupStationList.add(new MarkerItem(37.561474, 126.9810088, "신세계백화점 본점 앞", "324"));
                break;
            //없음
            case "남산 공원":
                break;
            //없음
            case "N 서울타워":
                break;
            //302, 314
            case "경복궁":
                BackupStationList.add(new MarkerItem(37.5759804, 126.9741132, "경복궁역 4번출구 뒤", "302"));
                BackupStationList.add(new MarkerItem(37.5790923, 126.9803112, "국립현대미술관", "314"));
                break;
            //304, 305, 306
            case "광화문 광장":
                BackupStationList.add(new MarkerItem(37.572552, 126.977427, "광화문역 2번출구 앞", "304"));
                BackupStationList.add(new MarkerItem(37.5725488, 126.9783353, "종로구청 옆", "305"));
                BackupStationList.add(new MarkerItem(37.5706864, 126.9764515, "광화문역 7번출구 앞", "306"));
                break;
            //333, 334, 338
            case "종묘":
                BackupStationList.add(new MarkerItem(37.5775368, 126.9936516, "창덕궁 매표소 앞", "333"));
                BackupStationList.add(new MarkerItem(37.570584, 126.9918076, "종로3가역 2번출구 뒤", "334"));
                BackupStationList.add(new MarkerItem(37.5712826, 126.9974966, "세운스퀘어 앞", "338"));
                break;
            //316, 318, 330
            case "보신각 터":
                BackupStationList.add(new MarkerItem(37.5703991, 126.9818301, "종각역 1번출구 앞", "316"));
                BackupStationList.add(new MarkerItem(37.5685003, 126.9825137, "광교사거리 남측", "318"));
                BackupStationList.add(new MarkerItem(37.5682135, 126.9849536, "청계천 한빛광장", "330"));
                break;
            //315, 326, 327
            case "쌈지길":
                BackupStationList.add(new MarkerItem(37.5758723, 126.9833154, "신한은행 안국역지점 옆", "315"));
                BackupStationList.add(new MarkerItem(37.5762768, 126.9861608, "안국역 5번출구 앞", "326"));
                BackupStationList.add(new MarkerItem(37.5736474, 126.9873623, "낙원상가 옆", "327"));
                break;
            //315, 326
            case "인사동":
                BackupStationList.add(new MarkerItem(37.5758723, 126.9833154, "신한은행 안국역지점 옆", "315"));
                BackupStationList.add(new MarkerItem(37.5762768, 126.9861608, "안국역 5번출구 앞", "326"));
                break;
            //333, 337
            case "창덕궁과 후원":
                BackupStationList.add(new MarkerItem(37.5775368, 126.9936516, "창덕궁 매표소 앞", "333"));
                BackupStationList.add(new MarkerItem(37.5789955, 126.9964652, "창경궁 입구", "337"));
                break;
            //333, 337
            case "창경궁":
                BackupStationList.add(new MarkerItem(37.5775368, 126.9936516, "창덕궁 매표소 앞", "333"));
                BackupStationList.add(new MarkerItem(37.5789955, 126.9964652, "창경궁 입구", "337"));
                break;
            //325, 326
            case "북촌 한옥마을":
                BackupStationList.add(new MarkerItem(37.580003, 126.9849234, "가회동 주민센터 옆", "325"));
                BackupStationList.add(new MarkerItem(37.5762768, 126.9861608, "안국역 5번출구 앞", "326"));
                break;
            //344, 346
            case "흥인지문":
                BackupStationList.add(new MarkerItem(37.5740319, 127.0067303, "성균관대 E하우스 앞", "344"));
                BackupStationList.add(new MarkerItem(37.5688585, 127.0100563, "맥스타일 앞", "346"));
                break;
            //346, 347
            case "동대문 패션타운":
                BackupStationList.add(new MarkerItem(37.5688585, 127.0100563, "맥스타일 앞", "346"));
                BackupStationList.add(new MarkerItem(37.5653792, 127.0078744, "동대문역사문화공원역 9번출구 앞", "347"));
                break;
            //341, 342
            case "대학로":
                BackupStationList.add(new MarkerItem(37.5821752, 127.0017159, "혜화역 3번출구 뒤", "341"));
                BackupStationList.add(new MarkerItem(37.5796835, 127.00248, "대학로 마로니에공원", "342"));
                break;
            //341, 342
            case "마로니에 공원":
                BackupStationList.add(new MarkerItem(37.5821752, 127.0017159, "혜화역 3번출구 뒤", "341"));
                BackupStationList.add(new MarkerItem(37.5796835, 127.00248, "대학로 마로니에공원", "342"));
                break;
            //341, 342
            case "낙산 공원":
                BackupStationList.add(new MarkerItem(37.5821752, 127.0017159, "혜화역 3번출구 뒤", "341"));
                BackupStationList.add(new MarkerItem(37.5796835, 127.00248, "대학로 마로니에공원", "342"));
                break;
            //221, 222
            case "63스퀘어":
                BackupStationList.add(new MarkerItem(37.522529, 126.9376243, "여의도초교 앞", "221"));
                BackupStationList.add(new MarkerItem(37.5190648, 126.9375564, "시범아파트버스정류장 옆", "222"));
                break;
            //202, 205, 206, 213, 214, 210
            case "여의도 공원":
                BackupStationList.add(new MarkerItem(37.5287803, 126.924613, "국민일보 앞", "202"));
                BackupStationList.add(new MarkerItem(37.52629, 126.9204017, "산업은행 앞", "205"));
                BackupStationList.add(new MarkerItem(37.5245621, 126.9178234, "KBS 앞", "206"));
                BackupStationList.add(new MarkerItem(37.5250461, 126.9240667, "신한금융투자후문 앞", "210"));
                BackupStationList.add(new MarkerItem(37.5218453, 126.9188913, "KT 앞", "213"));
                BackupStationList.add(new MarkerItem(37.5230357, 126.9208569, "금융감독원 앞", "214"));
                break;
            //404, 405, 408, 409, 411, 414
            case "MBC 월드 방송 테마 파크":
                BackupStationList.add(new MarkerItem(37.583542, 126.8867311, "우리금융상암센터 교차로", "404"));
                BackupStationList.add(new MarkerItem(37.5825567, 126.8856689, "DMC빌 앞", "405"));
                BackupStationList.add(new MarkerItem(37.5808287, 126.886682, "LG CNS앞", "408"));
                BackupStationList.add(new MarkerItem(37.579368, 126.8891946, "누리꿈스퀘어 옆", "409"));
                BackupStationList.add(new MarkerItem(37.5779916, 126.8914505, "KT 앞", "411"));
                BackupStationList.add(new MarkerItem(37.5781785, 126.8945011, "상암동주민센터 옆", "414"));
                break;
            //413, 420, 421
            case "평화의 공원":
                BackupStationList.add(new MarkerItem(37.5715288, 126.8896663, "상암월드컵파크 3단지 후문", "413"));
                BackupStationList.add(new MarkerItem(37.5662219, 126.8961888, "서울시 공공자전거 운영센터 옆", "420"));
                BackupStationList.add(new MarkerItem(37.5659259, 126.9009017, "마포구청 앞", "421"));
                break;
            //413, 420, 421
            case "하늘 공원":
                BackupStationList.add(new MarkerItem(37.5715288, 126.8896663, "상암월드컵파크 3단지 후문", "413"));
                BackupStationList.add(new MarkerItem(37.5662219, 126.8961888, "서울시 공공자전거 운영센터 옆", "420"));
                BackupStationList.add(new MarkerItem(37.5659259, 126.9009017, "마포구청 앞", "421"));
                break;

            default:
                break;
        }

        for (int i = 0; i < BackupStationList.size(); i++) {
            removeMarker = (Marker) stationMarkerMap.get(BackupStationList.get(i).contentNum.toString());
            removeMarker.remove();
            addMarker(BackupStationList.get(i), "nearStation");
        }
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            if (stationInfoLayout.getVisibility() == View.VISIBLE || tourSpotInfoLayout.getVisibility() == View.VISIBLE) {
                changeInfoLayoutVisibility(stationInfoLayout, false);
                changeInfoLayoutVisibility(tourSpotInfoLayout, false);
            } else if (alarmSettingLayout.getVisibility() == View.VISIBLE) {
                changeAlarmLayoutVisibility(false);
            } else {
                Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
        }

    }
}

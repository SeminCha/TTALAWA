package ensharp.ttalawa.TourSpot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapTapi;

import java.util.ArrayList;
import java.util.Locale;

import ensharp.ttalawa.DBAdapter.SpotsDbAdapter;
import ensharp.ttalawa.MainActivity;
import ensharp.ttalawa.R;
import ensharp.ttalawa.SharedPreferences;

public class TourInfoTab1_Intro extends Fragment {

    private TextView nameText;
    public String spotName;
    private View inflatedView;
    private ImageView imageView;
    private Intent callIntent;
    private Intent webIntent;
    private TextView spotText;
    private TextView spotAddr;
    private RelativeLayout mapBtn;
    private RelativeLayout naviBtn;
    private RelativeLayout callBtn;
    private RelativeLayout webBtn;
    //방문체크 버튼
    private Button tourSpotVisitCheck;
    SharedPreferences pref;
    String key;
    TelephonyManager telephonyManager;
    String networkoper;
    TMapTapi tmaptapi;
    private SpotsDbAdapter spotDbAdapter;

    //관광명소 소개 리스트
    private ArrayList<String> spotIntroList;
    //관광명소 주소 리스트
    private ArrayList<String> spotAddrList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.spot_tab1_intro, container, false);
        callBtn = (RelativeLayout) inflatedView.findViewById(R.id.btn_call);
        webBtn = (RelativeLayout) inflatedView.findViewById(R.id.btn_website);
        mapBtn = (RelativeLayout) inflatedView.findViewById(R.id.showmap_btn);
        naviBtn = (RelativeLayout) inflatedView.findViewById(R.id.navi_btn);

        callBtn.setOnClickListener(btnListener);
        webBtn.setOnClickListener(btnListener);
        mapBtn.setOnClickListener(btnListener);
        naviBtn.setOnClickListener(btnListener);
        tourSpotVisitCheck = (Button) inflatedView.findViewById(R.id.visitCheckBtn);
        tourSpotVisitCheck.setOnClickListener(btnListener);

        if(Locale.getDefault().getLanguage().equals("ko")) {
            getKorIntroDbTourSpot();
        }else{
            getEngIntroDbTourSpot();
        }

        pref = new SharedPreferences(TourInfoActivity.mContextTourInfo);
        spotName = TourInfoActivity.spotName;
        setContentView(spotName);
        //TmapAuthentication();

        spotName = TourInfoActivity.spotName;
        setContentView(spotName);
        return inflatedView;
    }

    public void setTourCheckBtnImage(String key) {
        // 방문한 경우
        if (pref.getValue(key, false, "방문여부")) {
//            tourSpotVisitCheck.setBackground(getResources().getDrawable(R.drawable.visited_btn));
            //   tourSpotVisitCheck.setBackgroundColor(getResources().getColor(R.color.green_light));
        }
        // 방문 안한 경우
        else {
            // tourSpotVisitCheck.setBackgroundColor(getResources().getColor(R.color.grey_dark));
        }
    }

    //한글판 소개 디비 받아오기
    private void getKorIntroDbTourSpot() {
        this.spotDbAdapter = new SpotsDbAdapter(getContext());

        spotDbAdapter.open();
        Cursor result = spotDbAdapter.fetchAllSpots();
        result.moveToFirst();
        String spotKorIntro = "";
        String spotKorAddr="";

        spotIntroList = new ArrayList();
        spotAddrList=new ArrayList();
        while (!result.isAfterLast()) {
            spotKorIntro = result.getString(7);
            spotKorAddr=result.getString(4);
            spotIntroList.add(spotKorIntro);
            spotAddrList.add(spotKorAddr);
            result.moveToNext();
        }

        result.close();
        spotDbAdapter.close();
    }
    //영문판 소개 디비 받아오기
    private void getEngIntroDbTourSpot() {
        this.spotDbAdapter = new SpotsDbAdapter(getContext());

        spotDbAdapter.open();
        Cursor result = spotDbAdapter.fetchAllSpots();
        result.moveToFirst();
        String spotEngIntro = "";
        String spotEngAddr="";

        spotIntroList = new ArrayList();
        spotAddrList=new ArrayList();
        while (!result.isAfterLast()) {
            spotEngIntro = result.getString(8);
            spotEngAddr=result.getString(6);
            spotIntroList.add(spotEngIntro);
            spotAddrList.add(spotEngAddr);
            result.moveToNext();
        }

        result.close();
        spotDbAdapter.close();
    }

    public void setContentView(String spotName) {
        nameText = (TextView) inflatedView.findViewById(R.id.tab1_spotname);
        nameText.setText(spotName);
        imageView = (ImageView) inflatedView.findViewById(R.id.spot_img);
        spotText = (TextView) inflatedView.findViewById(R.id.tab1_spottext);
        spotAddr = (TextView) inflatedView.findViewById(R.id.tab1_spotaddr);
        if (spotName.equals(getResources().getString(R.string.spot_deoksu))) {
            imageView.setImageResource(R.drawable.deoksugung_img);
            spotText.setText(spotIntroList.get(0));
            spotAddr.setText(spotAddrList.get(0));
            setTourCheckBtnImage("0");
        } else if (spotName.equals(getResources().getString(R.string.spot_myeongdong))) {
            imageView.setImageResource(R.drawable.myeongdong_img);
            spotText.setText(spotIntroList.get(1));
            spotAddr.setText(spotAddrList.get(1));
            setTourCheckBtnImage("1");
        } else if (spotName.equals(getResources().getString(R.string.spot_namsangol))) {
            imageView.setImageResource(R.drawable.namsangol_img);
            spotText.setText(spotIntroList.get(2));
            spotAddr.setText(spotAddrList.get(2));
            setTourCheckBtnImage("2");
        } else if (spotName.equals(getResources().getString(R.string.spot_sungnyemun))) {
            imageView.setImageResource(R.drawable.sungryemun_img);
            spotText.setText(spotIntroList.get(3));
            spotAddr.setText(spotAddrList.get(3));
            setTourCheckBtnImage("3");
        } else if (spotName.equals(getResources().getString(R.string.spot_namsanpark))) {
            imageView.setImageResource(R.drawable.namsanpark_img);
            spotText.setText(spotIntroList.get(4));
            spotAddr.setText(spotAddrList.get(4));
            setTourCheckBtnImage("4");
        } else if (spotName.equals(getResources().getString(R.string.spot_nseoul))) {
            imageView.setImageResource(R.drawable.nseoultower_img);
            spotText.setText(spotIntroList.get(5));
            spotAddr.setText(spotAddrList.get(5));
            setTourCheckBtnImage("5");
        } else if (spotName.equals(getResources().getString(R.string.spot_gyeongbok))) {
            imageView.setImageResource(R.drawable.gyeongbokgung_img);
            spotText.setText(spotIntroList.get(6));
            spotAddr.setText(spotAddrList.get(6));
            setTourCheckBtnImage("6");
        } else if (spotName.equals(getResources().getString(R.string.spot_gwanghwamun))) {
            imageView.setImageResource(R.drawable.gwanghwamun_img);
            spotText.setText(spotIntroList.get(7));
            spotAddr.setText(spotAddrList.get(7));
            setTourCheckBtnImage("7");
        } else if (spotName.equals(getResources().getString(R.string.spot_jongmyo))) {
            imageView.setImageResource(R.drawable.jongmyo_img);
            spotText.setText(spotIntroList.get(8));
            spotAddr.setText(spotAddrList.get(8));
            setTourCheckBtnImage("8");
        } else if (spotName.equals(getResources().getString(R.string.spot_bosingak))) {
            imageView.setImageResource(R.drawable.bosingak_img);
            spotText.setText(spotIntroList.get(9));
            spotAddr.setText(spotAddrList.get(9));
            setTourCheckBtnImage("9");
        } else if (spotName.equals(getResources().getString(R.string.spot_ssamzie))) {
            imageView.setImageResource(R.drawable.samziegil_img);
            spotText.setText(spotIntroList.get(10));
            spotAddr.setText(spotAddrList.get(10));
            setTourCheckBtnImage("10");
        } else if (spotName.equals(getResources().getString(R.string.spot_insadong))) {
            imageView.setImageResource(R.drawable.insadong_img);
            spotText.setText(spotIntroList.get(11));
            spotAddr.setText(spotAddrList.get(11));
            setTourCheckBtnImage("11");
        } else if (spotName.equals(getResources().getString(R.string.spot_changdeokgung))) {
            imageView.setImageResource(R.drawable.changdeokgung_img);
            spotText.setText(spotIntroList.get(12));
            spotAddr.setText(spotAddrList.get(12));
            setTourCheckBtnImage("12");
        } else if (spotName.equals(getResources().getString(R.string.spot_changgyeong))) {
            imageView.setImageResource(R.drawable.changkyeonggung_img);
            spotText.setText(spotIntroList.get(13));
            spotAddr.setText(spotAddrList.get(13));
            setTourCheckBtnImage("13");
        } else if (spotName.equals(getResources().getString(R.string.spot_bukchon))) {
            imageView.setImageResource(R.drawable.bukchon_img);
            spotText.setText(spotIntroList.get(14));
            spotAddr.setText(spotAddrList.get(14));
            setTourCheckBtnImage("14");
        } else if (spotName.equals(getResources().getString(R.string.spot_heunginjimun))) {
            imageView.setImageResource(R.drawable.hunginjimun_img);
            spotText.setText(spotIntroList.get(15));
            spotAddr.setText(spotAddrList.get(15));
            setTourCheckBtnImage("15");
        } else if (spotName.equals(getResources().getString(R.string.spot_dongfashion))) {
            imageView.setImageResource(R.drawable.ddmfashion_img);
            spotText.setText(spotIntroList.get(16));
            spotAddr.setText(spotAddrList.get(16));
            setTourCheckBtnImage("16");
        } else if (spotName.equals(getResources().getString(R.string.spot_daehakro))) {
            imageView.setImageResource(R.drawable.daehakro_img);
            spotText.setText(spotIntroList.get(17));
            spotAddr.setText(spotAddrList.get(17));
            setTourCheckBtnImage("17");
        } else if (spotName.equals(getResources().getString(R.string.spot_marronnier))) {
            imageView.setImageResource(R.drawable.maroniae_img);
            spotText.setText(spotIntroList.get(18));
            spotAddr.setText(spotAddrList.get(18));
            setTourCheckBtnImage("18");
        } else if (spotName.equals(getResources().getString(R.string.spot_naksan))) {
            imageView.setImageResource(R.drawable.naksan_img);
            spotText.setText(spotIntroList.get(19));
            spotAddr.setText(spotAddrList.get(19));
            setTourCheckBtnImage("19");
        } else if (spotName.equals(getResources().getString(R.string.spot_63square))) {
            imageView.setImageResource(R.drawable.square_img);
            spotText.setText(spotIntroList.get(20));
            spotAddr.setText(spotAddrList.get(20));
            setTourCheckBtnImage("20");
        } else if (spotName.equals(getResources().getString(R.string.spot_yeouido))) {
            imageView.setImageResource(R.drawable.yeouido_img);
            spotText.setText(spotIntroList.get(21));
            spotAddr.setText(spotAddrList.get(21));
            setTourCheckBtnImage("21");
        } else if (spotName.equals(getResources().getString(R.string.spot_mbc))) {
            imageView.setImageResource(R.drawable.mbcpark_img);
            spotText.setText(spotIntroList.get(22));
            spotAddr.setText(spotAddrList.get(22));
            setTourCheckBtnImage("22");
        } else if (spotName.equals(getResources().getString(R.string.spot_pyeongwha))) {
            imageView.setImageResource(R.drawable.pyeonghwapark_img);
            spotText.setText(spotIntroList.get(23));
            spotAddr.setText(spotAddrList.get(23));
            setTourCheckBtnImage("23");
        } else if (spotName.equals(getResources().getString(R.string.spot_skypark))) {
            imageView.setImageResource(R.drawable.hanulpark_img);
            spotText.setText(spotIntroList.get(24));
            spotAddr.setText(spotAddrList.get(24));
            setTourCheckBtnImage("24");
        }
    }

    //case에 따른 전화 연결 / 인터넷 연결 / 맵 연결 / 네비 연결
    Button.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_call:
                    switch (spotName) {
                        case "덕수궁 돌담길":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-771-9951"));
                            startActivity(callIntent);
                            break;
                        case "명동":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-778-0333"));
                            startActivity(callIntent);
                            break;
                        case "남산골 한옥마을":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2261-0500"));
                            startActivity(callIntent);
                            break;
                        case "숭례문":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:042-481-4650"));
                            startActivity(callIntent);
                            break;
                        case "남산 공원":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3783-5900"));
                            startActivity(callIntent);
                            break;
                        case "N 서울타워":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3455-9277"));
                            startActivity(callIntent);
                            break;
                        case "경복궁":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3700-3900"));
                            startActivity(callIntent);
                            break;
                        case "광화문 광장":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3700-3901"));
                            startActivity(callIntent);
                            break;
                        case "종묘":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3672-4332"));
                            startActivity(callIntent);
                            break;
                        case "보신각 터":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2148-1114"));
                            startActivity(callIntent);
                            break;
                        case "쌈지길":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-736-0088"));
                            startActivity(callIntent);
                            break;
                        case "인사동":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-737-7890"));
                            startActivity(callIntent);
                            break;
                        case "창덕궁과 후원":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3668-2300"));
                            startActivity(callIntent);
                            break;
                        case "창경궁":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-762-4868"));
                            startActivity(callIntent);
                            break;
                        case "북촌 한옥마을":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2133-5580"));
                            startActivity(callIntent);
                            break;
                        case "흥인지문":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-778-0333"));
                            startActivity(callIntent);
                            break;
                        case "동대문 패션타운":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2278-0500"));
                            startActivity(callIntent);
                            break;
                        case "대학로":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2148-1812"));
                            startActivity(callIntent);
                            break;
                        case "마로니에 공원":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2148-4158"));
                            startActivity(callIntent);
                            break;
                        case "낙산 공원":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2148-2842"));
                            startActivity(callIntent);
                            break;
                        case "63스퀘어":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-789-5663"));
                            startActivity(callIntent);
                            break;
                        case "여의도 공원":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2670-3758"));
                            startActivity(callIntent);
                            break;
                        case "MBC 월드 방송 테마 파크":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-789-3705"));
                            startActivity(callIntent);
                            break;
                        case "평화의 공원":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-300-5501"));
                            startActivity(callIntent);
                            break;
                        case "하늘 공원":
                            callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-300-5502"));
                            startActivity(callIntent);
                            break;
                    }
                    break;
                case R.id.btn_website:
                    switch (spotName) {
                        case "덕수궁 돌담길":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.deoksugung.go.kr/"));
                            startActivity(webIntent);
                            break;
                        case "명동":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/MyeongdongTouristInformationCenter/"));
                            startActivity(webIntent);
                            break;
                        case "남산골 한옥마을":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hanokmaeul.or.kr/"));
                            startActivity(webIntent);
                            break;
                        case "숭례문":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_main_search.jsp?cid=128162/"));
                            startActivity(webIntent);
                            break;
                        case "남산 공원":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://parks.seoul.go.kr/namsan/"));
                            startActivity(webIntent);
                            break;
                        case "N 서울타워":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nseoultower.com/"));
                            startActivity(webIntent);
                            break;
                        case "경복궁":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.royalpalace.go.kr/"));
                            startActivity(webIntent);
                            break;
                        case "광화문 광장":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://terms.naver.com/entry.nhn?docId=1065227&cid=40942&categoryId=33076/"));
                            startActivity(webIntent);
                            break;
                        case "종묘":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://jm.cha.go.kr/n_jm/"));
                            startActivity(webIntent);
                            break;
                        case "보신각 터":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tour.jongno.go.kr/tourMain.do/"));
                            startActivity(webIntent);
                            break;
                        case "쌈지길":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ssamzigil.co.kr/"));
                            startActivity(webIntent);
                            break;
                        case "인사동":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hiinsa.com/"));
                            startActivity(webIntent);
                            break;
                        case "창덕궁과 후원":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cdg.go.kr:9901/"));
                            startActivity(webIntent);
                            break;
                        case "창경궁":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cgg.cha.go.kr/n_cgg/index.html/"));
                            startActivity(webIntent);
                            break;
                        case "북촌 한옥마을":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://hanok.seoul.go.kr/"));
                            startActivity(webIntent);
                            break;
                        case "흥인지문":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://korean.visitseoul.net/attractions/%ED%9D%A5%EC%9D%B8%EC%A7%80%EB%AC%B8%EB%8F%99%EB%8C%80%EB%AC%B8_/1999"));
                            startActivity(webIntent);
                            break;
                        case "동대문 패션타운":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dft.co.kr/"));
                            startActivity(webIntent);
                            break;
                        case "대학로":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_main_search.jsp?cid=126534/"));
                            startActivity(webIntent);
                            break;
                        case "마로니에 공원":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_tour.jsp?cid=126487/"));
                            startActivity(webIntent);
                            break;
                        case "낙산 공원":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_tour.jsp?cid=129501/"));
                            startActivity(webIntent);
                            break;
                        case "63스퀘어":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.63.co.kr/"));
                            startActivity(webIntent);
                            break;
                        case "여의도 공원":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_tour.jsp?cid=127955/"));
                            startActivity(webIntent);
                            break;
                        case "MBC 월드 방송 테마 파크":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mbcworld.imbc.com/guide/index.html/"));
                            startActivity(webIntent);
                            break;
                        case "평화의 공원":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://worldcuppark.seoul.go.kr/parkinfo/parkinfo2_1.html/"));
                            startActivity(webIntent);
                            break;
                        case "하늘 공원":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://worldcuppark.seoul.go.kr/parkinfo/parkinfo3_1.html/"));
                            startActivity(webIntent);
                            break;
                    }
                    break;
                case R.id.showmap_btn:
                    ///////////////////////////////////////지도 연결 ///////////////////////////////////////////////////////
                    Intent intent = new Intent();
                    switch (spotName) {
                        case "덕수궁 돌담길":
                            intent.putExtra("key", "덕수궁 돌담길");
                            break;
                        case "명동":
                            intent.putExtra("key", "명동");
                            break;
                        case "남산골 한옥마을":
                            intent.putExtra("key", "남산골 한옥마을");
                            break;
                        case "숭례문":
                            intent.putExtra("key", "숭례문");
                            break;
                        case "남산 공원":
                            intent.putExtra("key", "남산 공원");
                            break;
                        case "N 서울타워":
                            intent.putExtra("key", "N 서울타워");
                            break;
                        case "경복궁":
                            intent.putExtra("key", "경복궁");
                            break;
                        case "광화문 광장":
                            intent.putExtra("key", "광화문 광장");
                            break;
                        case "종묘":
                            intent.putExtra("key", "종묘");
                            break;
                        case "보신각 터":
                            intent.putExtra("key", "보신각 터");
                            break;
                        case "쌈지길":
                            intent.putExtra("key", "쌈지길");
                            break;
                        case "인사동":
                            intent.putExtra("key", "인사동");
                            break;
                        case "창덕궁과 후원":
                            intent.putExtra("key", "창덕궁과 후원");
                            break;
                        case "창경궁":
                            intent.putExtra("key", "창경궁");
                            break;
                        case "북촌 한옥마을":
                            intent.putExtra("key", "북촌 한옥마을");
                            break;
                        case "흥인지문":
                            intent.putExtra("key", "흥인지문");
                            break;
                        case "동대문 패션타운":
                            intent.putExtra("key", "동대문 패션타운");
                            break;
                        case "대학로":
                            intent.putExtra("key", "대학로");
                            break;
                        case "마로니에 공원":
                            intent.putExtra("key", "마로니에 공원");
                            break;
                        case "낙산 공원":
                            intent.putExtra("key", "낙산 공원");
                            break;
                        case "63스퀘어":
                            intent.putExtra("key", "63스퀘어");
                            break;
                        case "여의도 공원":
                            intent.putExtra("key", "여의도 공원");
                            break;
                        case "MBC 월드 방송 테마 파크":
                            intent.putExtra("key", "MBC 월드 방송 테마 파크");
                            break;
                        case "평화의 공원":
                            intent.putExtra("key", "평화의 공원");
                            break;
                        case "하늘 공원":
                            intent.putExtra("key", "하늘 공원");
                            break;
                    }
                    getActivity().setResult(1, intent);
                    getActivity().finish();
                    break;
                case R.id.navi_btn:
                    ////////////////////////////////////// 네비 연결 ////////////////////////////////////////////////////////
                    switch (spotName) {
                        case "덕수궁 돌담길":
                            TmapNavigation("덕수궁 돌담길");
                            break;
                        case "명동":
                            TmapNavigation("명동");
                            break;
                        case "남산골 한옥마을":
                            TmapNavigation("남산골 한옥마을");
                            break;
                        case "숭례문":
                            TmapNavigation("숭례문");
                            break;
                        case "남산 공원":
                            TmapNavigation("남산 공원");
                            break;
                        case "N 서울타워":
                            TmapNavigation("N 서울타워");
                            break;
                        case "경복궁":
                            TmapNavigation("경복궁");
                            break;
                        case "광화문 광장":
                            TmapNavigation("광화문 광장");
                            break;
                        case "종묘":
                            TmapNavigation("종묘");
                            break;
                        case "보신각 터":
                            TmapNavigation("보신각 터");
                            break;
                        case "쌈지길":
                            TmapNavigation("쌈지길");
                            break;
                        case "인사동":
                            TmapNavigation("인사동");
                            break;
                        case "창덕궁과 후원":
                            TmapNavigation("창덕궁과 후원");
                            break;
                        case "창경궁":
                            TmapNavigation("창경궁");
                            break;
                        case "북촌 한옥마을":
                            TmapNavigation("북촌 한옥마을");
                            break;
                        case "흥인지문":
                            TmapNavigation("흥인지문");
                            break;
                        case "동대문 패션타운":
                            TmapNavigation("동대문 패션타운");
                            break;
                        case "대학로":
                            TmapNavigation("대학로");
                            break;
                        case "마로니에 공원":
                            TmapNavigation("마로니에 공원");
                            break;
                        case "낙산 공원":
                            TmapNavigation("낙산 공원");
                            break;
                        case "63스퀘어":
                            TmapNavigation("63스퀘어");
                            break;
                        case "여의도 공원":
                            TmapNavigation("여의도 공원");
                            break;
                        case "MBC 월드 방송 테마 파크":
                            TmapNavigation("MBC 월드 방송 테마 파크");
                            break;
                        case "평화의 공원":
                            TmapNavigation(("평화의 공원"));
                            break;
                        case "하늘 공원":
                            TmapNavigation("하늘 공원");
                            break;
                    }
                    break;
                case R.id.visitCheckBtn:
                    switch (spotName) {
                        case "덕수궁 돌담길":
                            tourVisitCheck("0");
                            break;
                        case "명동":
                            tourVisitCheck("1");
                            break;
                        case "남산골 한옥마을":
                            tourVisitCheck("2");
                            break;
                        case "숭례문":
                            tourVisitCheck("3");
                            break;
                        case "남산 공원":
                            tourVisitCheck("4");
                            break;
                        case "N 서울타워":
                            tourVisitCheck("5");
                            break;
                        case "경복궁":
                            tourVisitCheck("6");
                            break;
                        case "광화문 광장":
                            tourVisitCheck("7");
                            break;
                        case "종묘":
                            tourVisitCheck("8");
                            break;
                        case "보신각 터":
                            tourVisitCheck("9");
                            break;
                        case "쌈지길":
                            tourVisitCheck("10");
                            break;
                        case "인사동":
                            tourVisitCheck("11");
                            break;
                        case "창덕궁과 후원":
                            tourVisitCheck("12");
                            break;
                        case "창경궁":
                            tourVisitCheck("13");
                            break;
                        case "북촌 한옥마을":
                            tourVisitCheck("14");
                            break;
                        case "흥인지문":
                            tourVisitCheck("15");
                            break;
                        case "동대문 패션타운":
                            tourVisitCheck("16");
                            break;
                        case "대학로":
                            tourVisitCheck("17");
                            break;
                        case "마로니에 공원":
                            tourVisitCheck("18");
                            break;
                        case "낙산 공원":
                            tourVisitCheck("19");
                            break;
                        case "63스퀘어":
                            tourVisitCheck("20");
                            break;
                        case "여의도 공원":
                            tourVisitCheck("21");
                            break;
                        case "MBC 월드 방송 테마 파크":
                            tourVisitCheck("22");
                            break;
                        case "평화의 공원":
                            tourVisitCheck("23");
                            break;
                        case "하늘 공원":
                            tourVisitCheck("24");
                            break;
                    }
                    break;

            }
        }
    };

    public void tourVisitCheck(String position) {
        //이미 방문을 한 경우
        if (pref.getValue(position, false, "방문여부")) {
            buildAlertMessage("이미방문", position);
        }
        // 방문을 안했을 경우
        else {
            if (!MainActivity.isOnGps()) {
                buildAlertMessage("GPS", "");
            } else {
                Location location = MainActivity.getMyLocation();
                if (checkPosition(location, Integer.valueOf(position))) {
                    buildAlertMessage("성공", position);
                } else {
                    buildAlertMessage("실패", "");
                }
            }
        }
    }

    public void buildAlertMessage(String mode, String position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(TourInfoActivity.mContextTourInfo);
        key = position;

        if (mode.equals("GPS")) {
            builder.setMessage("방문 인증 서비스를 이용하시려면, 단말기 설정에서 '위치 서비스' 사용을 허용해주세요.")
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (mode.equals("성공")) {
            builder.setMessage("즐거운 관광 되셨나요? \n해당 관광지 방문이 확인되었습니다!")
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                            tourSpotVisitCheck.setBackground(getResources().getDrawable(R.drawable.visited_btn));
                            //tourSpotVisitCheck.setBackgroundColor(getResources().getColor(R.color.green_light));
                            pref.putValue(key, true, "방문여부");
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (mode.equals("실패")) {
            builder.setMessage("지금 계신 곳은 해당 관광지가 아니네요!\n조금만 더 근처로 가주세요.")
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (mode.equals("이미방문")) {
            builder.setMessage("이미 방문하셨습니다! \n방문기록을 삭제할까요?")
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            // tourSpotVisitCheck.setBackgroundColor(getResources().getColor(R.color.grey));
                            pref.putValue(key, false, "방문여부");
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public boolean checkPosition(Location myLocation, int btnId) {
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

    private void TmapNavigation(String spotName) {
        boolean isTmapApp_1 = appInstalledOrNot("com.skt.skaf.l001mtm091");
        boolean isTmapApp_2 = appInstalledOrNot("com.skt.tmap.ku");
        boolean isTmapApp_3 = appInstalledOrNot("com.skt.skaf.l001mtm092");
        String latitude = "37.5648988447", longitude = "126.9764934725";
        this.spotDbAdapter = new SpotsDbAdapter(TourInfoActivity.mContextTourInfo);
        spotDbAdapter.open();
        Cursor result = spotDbAdapter.fetchSpotByTitle(spotName);
        result.moveToFirst();

        while (!result.isAfterLast()) {
            latitude = result.getString(1);
            Log.i("위도", latitude);
            longitude = result.getString(2);
            Log.i("위도", longitude);
            result.moveToNext();
        }
        if (isTmapApp_1 || isTmapApp_2 || isTmapApp_3) {
            tmaptapi.invokeRoute(spotName, Float.parseFloat(longitude), Float.parseFloat(latitude));
        } else {
            showTmapInstallDialog();
        }
    }


    private void TmapAuthentication() {
        tmaptapi = new TMapTapi(TourInfoActivity.mContextTourInfo);
        tmaptapi.setSKPMapAuthentication("593851f2-df66-33d7-ae97-52ef7278295f");

        tmaptapi.setOnAuthenticationListener(new TMapTapi.OnAuthenticationListenerCallback() {

            @Override
            public void SKPMapApikeySucceed() {
                Log.i("키인증", "성공");
            }

            @Override
            public void SKPMapApikeyFailed(String errorMsg) {
                Log.i("키인증", "실패");
                Toast.makeText(TourInfoActivity.mContextTourInfo, R.string.navigationError, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showTmapInstallDialog() {

        telephonyManager = (TelephonyManager) TourInfoActivity.mContextTourInfo.getSystemService(Context.TELEPHONY_SERVICE);
        networkoper = telephonyManager.getNetworkOperatorName();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                TourInfoActivity.mContextTourInfo);

        alertDialogBuilder
                .setMessage(R.string.alertTmapInstall)
                .setCancelable(false)
                .setPositiveButton(R.string.install,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                if (networkoper.equals("SKTelecom")) {
                                    Log.i("통신사", "skt");
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://onesto.re/0000163382"));
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.skt.tmap.ku"));
                                    startActivity(intent);
                                }
                            }
                        })
                .setNegativeButton(R.string.no,
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
        PackageManager pm = getActivity().getPackageManager();
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
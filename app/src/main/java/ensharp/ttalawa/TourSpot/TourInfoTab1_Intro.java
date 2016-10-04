package ensharp.ttalawa.TourSpot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ensharp.ttalawa.R;

public class TourInfoTab1_Intro extends Fragment{

    private TextView nameText;
    public String spotName;
    private View inflatedView;
    private ImageView imageView;
    private Intent callIntent;
    private Intent webIntent;
    private Button callBtn;
    private Button webBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView=inflater.inflate(R.layout.spot_tab1_intro, container, false);
        callBtn=(Button)inflatedView.findViewById(R.id.btn_call);
        webBtn=(Button)inflatedView.findViewById(R.id.btn_website);
        callBtn.setOnClickListener(btnListener);
        webBtn.setOnClickListener(btnListener);

        spotName=TourInfoActivity.spotName;
        setContentView(spotName);
        return inflatedView;
    }

    public void setContentView(String spotName){
        nameText=(TextView)inflatedView.findViewById(R.id.tab1_spotname);
        nameText.setText(spotName);
        imageView=(ImageView)inflatedView.findViewById(R.id.spot_img);
        switch(spotName) {
            case "덕수궁 돌담길":
                imageView.setImageResource(R.drawable.deoksugung_img);
                break;
            case "명동":
                imageView.setImageResource(R.drawable.myeongdong_img);
                break;
            case "남산골 한옥마을":
                imageView.setImageResource(R.drawable.namsangol_img);
                break;
            case "숭례문":
                imageView.setImageResource(R.drawable.sungryemun_img);
                break;
            case "남산 공원":
                imageView.setImageResource(R.drawable.namsanpark_img);
                break;
            case "N 서울타워":
                imageView.setImageResource(R.drawable.nseoultower_img);
                break;
            case "경복궁":
                imageView.setImageResource(R.drawable.gyeongbokgung_img);
                break;
            case "광화문 광장":
                imageView.setImageResource(R.drawable.gwanghwamun_img);
                break;
            case "종묘":
                imageView.setImageResource(R.drawable.jongmyo_img);
                break;
            case "보신각 터":
                imageView.setImageResource(R.drawable.bosingak_img);
                break;
            case "쌈지길":
                imageView.setImageResource(R.drawable.samziegil_img);
                break;
            case "인사동":
                imageView.setImageResource(R.drawable.insadong_img);
                break;
            case "창덕궁과 후원":
                imageView.setImageResource(R.drawable.changdeokgung_img);
                break;
            case "창경궁":
                imageView.setImageResource(R.drawable.changkyeonggung_img);
                break;
            case "북촌 한옥마을":
                imageView.setImageResource(R.drawable.bukchon_img);
                break;
            case "흥인지문":
                imageView.setImageResource(R.drawable.hunginjimun_img);
                break;
            case "동대문 패션타운":
                imageView.setImageResource(R.drawable.ddmfashion_img);
                break;
            case "대학로":
                imageView.setImageResource(R.drawable.daehakro_img);
                break;
            case "마로니에 공원":
                imageView.setImageResource(R.drawable.maroniae_img);
                break;
            case "낙산 공원":
                imageView.setImageResource(R.drawable.naksan_img);
                break;
            case "63스퀘어":
                imageView.setImageResource(R.drawable.square_img);
                break;
            case "여의도 공원":
                imageView.setImageResource(R.drawable.yeouido_img);
                break;
            case "MBC 월드 방송 테마 파크":
                imageView.setImageResource(R.drawable.mbcpark_img);
                break;
            case "평화의 공원":
                imageView.setImageResource(R.drawable.pyeonghwapark_img);
                break;
            case "하늘 공원":
                imageView.setImageResource(R.drawable.hanulpark_img);
                break;
        }
    }

    //case에 따른 전화 연결 및 인터넷 연결
    Button.OnClickListener btnListener=new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_call:
                    switch(spotName){
                        case "덕수궁 돌담길":
                            break;
                        case "명동":
                            break;
                        case "남산골 한옥마을":
                            break;
                        case "숭례문":
                            break;
                        case "남산 공원":
                            break;
                        case "N 서울타워":
                            break;
                        case "경복궁":
                            break;
                        case "광화문 광장":
                            break;
                        case "종묘":
                            break;
                        case "보신각 터":
                            break;
                        case "쌈지길":
                            break;
                        case "인사동":
                            break;
                        case "창덕궁과 후원":
                            break;
                        case "창경궁":
                            break;
                        case "북촌 한옥마을":
                            break;
                        case "흥인지문":
                            break;
                        case "동대문 패션타운":
                            break;
                        case "대학로":
                            break;
                        case "마로니에 공원":
                            break;
                        case "낙산 공원":
                            break;
                        case "63스퀘어":
                            break;
                        case "여의도 공원":
                            break;
                        case "MBC 월드 방송 테마 파크":
                            break;
                        case "평화의 공원":
                            break;
                        case "하늘 공원":
                            break;
                    }
                    break;
                case R.id.btn_website:
                    switch(spotName){
                        case "덕수궁 돌담길":
                            break;
                        case "명동":
                            break;
                        case "남산골 한옥마을":
                            break;
                        case "숭례문":
                            break;
                        case "남산 공원":
                            break;
                        case "N 서울타워":
                            break;
                        case "경복궁":
                            break;
                        case "광화문 광장":
                            break;
                        case "종묘":
                            break;
                        case "보신각 터":
                            break;
                        case "쌈지길":
                            break;
                        case "인사동":
                            break;
                        case "창덕궁과 후원":
                            break;
                        case "창경궁":
                            break;
                        case "북촌 한옥마을":
                            break;
                        case "흥인지문":
                            break;
                        case "동대문 패션타운":
                            break;
                        case "대학로":
                            break;
                        case "마로니에 공원":
                            break;
                        case "낙산 공원":
                            break;
                        case "63스퀘어":
                            break;
                        case "여의도 공원":
                            break;
                        case "MBC 월드 방송 테마 파크":
                            break;
                        case "평화의 공원":
                            break;
                        case "하늘 공원":
                            break;
                    }
                    break;
            }
        }
    };

}

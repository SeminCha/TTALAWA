package ensharp.ttalawa.TourSpot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ensharp.ttalawa.R;

public class TourInfoTab1_Intro extends Fragment{

    private TextView nameText;
    public String spotName;
    private View inflatedView;
    private ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView=inflater.inflate(R.layout.spot_tab1_intro, container, false);
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

}

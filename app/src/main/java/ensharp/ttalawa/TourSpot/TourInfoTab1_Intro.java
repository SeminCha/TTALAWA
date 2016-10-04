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
                imageView.setImageResource(R.drawable.bicycle_logo);
                break;
            case "명동":
                imageView.setImageResource(R.drawable.back_button);
                break;
            case "남산골 한옥마을":
                imageView.setImageResource(R.drawable.redmarker);
                break;
            case "숭례문":
                imageView.setImageResource(R.drawable.greenmarker);
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
    }

}

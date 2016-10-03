package ensharp.ttalawa.TourSpot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
        Log.i("왜안찍히지",String.valueOf(spotName));
        setContentView(spotName);
        return inflatedView;
    }

    public void setContentView(String spotName){
        nameText=(TextView)inflatedView.findViewById(R.id.tab1_spotname);
        nameText.setText(spotName);
        imageView=(ImageView)inflatedView.findViewById(R.id.spot_img);
//        switch(spotNumber) {
//            case 1:
//                nameText.setText("덕수궁 돌담길");
//                imageView.setImageResource(R.drawable.bicycle_logo);
//                break;
//            case 2:
//                nameText.setText("명동");
//                break;
//            case 3:
//                nameText.setText("남산골 한옥마을");
//                break;
//            case 4:
//                nameText.setText("숭례문");
//                break;
//            case 5:
//                nameText.setText("남산공원");
//                break;
//            case 6:
//                nameText.setText("N 서울타워");
//                break;
//            case 7:
//                nameText.setText("경복궁");
//                break;
//            case 8:
//                nameText.setText("광화문 광장");
//                break;
//            case 9:
//                nameText.setText("종묘");
//                break;
//            case 10:
//                nameText.setText("보신각 터");
//                break;
//            case 11:
//                nameText.setText("쌈지길");
//                break;
//            case 12:
//                nameText.setText("인사동");
//                break;
//            case 13:
//                nameText.setText("창덕궁과 후원");
//                break;
//            case 14:
//                nameText.setText("창경궁");
//                break;
//            case 15:
//                nameText.setText("북촌 한옥 마을");
//                break;
//            case 16:
//                nameText.setText("흥인지문");
//                break;
//            case 17:
//                nameText.setText("동대문 패션타운");
//                break;
//            case 18:
//                nameText.setText("대학로");
//                break;
//            case 19:
//                nameText.setText("마로니에 공원");
//                break;
//            case 20:
//                nameText.setText("낙산 공원");
//                break;
//            case 21:
//                nameText.setText("63빌딩");
//                break;
//            case 22:
//                nameText.setText("여의도 공원");
//                break;
//            case 23:
//                nameText.setText("평화의 공원");
//                break;
//            case 24:
//                nameText.setText("하늘 공원");
//                break;
//            case 25:
//                nameText.setText("MBC 월드 방송 테마파크");
//                break;
//
//
//        }
    }

}

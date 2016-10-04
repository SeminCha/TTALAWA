package ensharp.ttalawa.TourSpot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ensharp.ttalawa.R;

public class TourInfoTab2_Hours extends Fragment {

    public String spotName;
    private LinearLayout inflatedLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        spotName = TourInfoActivity.spotName;
        switch (spotName) {
            case "남산골 한옥마을":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_namsangol, container, false);
                break;
            case "남산 공원":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_namsanpark, container, false);
                break;
            case "N 서울타워":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_nseoultower, container, false);
                break;
            case "경복궁":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_gyeongbok, container, false);
                break;
            case "종묘":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_jongmyo, container, false);
                break;
            case "쌈지길":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_samziegil, container, false);
                break;
            case "창덕궁과 후원":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_changdeok, container, false);
                break;
            case "창경궁":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_changkyeong, container, false);
                break;
            case "63스퀘어":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_63square, container, false);
                break;
            case "MBC 월드 방송 테마 파크":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_mbcpark, container, false);
                break;
            case "하늘 공원":
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.hours_skypark, container, false);
                break;
            default:
                inflatedLayout = (LinearLayout) inflater.inflate(R.layout.spot_tab_null, container, false);
                break;
        }
        setContentWork();
        return inflatedLayout;
    }

    public void setContentWork() {

    }

}
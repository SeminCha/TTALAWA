package ensharp.ttalawa.TourSpot;

import android.content.Intent;
import android.net.Uri;
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
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-771-9951"));
                            startActivity(callIntent);
                            break;
                        case "명동":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-778-0333"));
                            startActivity(callIntent);
                            break;
                        case "남산골 한옥마을":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2261-0500"));
                            startActivity(callIntent);
                            break;
                        case "숭례문":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:042-481-4650"));
                            startActivity(callIntent);
                            break;
                        case "남산 공원":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3783-5900"));
                            startActivity(callIntent);
                            break;
                        case "N 서울타워":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3455-9277"));
                            startActivity(callIntent);
                            break;
                        case "경복궁":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3700-3900"));
                            startActivity(callIntent);
                            break;
                        case "광화문 광장":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3700-3901"));
                            startActivity(callIntent);
                            break;
                        case "종묘":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3672-4332"));
                            startActivity(callIntent);
                            break;
                        case "보신각 터":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2148-1114"));
                            startActivity(callIntent);
                            break;
                        case "쌈지길":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-736-0088"));
                            startActivity(callIntent);
                            break;
                        case "인사동":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-737-7890"));
                            startActivity(callIntent);
                            break;
                        case "창덕궁과 후원":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-3668-2300"));
                            startActivity(callIntent);
                            break;
                        case "창경궁":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-762-4868"));
                            startActivity(callIntent);
                            break;
                        case "북촌 한옥마을":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2133-5580"));
                            startActivity(callIntent);
                            break;
                        case "흥인지문":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-778-0333"));
                            startActivity(callIntent);
                            break;
                        case "동대문 패션타운":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2278-0500"));
                            startActivity(callIntent);
                            break;
                        case "대학로":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2148-1812"));
                            startActivity(callIntent);
                            break;
                        case "마로니에 공원":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2148-4158"));
                            startActivity(callIntent);
                            break;
                        case "낙산 공원":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2148-2842"));
                            startActivity(callIntent);
                            break;
                        case "63스퀘어":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-789-5663"));
                            startActivity(callIntent);
                            break;
                        case "여의도 공원":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2670-3758"));
                            startActivity(callIntent);
                            break;
                        case "MBC 월드 방송 테마 파크":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-789-3705"));
                            startActivity(callIntent);
                            break;
                        case "평화의 공원":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-300-5501"));
                            startActivity(callIntent);
                            break;
                        case "하늘 공원":
                            callIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-300-5502"));
                            startActivity(callIntent);
                            break;
                    }
                    break;
                case R.id.btn_website:
                    switch(spotName){
                        case "덕수궁 돌담길":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.deoksugung.go.kr/"));
                            startActivity(webIntent);
                            break;
                        case "명동":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.facebook.com/MyeongdongTouristInformationCenter/"));
                            startActivity(webIntent);
                            break;
                        case "남산골 한옥마을":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.hanokmaeul.or.kr/"));
                            startActivity(webIntent);
                            break;
                        case "숭례문":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_main_search.jsp?cid=128162/"));
                            startActivity(webIntent);
                            break;
                        case "남산 공원":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://parks.seoul.go.kr/template/default.jsp?park_id=namsan/"));
                            startActivity(webIntent);
                            break;
                        case "N 서울타워":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.nseoultower.com/"));
                            startActivity(webIntent);
                            break;
                        case "경복궁":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("www.royalpalace.go.kr:8080/"));
                            startActivity(webIntent);
                            break;
                        case "광화문 광장":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://terms.naver.com/entry.nhn?docId=1065227&cid=40942&categoryId=33076/"));
                            startActivity(webIntent);
                            break;
                        case "종묘":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://jm.cha.go.kr/n_jm/index.html/"));
                            startActivity(webIntent);
                            break;
                        case "보신각 터":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://tour.jongno.go.kr/tourMain.do/"));
                            startActivity(webIntent);
                            break;
                        case "쌈지길":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.ssamzigil.co.kr/"));
                            startActivity(webIntent);
                            break;
                        case "인사동":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.hiinsa.com/"));
                            startActivity(webIntent);
                            break;
                        case "창덕궁과 후원":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.cdg.go.kr:9901/"));
                            startActivity(webIntent);
                            break;
                        case "창경궁":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://cgg.cha.go.kr/n_cgg/index.html/"));
                            startActivity(webIntent);
                            break;
                        case "북촌 한옥마을":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://hanok.seoul.go.kr/"));
                            startActivity(webIntent);
                            break;
                        case "흥인지문":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.deoksugung.go.kr/"));
                            startActivity(webIntent);
                            break;
                        case "동대문 패션타운":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.dft.co.kr/"));
                            startActivity(webIntent);
                            break;
                        case "대학로":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_main_search.jsp?cid=126534/"));
                            startActivity(webIntent);
                            break;
                        case "마로니에 공원":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_tour.jsp?cid=126487/"));
                            startActivity(webIntent);
                            break;
                        case "낙산 공원":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_tour.jsp?cid=129501/"));
                            startActivity(webIntent);
                            break;
                        case "63스퀘어":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("www.63.co.kr/"));
                            startActivity(webIntent);
                            break;
                        case "여의도 공원":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://korean.visitkorea.or.kr/kor/bz15/where/where_tour.jsp?cid=127955/"));
                            startActivity(webIntent);
                            break;
                        case "MBC 월드 방송 테마 파크":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://mbcworld.imbc.com/guide/index.html/"));
                            startActivity(webIntent);
                            break;
                        case "평화의 공원":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://worldcuppark.seoul.go.kr/parkinfo/parkinfo2_1.html/"));
                            startActivity(webIntent);
                            break;
                        case "하늘 공원":
                            webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://worldcuppark.seoul.go.kr/parkinfo/parkinfo3_1.html/"));
                            startActivity(webIntent);
                            break;
                    }
                    break;
            }
        }
    };

}

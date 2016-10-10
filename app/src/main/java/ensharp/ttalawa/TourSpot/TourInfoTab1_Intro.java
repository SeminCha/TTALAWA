package ensharp.ttalawa.TourSpot;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.spot_tab1_intro, container, false);
        callBtn = (RelativeLayout) inflatedView.findViewById(R.id.btn_call);
        webBtn = (RelativeLayout) inflatedView.findViewById(R.id.btn_website);
        mapBtn = (RelativeLayout) inflatedView.findViewById(R.id.showmap_btn);
        naviBtn = (RelativeLayout) inflatedView.findViewById(R.id.navi_btn);
        tourSpotVisitCheck = (Button) inflatedView.findViewById(R.id.visitCheckBtn);

        callBtn.setOnClickListener(btnListener);
        webBtn.setOnClickListener(btnListener);
        mapBtn.setOnClickListener(btnListener);
        naviBtn.setOnClickListener(btnListener);

        tourSpotVisitCheck.setOnClickListener(btnListener);
        pref = new SharedPreferences(TourInfoActivity.mContextTourInfo);
        spotName = TourInfoActivity.spotName;
        setContentView(spotName);


        return inflatedView;
    }

    public void setTourCheckBtnImage(String key) {
        // 방문한 경우
        if(pref.getValue(key,false,"방문여부")) {
            tourSpotVisitCheck.setBackgroundColor(getResources().getColor(R.color.green_light));
        }
        // 방문 안한 경우
        else {
            tourSpotVisitCheck.setBackgroundColor(getResources().getColor(R.color.grey_dark));
        }
    }


    public void setContentView(String spotName) {
        nameText = (TextView) inflatedView.findViewById(R.id.tab1_spotname);
        nameText.setText(spotName);
        imageView = (ImageView) inflatedView.findViewById(R.id.spot_img);
        spotText = (TextView) inflatedView.findViewById(R.id.tab1_spottext);
        spotAddr = (TextView) inflatedView.findViewById(R.id.tab1_spotaddr);
        switch (spotName) {
            case "덕수궁 돌담길":
                imageView.setImageResource(R.drawable.deoksugung_img);
                spotText.setText("한국의 아름다운 길 100에 선정된 길. 아름드리 가로수와 돌담이 어우러진 멋스러운 장소.");
                spotAddr.setText("중구 세종대로 99");
                setTourCheckBtnImage("0");
                break;
            case "명동":
                imageView.setImageResource(R.drawable.myeongdong_img);
                spotText.setText("한국의 대표적인 쇼핑타운으로 백화점을 비롯 해 유명 화장품, 의류, 신발브랜드와 액세서리 숍들을 한 곳에서 만날 수 있다.");
                spotAddr.setText("중구 명동 1가, 2가");
                setTourCheckBtnImage("1");
                break;
            case "남산골 한옥마을":
                imageView.setImageResource(R.drawable.namsangol_img);
                spotText.setText("잘 보존된 한옥들에 정자와 연못까지 만들어 조선시대 마을의 모습을 그대로 살렸다. 조선 시대 주거문화를 둘러본 후 전통문화 체험에 참여해본다.");
                spotAddr.setText("중구 퇴계로34길 28");
                setTourCheckBtnImage("2");
                break;
            case "숭례문":
                imageView.setImageResource(R.drawable.sungryemun_img);
                spotText.setText("국보 1호 숭례문은 한양 도성의 남쪽 정문의 역할을 했던 문으로 남대문 이라고도 불린다.");
                spotAddr.setText("중구 세종대로 40");
                setTourCheckBtnImage("3");
                break;
            case "남산 공원":
                imageView.setImageResource(R.drawable.namsanpark_img);
                spotText.setText("우거진 숲과 N서울타워를 비롯해 팔각정, 야외 식물원, 국립 중앙 극장 등 다양한 명소들을 둘러보는 즐거움을 누릴 수 있다.");
                spotAddr.setText("중구 삼일대로 231");
                setTourCheckBtnImage("4");
                break;
            case "N 서울타워":
                imageView.setImageResource(R.drawable.nseoultower_img);
                spotText.setText("서울을 한눈에 조망할 수 있는 남산의 명소. 2, 3층 전망대와 회전 레스토랑에서 아름다운 서울의 야경을 관람하고, 매일 서울 밤하늘에 현란한 빛의 예술을 선보이는 레이저 쇼도 즐겨보자.");
                spotAddr.setText("용산구 남산공원길 105");
                setTourCheckBtnImage("5");
                break;
            case "경복궁":
                imageView.setImageResource(R.drawable.gyeongbokgung_img);
                spotText.setText("조선시대 최초의 궁궐로 조선왕조 500년의 역사가 시작된 곳이다. 서울에 남아 있는 5대 궁궐 중에서 가장 크고 웅장하며, 근정전, 경회루 등 대표 전각을 통해 격조 높은 조선 왕실 문화 와 생활상, 당시의 건축문화까지 엿볼 수 있다. 궁중 문화를 전시한 고궁박물관과 한국 생활사를 볼 수 있는 민속박물관도 있다.");
                spotAddr.setText("종로구 사직로 161");
                setTourCheckBtnImage("6");
                break;
            case "광화문 광장":
                imageView.setImageResource(R.drawable.gwanghwamun_img);
                spotText.setText("경복궁의 정문 광화문 앞에 조성된 길이 555m, 너비 34m의 대형 광장. 한국인들이 가 장 존경하는 위인 세종대왕과 이순신 장군의 동상이 있다.");
                spotAddr.setText("종로구 세종로 1-68");
                setTourCheckBtnImage("7");
                break;
            case "종묘":
                imageView.setImageResource(R.drawable.jongmyo_img);
                spotText.setText("조선왕조 역대 왕과 왕비의 신주를 모시고 제사를 지내는 유교사당으로 1995년 유네스코 세계 문화유산에 등재되었다. 매주 토요일, 마지막 주 수요일은 자유 관람을, 그 외에는 문화재 해설 사와 함께 하는 제한 관람을 실시하고 있다.");
                spotAddr.setText("종로구 종로 157");
                setTourCheckBtnImage("8");
                break;
            case "보신각 터":
                imageView.setImageResource(R.drawable.bosingak_img);
                spotText.setText("조선시대, 하루의 시각을 알렸던 곳. 현재에도 당시의 모습을 재현해 하루 한 번 타종행사를 하고 있다. 매년 1월 1일에는 새해를 알리는 타종행사가 열린다.");
                spotAddr.setText("종로구 종로 54");
                setTourCheckBtnImage("9");
                break;
            case "쌈지길":
                imageView.setImageResource(R.drawable.samziegil_img);
                spotText.setText("다양한 전통 공예 디자인 상품을 판매하는 복합 문화 공간. 곳곳에서 전시와 공연도 볼 수 있다.");
                spotAddr.setText("종로구 인사동길 44");
                setTourCheckBtnImage("10");
                break;
            case "인사동":
                imageView.setImageResource(R.drawable.insadong_img);
                spotText.setText("고미술품과 고서적 상점을 비롯해 전통찻집, 전통공예품 매장 등이 밀집해 있는 한국 전통 문화의 거리. 오래전부터 화랑과 갤러리가 많아 서울의 대표적인 화랑 거리로 꼽힌다.");
                spotAddr.setText("종로구 인사동길");
                setTourCheckBtnImage("11");
                break;
            case "창덕궁과 후원":
                imageView.setImageResource(R.drawable.changdeokgung_img);
                spotText.setText("1405년 지어진 조선왕조의 이궁으로 조선시대 궁궐 중 원형이 가장 잘 보존된 궁이다. 자연과 의 조화로운 배치가 탁월한 점에서 1997년 유네스코 세계유산으로 등록되었다.");
                spotAddr.setText("종로구 율곡로 99");
                setTourCheckBtnImage("12");
                break;
            case "창경궁":
                imageView.setImageResource(R.drawable.changkyeonggung_img);
                spotText.setText("조선의 9대 임금 성종이 세 명의 왕후를 위해 지은 효심이 깃든 궁궐이다. 창경궁의 정문 홍 화문 등을 통해 당시의 뛰어난 건축미와 지난 역사를 만날 수 있다.");
                spotAddr.setText("종로구 창경궁로 185");
                setTourCheckBtnImage("13");
                break;
            case "북촌 한옥마을":
                imageView.setImageResource(R.drawable.bukchon_img);
                spotText.setText("경복궁과 창덕궁 사이에 900여 동의 한옥이 빼곡하게 들어선 곳으로 조선시대 왕족과 귀족들이 모여 살았다. 과거 서울의 모습이 잘 남아 있어 영화나 드라마 촬영지로 인기이다. 주민들의 거주 공간이므로 조용한 관광이 필요하다.");
                spotAddr.setText("종로구 계동");
                setTourCheckBtnImage("14");
                break;
            case "흥인지문":
                imageView.setImageResource(R.drawable.hunginjimun_img);
                spotText.setText("국가지정 보물 1호. 한양 도성의 8개 성문 중 동 쪽에 자리해, 동대문 이라고도 불린다. 좌우의 성벽은 사라졌으나 성문만은 조선시대 모습 그대로 남아 웅장한 아름다움을 보여준다.");
                spotAddr.setText("종로구 종로 288");
                setTourCheckBtnImage("15");
                break;
            case "동대문 패션타운":
                imageView.setImageResource(R.drawable.ddmfashion_img);
                spotText.setText("24시간 쇼핑이 가능해 젊은이들의 데이트 코스로도 사랑받고 있다. 서울을 방문하는 외국인 관광객의 45%가 즐겨 찾는 대단위 쇼핑 단지다.");
                spotAddr.setText("중구 을지로, 장충단로 ");
                setTourCheckBtnImage("16");
                break;
            case "대학로":
                imageView.setImageResource(R.drawable.daehakro_img);
                spotText.setText("소극장 150여 개가 한데 모여 있는 공연문화의 중심지. 매일 배우들의 생생한 연기가 소극장 무대에 펼쳐지고, 길거리에서는 젊은 예술가들이 즉흥 공연을 펼친다. 소극장 사이사이 갤러리와 맛집, 영화관 등 밤낮으로 즐길 거리가 다양하다.");
                spotAddr.setText("종로구 종로5가 82-1~혜화동 56");
                setTourCheckBtnImage("17");
                break;
            case "마로니에 공원":
                imageView.setImageResource(R.drawable.maroniae_img);
                spotText.setText("커다란 마로니에 가로수가 자리한 작은 공원 은 한때 서울대학교 교정이었으나 지금은 대학로의 중심이다. 거리의 화가를 비롯해 젊은 예술가와 음악인들이 자신의 끼를 맘껏 펼치는 야외공연장이기도 하다.");
                spotAddr.setText("종로구 동숭동 1-124");
                setTourCheckBtnImage("18");
                break;
            case "낙산 공원":
                imageView.setImageResource(R.drawable.naksan_img);
                spotText.setText("낙타의 등을 닮아 낙산이라 이름 붙여진 낙산 공원에 오르면 대학로를 한눈에 조망할 수 있다. 한류 드라마 촬영지로도 유명한 이곳은 특히 일몰과 야경이 아름답다.");
                spotAddr.setText("종로구 낙산길 54");
                setTourCheckBtnImage("19");
                break;
            case "63스퀘어":
                imageView.setImageResource(R.drawable.square_img);
                spotText.setText("국내 최고층 빌딩 중 하나로 꼽히는 복합 문화공간 63빌딩. 63아트(전망대 미술관), 아쿠아 플라넷 63(아쿠아리움) 등 다양한 즐길 거리가 준비되어 있다. 특히 세계에서 가장 높은 미술관으로 불리는 63아트에서 내려다보는 서울의 야경이 아름답다.");
                spotAddr.setText("영등포구 63로 50");
                setTourCheckBtnImage("20");
                break;
            case "여의도 공원":
                imageView.setImageResource(R.drawable.yeouido_img);
                spotText.setText("도심 한복판에 숲으로 둘러싸인 넓은 광장이다. 생태 숲과 나무가 우거진 길을 따라 산책을 즐기거나 자전거나 인라인 스케이트를 빌려 공원을 달려볼 수 있다. 공원 내에는 세종대왕 동상과 그의 발명품 모형들이 배치되어 또 하나의 볼거리를 제공한다.");
                spotAddr.setText("영등포구 여의공원로 68");
                setTourCheckBtnImage("21");
                break;
            case "MBC 월드 방송 테마 파크":
                imageView.setImageResource(R.drawable.mbcpark_img);
                spotText.setText("한류 콘텐츠를 중심으로 MBC 명품 드라마와 인기 예능 프로그램들을 즐길 수 있는 대한민국 방송사 최초 방송테마파크, MBC 월드.");
                spotAddr.setText("마포구 성암로 267");
                setTourCheckBtnImage("22");
                break;
            case "평화의 공원":
                imageView.setImageResource(R.drawable.pyeonghwapark_img);
                spotText.setText("평화의 공원은 월드컵공원 전체를 대표하는 공간으로, 유니세프 광장과 난지연못를 비롯해 평화의 정원, 피크닉장, 난지도이야기(월드컵공원 전시관)등이 있다.");
                spotAddr.setText("마포구 월드컵로 243-60");
                setTourCheckBtnImage("23");
                break;
            case "하늘 공원":
                imageView.setImageResource(R.drawable.hanulpark_img);
                spotText.setText("난지도에서 가장 높은 이 곳 하늘공원에 서면 서울의 풍광이 한 눈에 펼쳐져 북쪽으로는 북한산, 동쪽으로는 남산과 63빌딩, 남쪽으로는 한강, 서쪽으로는 행주산성이 보인다.");
                spotAddr.setText("마포구 하늘공원로 95");
                setTourCheckBtnImage("24");
                break;
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
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://parks.seoul.go.kr/template/default.jsp?park_id=namsan/"));
                            startActivity(webIntent);
                            break;
                        case "N 서울타워":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nseoultower.com/"));
                            startActivity(webIntent);
                            break;
                        case "경복궁":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.royalpalace.go.kr:8080/"));
                            startActivity(webIntent);
                            break;
                        case "광화문 광장":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://terms.naver.com/entry.nhn?docId=1065227&cid=40942&categoryId=33076/"));
                            startActivity(webIntent);
                            break;
                        case "종묘":
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://jm.cha.go.kr/n_jm/index.html/"));
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
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.deoksugung.go.kr/"));
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
                            webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.63.co.kr/"));
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


                    break;

                case R.id.navi_btn:


                    ////////////////////////////////////// 네비 연결 ////////////////////////////////////////////////////////
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
                buildAlertMessage("GPS","");
            } else {
                Location location = MainActivity.getMyLocation();
                if (checkPosition(location, Integer.valueOf(position))) {
                    buildAlertMessage("성공", position);
                } else {
                    buildAlertMessage("실패","");
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
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (mode.equals("성공")) {
            builder.setMessage("즐거운 라이딩 되셨나요? \n해당 관광지 방문이 확인되었습니다!")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            tourSpotVisitCheck.setBackgroundColor(getResources().getColor(R.color.green_light));
                            pref.putValue(key,true,"방문여부");
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (mode.equals("실패")) {
            builder.setMessage("지금 계신 곳은 해당 관광지가 아니네요!\n조금만 더 근처로 가주세요.")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (mode.equals("이미방문")) {
            builder.setMessage("이미 방문해 주셨네요! \n방문기록을 삭제할까요?")
                    .setCancelable(false)
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            tourSpotVisitCheck.setBackgroundColor(getResources().getColor(R.color.grey));
                            pref.putValue(key,false,"방문여부");
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
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

}

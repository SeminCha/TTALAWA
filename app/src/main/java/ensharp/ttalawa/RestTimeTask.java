package ensharp.ttalawa;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;

/**
 * Created by mh on 2016-08-01.
 */
public class RestTimeTask extends AsyncTask<Void, Void, Void> {

    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    Intent intent;
    PendingIntent contentIntent;
    public final Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.mContext.getResources(), android.R.drawable.ic_menu_gallery); // 아이콘 ic_menu_gallery를 띄워준다.;
    CountDownTimer timer;
    String charging;

    protected void onPreExecute() {
        if (MainActivity.mode == MainActivity.rent) {
            intent = new Intent(MainActivity.mContext, MainActivity.class);
            contentIntent = PendingIntent.getActivity(MainActivity.mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            charging = "과금 0 원";
            //큰 아이콘

            notificationManager = (NotificationManager) MainActivity.mContext.getSystemService(MainActivity.mContext.NOTIFICATION_SERVICE);


            timer = new CountDownTimer(3600000, 1000) {

                public void onTick(long millisUntilFinished) {
                    if ((millisUntilFinished < 1800000) && (millisUntilFinished > 1709000)) {
                        notificationBuilder = new NotificationCompat.Builder(MainActivity.mContext);
                        notificationBuilder
                                .setLargeIcon(bitmap) // 이미지 띄워주기
                                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                                .setContentTitle("30분 남았습니다")
                                .setContentText(charging) // 텍스트 띄우기
                                .setTicker("30분 남았습니다") // 상태 바에 뜨는 문구
                                .setContentIntent(contentIntent)
                                .setAutoCancel(true);

                        notificationManager.notify(1, notificationBuilder.build());
                    } else if ((millisUntilFinished < 1200000) && (millisUntilFinished > 1109000)) {
                        notificationBuilder = new NotificationCompat.Builder(MainActivity.mContext);
                        notificationBuilder
                                .setLargeIcon(bitmap) // 이미지 띄워주기
                                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                                .setContentTitle("20분 남았습니다")
                                .setContentText(charging) // 텍스트 띄우기
                                .setTicker("20분 남았습니다") // 상태 바에 뜨는 문구
                                .setContentIntent(contentIntent)
                                .setAutoCancel(true);

                        notificationManager.notify(1, notificationBuilder.build());
                    } else if ((millisUntilFinished < 600000) && (millisUntilFinished > 509000)) {
                        MainActivity.mainTxtView.setTextColor(Color.parseColor("#FF0000"));
                        notificationBuilder = new NotificationCompat.Builder(MainActivity.mContext);
                        notificationBuilder
                                .setLargeIcon(bitmap) // 이미지 띄워주기
                                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                                .setContentTitle("10분 남았습니다")
                                .setContentText(charging) // 텍스트 띄우기
                                .setTicker("10분 남았습니다") // 상태 바에 뜨는 문구
                                .setContentIntent(contentIntent)
                                .setAutoCancel(true);

                        notificationManager.notify(1, notificationBuilder.build());
                    } else if ((millisUntilFinished < 300000) && (millisUntilFinished > 209000)) {
                        MainActivity.mainTxtView.setTextColor(Color.parseColor("#FF0000"));
                        notificationBuilder = new NotificationCompat.Builder(MainActivity.mContext);
                        notificationBuilder
                                .setLargeIcon(bitmap) // 이미지 띄워주기
                                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                                .setContentTitle("5분 남았습니다")
                                .setContentText(charging) // 텍스트 띄우기
                                .setTicker("5분 남았습니다") // 상태 바에 뜨는 문구
                                .setContentIntent(contentIntent)
                                .setAutoCancel(true);

                        notificationManager.notify(1, notificationBuilder.build());
                    }
                    MainActivity.mainTxtView.setText(millisUntilFinished / 1000 / 60 / 60 + ":" + millisUntilFinished / 1000 / 60 % 60 + ":" + millisUntilFinished / 1000 % 60);
                    MainActivity.overChargingView.setText(charging);
                    if (MainActivity.mode == MainActivity.returned) {
                        timer.cancel();
                        initData();
                    }
                }

                public void onFinish() {
                    if(MainActivity.mode == MainActivity.rent){
                        MainActivity.mode = MainActivity.firstOver;
                        charging = "과금 1000원";
                        timeOver();
                    } else {
                        initData();
                    }
                }
            }.start();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        return null;
    }

    protected void onPostExecute(Void result) {

    }

    public void timeOver(){
        timer = new CountDownTimer(1800000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished < 1200000) && (millisUntilFinished > 1109000)) {
                    notificationBuilder = new NotificationCompat.Builder(MainActivity.mContext);
                    notificationBuilder
                            .setLargeIcon(bitmap) // 이미지 띄워주기
                            .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                            .setContentTitle("20분 남았습니다")
                            .setContentText(charging) // 텍스트 띄우기
                            .setTicker("20분 남았습니다") // 상태 바에 뜨는 문구
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true);

                    notificationManager.notify(1, notificationBuilder.build());
                } else if ((millisUntilFinished < 600000) && (millisUntilFinished > 509000)) {
                    notificationBuilder = new NotificationCompat.Builder(MainActivity.mContext);
                    notificationBuilder
                            .setLargeIcon(bitmap) // 이미지 띄워주기
                            .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                            .setContentTitle("10분 남았습니다")
                            .setContentText(charging) // 텍스트 띄우기
                            .setTicker("10분 남았습니다") // 상태 바에 뜨는 문구
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true);

                    notificationManager.notify(1, notificationBuilder.build());
                } else if ((millisUntilFinished < 300000) && (millisUntilFinished > 209000)) {
                    notificationBuilder = new NotificationCompat.Builder(MainActivity.mContext);
                    notificationBuilder
                            .setLargeIcon(bitmap) // 이미지 띄워주기
                            .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                            .setContentTitle("5분 남았습니다")
                            .setContentText(charging) // 텍스트 띄우기
                            .setTicker("5분 남았습니다") // 상태 바에 뜨는 문구
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true);

                    notificationManager.notify(1, notificationBuilder.build());
                }
                MainActivity.mainTxtView.setText(millisUntilFinished / 1000 / 60 / 60 + ":" + millisUntilFinished / 1000 / 60 % 60 + ":" + millisUntilFinished / 1000 % 60);
                MainActivity.overChargingView.setText(charging);
                if (MainActivity.mode == MainActivity.returned) {
                    timer.cancel();
                    initData();
                }
            }
            @Override
            public void onFinish() {
                switch (MainActivity.mode){
                    case 3: // firstOver
                        MainActivity.mode = MainActivity.secondOver;
                        timer.cancel();
                        charging = "과금 2000원";
                        timeOver();
                        break;
                    case 4: // secondOver
                        MainActivity.mode = MainActivity.thirdOver;
                        timer.cancel();
                        charging = "과금 3000원";
                        timeOver();
                        break;
                    case 5: // thirdOver
                        MainActivity.mode = MainActivity.fourthOver;
                        timer.cancel();
                        charging = "분실됨";
                        break;
                    default:
                        initData();
                        break;
                }
            }
        }.start();
    }

    public void initData(){
        MainActivity.mainTxtView.setTextColor(Color.parseColor("#000000"));
        MainActivity.mainTxtView.setText("대여하세요");
        MainActivity.overChargingView.setText("");
        MainActivity.mode = MainActivity.nonRent;
    }
}
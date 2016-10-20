package ensharp.ttalawa;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.InputStream;

import ensharp.ttalawa.DBAdapter.SpotsDbAdapter;
import ensharp.ttalawa.DBAdapter.StationDbAdapter;
import jxl.Sheet;
import jxl.Workbook;


/**
 * Created by Semin on 2016-09-28.
 */
public class SplashActivity extends Activity {

    /* 스플래쉬 화면이 뜨는 시간 */
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private StationDbAdapter dbAdapter;
    private SpotsDbAdapter spotDbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.dbAdapter = new StationDbAdapter(this);
        //엑셀파일 데이터를 데이터베이스에 저장
        dbAdapter.open();
        Cursor temp = dbAdapter.fetchAllStations();
        Log.w("testing!getCount!", String.valueOf(temp.getCount()));

        if (temp.getCount() == 0)
            copyExcelDataToDatabase();
        dbAdapter.close();

        this.spotDbAdapter = new SpotsDbAdapter(this);

        //엑셀파일 데이터를 데이터베이스에 저장
        spotDbAdapter.open();
        temp = spotDbAdapter.fetchAllSpots();
        Log.w("testing!getCount!", String.valueOf(temp.getCount()));

        if (temp.getCount() == 0)
            copyExcelDataToDbSpot();
        spotDbAdapter.close();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void copyExcelDataToDatabase() {
        Log.w("ExcelToDatabase", "copyExcelDataToDatabase()");

        Workbook workbook = null;
        Sheet sheet = null;

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("RentalStationSeoul.xls");
            workbook = Workbook.getWorkbook(is);

            if (workbook != null) {

                sheet = workbook.getSheet(0);

                if (sheet != null) {

                    int nMaxColumn = 2;
                    int nRowStartIndex = 0;
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0;
                    int nColumnEndIndex = sheet.getRow(2).length - 1;

                    dbAdapter.open();
                    for (int nRow = nRowStartIndex + 2; nRow <= nRowEndIndex; nRow++) {
                        String content_name = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String addr_gu = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                        String new_addr = sheet.getCell(nColumnStartIndex + 2, nRow).getContents();
                        String coordinate_x = sheet.getCell(nColumnStartIndex + 3, nRow).getContents();
                        String coordinate_y = sheet.getCell(nColumnStartIndex + 4, nRow).getContents();
                        String content_num = sheet.getCell(nColumnStartIndex + 5, nRow).getContents();
                        String rack_count = sheet.getCell(nColumnStartIndex + 6, nRow).getContents();

                        dbAdapter.createStation(content_name, addr_gu, new_addr, coordinate_x, coordinate_y, content_num, rack_count);
                    }
                    dbAdapter.close();

                } else {
                    System.out.println("Sheet is null!!");
                }
            } else {
                System.out.println("WorkBook is null!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    private void copyExcelDataToDbSpot() {
        Log.w("ExcelToDatabase", "copyExcelDataToDatabase()");

        Workbook workbook = null;
        Sheet sheet = null;

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("TourSpot_DB.xls");
            workbook = Workbook.getWorkbook(is);

            if (workbook != null) {

                sheet = workbook.getSheet(0);

                if (sheet != null) {

                    int nMaxColumn = 2;
                    int nRowStartIndex = 0;
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0;
                    int nColumnEndIndex = sheet.getRow(2).length - 1;

                    spotDbAdapter.open();
                    for (int nRow = nRowStartIndex + 1; nRow <= nRowEndIndex; nRow++) {
                        String spotMapX = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String spotMapY = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                        String spotTitle = sheet.getCell(nColumnStartIndex + 2, nRow).getContents();
                        String spotAddress = sheet.getCell(nColumnStartIndex + 3, nRow).getContents();

                        spotDbAdapter.createSpot(spotMapX, spotMapY, spotTitle, spotAddress);
                    }
                    spotDbAdapter.close();

                } else {
                    System.out.println("Sheet is null!!");
                }
            } else {
                System.out.println("WorkBook is null!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

    }

}

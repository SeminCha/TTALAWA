package ensharp.ttalawa;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by mh on 2016-08-01.
 */
public class MmsAccesService extends AccessibilityService {

    static final String TAG = "MmsAccesService";
    private AsyncTask restTimeTask;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.v(TAG, getEventText(accessibilityEvent));
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.v(TAG, "onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }

    // Status Bar Notification 값 String으로 읽어오는 Function
    private String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        // 가져온 sb에 따릉이라는 문자가 포함되어있나?
        if (sb.toString().contains("<따릉이>") && sb.toString().contains("대여완료")) {
            if(MainActivity.mode != MainActivity.rent) {
                MainActivity.mode = MainActivity.rent;
                restTimeTask = new RestTimeTask().execute();
            }
        } else if (sb.toString().contains("<따릉이>") && sb.toString().contains("반납되었습니다")) {
            MainActivity.mode = MainActivity.returned;
        }
        return sb.toString();
    }
}
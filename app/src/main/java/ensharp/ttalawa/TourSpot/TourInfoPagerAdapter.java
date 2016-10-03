package ensharp.ttalawa.TourSpot;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TourInfoPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TourInfoPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TourInfoTab_Frag1 tab1 = new TourInfoTab_Frag1();
                return tab1;
            case 1:
                TourInfoTab_Frag2 tab2 = new TourInfoTab_Frag2();
                return tab2;
            case 2:
                TourInfoTab_Frag3 tab3 = new TourInfoTab_Frag3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
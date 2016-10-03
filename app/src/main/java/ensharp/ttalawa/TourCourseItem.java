package ensharp.ttalawa;

/**
 * Created by Moon on 2015-03-03.
 */
public class TourCourseItem extends TourAdapterItem {

    public TourCourseItem(String course) {
        super(course);
    }

//    public TourCourseItem(int year, int month, int dayOfMonth) {
//        super(year, month, dayOfMonth);
//    }

    @Override
    public int getType() {
        return TYPE_COURSE;
    }
}

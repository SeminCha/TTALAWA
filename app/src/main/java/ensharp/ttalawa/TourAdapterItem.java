package ensharp.ttalawa;

/**
 * Created by Moon on 2015-03-02.
 */
public abstract class TourAdapterItem {
    public static final int TYPE_COURSE = 1;
    public static final int TYPE_SPOT = 2;

    private String course;

    public TourAdapterItem(String course) {
        this.course = course;
    }

//    public TourAdapterItem(int year, int month, int dayOfMonth) {
//        setTime(year, month, dayOfMonth);
//    }

    public void setCourse(String course) {
        this.course = course;
    }

//    public void setTime(int year, int month, int dayOfMonth) {
//        Calendar cal = Calendar.getInstance();
//        cal.clear();
//        cal.set(year, month-1, dayOfMonth);
//        course = cal.getTimeInMillis();
//    }

//    public int getYear() {
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(course);
//        return cal.get(Calendar.YEAR);
//    }
//
//    public int getMonth() {
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(course);
//        return cal.get(Calendar.MONTH) + 1;
//    }
//
//    public int getDayOfMonth() {
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(course);
//        return cal.get(Calendar.DAY_OF_MONTH);
//    }

    public String getCourse() {
        return course;
    }

    public String getCourseToString() {
        return getCourse();
    }

    public abstract int getType();

}

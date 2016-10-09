package ensharp.ttalawa.TourSpot;

/**
 * Created by Moon on 2015-03-02.
*/
public class TourData extends TourAdapterItem {
    private String spotName;
    private String temp;

    public TourData(String spotName, String course, String temp) {
        super(course);
        this.spotName = spotName;
        this.temp = temp;
    }

    @Override
    public int getType() {
        return TYPE_SPOT;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getTemp() { return temp; }
}

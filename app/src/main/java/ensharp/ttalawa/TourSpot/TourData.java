package ensharp.ttalawa.TourSpot;

/**
 * Created by Moon on 2015-03-02.
 */
public class TourData extends TourAdapterItem {
    private String spotName;

    public TourData(String spotName, String course) {
        super(course);
        this.spotName = spotName;
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
}

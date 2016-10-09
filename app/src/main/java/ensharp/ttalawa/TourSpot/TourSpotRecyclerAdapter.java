package ensharp.ttalawa.TourSpot;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ensharp.ttalawa.R;


public class TourSpotRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TourAdapterItem> itemList;
    private OnItemClickListener listener;


    //코스 뷰어
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView courseTitleView;

        public CourseViewHolder(View v) {
            super(v);
            courseTitleView = (TextView) v.findViewById(R.id.courseTitleView);
        }
    }

    //관광지 데이터 뷰어(관광지명 + 인증여부 + 버튼)
    public static class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView spotItemView;  //관광지명
        public Button tourspotCheckBtn; //버튼

        private OnViewHolderClickListener listener;

        public TextView checkTour;



        public DataViewHolder(View v, OnViewHolderClickListener listener) {
            super(v);
            spotItemView = (TextView) v.findViewById(R.id.spotItemView);
            tourspotCheckBtn=(Button)v.findViewById(R.id.tourcheck_btn);
            tourspotCheckBtn.setOnClickListener(btnListener);

            checkTour =(TextView)v.findViewById(R.id.tempTxt);

            v.setOnClickListener(this);
            this.listener = listener;
        }
        Button.OnClickListener btnListener=new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener != null) {
                    Log.i("되냐고!!!",String.valueOf(getPosition()));
                    TourSpotListActivity.TempClass.checkPopUp(String.valueOf(getPosition()));
                }
            }
        };

        @Override
        public void onClick(View v) {
            if(listener != null)
                listener.onViewHolderClick(getPosition());
        }

        private interface OnViewHolderClickListener {
            void onViewHolderClick(int position);
        }
    }


    public TourSpotRecyclerAdapter(ArrayList<TourData> dataset) {
        itemList = initItemList(dataset);
    }

    private ArrayList<TourAdapterItem> initItemList(ArrayList<TourData> dataset) {
        ArrayList<TourAdapterItem> result = new ArrayList<>();

        String courseType="";

        for(TourData data:dataset) {
            if(courseType!=data.getCourse())
            {
                result.add(new TourCourseItem(data.getCourse()));
                courseType=data.getCourse();
            }
            result.add(data);
        }
        return result;
    }


    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TourAdapterItem.TYPE_COURSE)
            return new CourseViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recycler_item_course, parent, false));
        else
            return new DataViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recycler_item_spot, parent, false),
                    new DataViewHolder.OnViewHolderClickListener() {
                        @Override
                        public void onViewHolderClick(int position) {
                            if(listener != null)
                                listener.onItemClick(position);
                        }
                    }
            );
    }

    public CourseViewHolder tHolder;
    public DataViewHolder dHolder;

    //    리스트 뿌려주는 곳
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        if(holder instanceof CourseViewHolder) {
            tHolder = (CourseViewHolder) holder;
            tHolder.courseTitleView.setText(itemList.get(position).getCourseToString());
        }
        else if (holder instanceof DataViewHolder) {
            dHolder = (DataViewHolder) holder;
            dHolder.spotItemView.setText(
                    ((TourData)itemList.get(position))
                            .getSpotName());
            dHolder.checkTour.setText(((TourData)itemList.get(position)).getTemp());
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public TourData getItem(int position) {
        return (TourData)itemList.get(position);
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
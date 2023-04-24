package com.comp7506.customplus.UI.transportation.railway;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.comp7506.customplus.R;
import com.comp7506.customplus.UI.datamodel.RailwayData;
import com.comp7506.customplus.UI.tools.tools;

import java.util.List;

/**
 * @author chrisyuhimself@gmail.com
 * @date 4/22/2023 9:31 PM
 */
public class RailwayAdapter extends RecyclerView.Adapter<RailwayAdapter.ViewHolder> {
    private final List<RailwayData> railwayDataList;

    public RailwayAdapter(List<RailwayData> railwayDataList) {
        this.railwayDataList = railwayDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.railway_ticket_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RailwayData railwayData = railwayDataList.get(position);
        holder.mStartTime.setText(railwayData.getStartTime());
        holder.mArriveTime.setText(railwayData.getArriveTime());
        holder.mDurationTime.setText(railwayData.getDurationTime());
        holder.mTrainCode.setText(railwayData.getTrainCode());
        holder.mFromStation.setText(railwayData.getFromStation());
        holder.mToStation.setText(railwayData.getToStation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromStationBrowser = tools.codeToNameBrowserUrl(railwayData.getFromStation())[0];
                String fromStationCode = tools.codeToNameBrowserUrl(railwayData.getFromStation())[1];
                String date = tools.getTodayYYYYMMDD();
                String uri = String.format("https://www.12306.cn/en/left-ticket.html?fs=%s,%s&ts=Hkwestkowloon,XJA&date=%s&flag=G/C/D",
                        fromStationBrowser,fromStationCode, date);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                view.getContext().startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return railwayDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mStartTime;
        public TextView mArriveTime;
        public TextView mDurationTime;
        public TextView mTrainCode;
        public TextView mFromStation;
        public TextView mToStation;


        public ViewHolder(View itemView) {
            super(itemView);
            mFromStation = itemView.findViewById(R.id.from_station);
            mToStation = itemView.findViewById(R.id.to_station);
            mStartTime = itemView.findViewById(R.id.start_time);
            mArriveTime = itemView.findViewById(R.id.arrive_time);
            mDurationTime = itemView.findViewById(R.id.duration_time);
            mTrainCode = itemView.findViewById(R.id.train_code);
        }
    }
}

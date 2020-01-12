package com.peter.stchart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peter.stchart.R;
import com.peter.stchart.data.STChartData;

import java.util.ArrayList;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder>{

    private Context mContext;
    private List<STChartData> mData = new ArrayList<>();

    public DataListAdapter() {
    }

    public DataListAdapter(Context context, List<STChartData> dataList) {
        this.mContext = context;
        this.mData = dataList;
    }

    public List<STChartData> getDataList() {
        return mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        ViewHolder viewHolder = new ViewHolder(view, true);
        viewHolder.setIsRecyclable(false);
        return new ViewHolder(view, true);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if (mData != null && position < getItemCount()) {
            final STChartData stChartData = mData.get(position);
            final List<Boolean> booleanList = stChartData.getDataList();


            int minute = stChartData.getMinute();
            int datacount = stChartData.getDataCount();

            Log.d("DataListAdapter", "datacount : " + datacount);
            holder.tv_min.setText(String.valueOf(minute));

            holder.ll_container.removeAllViews();

            for (int i = 0; i < datacount; i++) {
                if (holder.ll_container.getChildCount() > datacount - 1) {
                    break;
                }
                final CheckBox checkBox = new CheckBox(mContext);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginStart(mContext.getResources().getDimensionPixelSize(R.dimen.x80));
                checkBox.setLayoutParams(layoutParams);
                checkBox.setTag(i);
                checkBox.setOnCheckedChangeListener(null);
                final int finalI = i;
                if (booleanList.size() > i) {
                    checkBox.setChecked(booleanList.get(i));
                } else {
                    booleanList.add(false);
                    checkBox.setChecked(false);
                }
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        booleanList.set(finalI, isChecked);
                    }
                });

                holder.ll_container.addView(checkBox);
            }

//            for (int i = 0; i < datacount; i++) {
//                Log.d("DataListAdapter",  "list index : " + i);
//                if (holder.ll_container.getChildCount() > datacount - 1) {
//                    CheckBox checkBox = (CheckBox) ((holder.ll_container.getChildAt(i)));
//                    checkBox.setOnCheckedChangeListener(null);
//                    Log.d("DataListAdapter",  "position : " + position + ",i : " + i + ", list size : " + booleanList.size());
//                    if (booleanList.size() > i) {
//                        checkBox.setChecked(booleanList.get(i));
//                    }
//                    final int finalI = i;
//                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            booleanList.set(finalI, isChecked);
//                        }
//                    });
////                    boolean result = booleanList.get(i);
////                    if (booleanList.size() <= i) {
//////                        booleanList.add(false);
////                    }else {
////                        ((CheckBox) (holder.ll_container.getChildAt(i))).setChecked(booleanList.get(i));
////                    }
//                }
//            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_min;
        private LinearLayout ll_container;

        public ViewHolder(View itemView, boolean isItem){
            super(itemView);
            if (isItem){
                tv_min = itemView.findViewById(R.id.item_min);
                ll_container = itemView.findViewById(R.id.item_container);
            }
        }
    }
}

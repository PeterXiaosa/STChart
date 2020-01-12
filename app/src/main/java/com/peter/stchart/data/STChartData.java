package com.peter.stchart.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class STChartData implements Parcelable {
    // 第几分钟的数据
    private int minute;
    // 这一分钟内数据的个数
    private int dataCount;
    // 具体数据，一分钟内数据为T，T 则列表为True, True
    private List<Boolean> dataList = new ArrayList<Boolean>();

    public STChartData() {
    }

    protected STChartData(Parcel in) {
        minute = in.readInt();
        dataCount = in.readInt();
    }

    public static final Creator<STChartData> CREATOR = new Creator<STChartData>() {
        @Override
        public STChartData createFromParcel(Parcel in) {
            return new STChartData(in);
        }

        @Override
        public STChartData[] newArray(int size) {
            return new STChartData[size];
        }
    };

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public List<Boolean> getDataList() {
        return dataList;
    }

    public void setDataList(List<Boolean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(minute);
        dest.writeInt(dataCount);
    }
}

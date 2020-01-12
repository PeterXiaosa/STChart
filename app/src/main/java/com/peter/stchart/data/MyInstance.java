package com.peter.stchart.data;

import java.util.ArrayList;
import java.util.List;

public class MyInstance {

    private static final MyInstance ourInstance = new MyInstance();

    public static MyInstance getInstance() {
        return ourInstance;
    }

    private List<STChartData> list = new ArrayList<>();

    private MyInstance() {
    }



    public void setData(List<STChartData> list){
        this.list = list;
    }

    public List<STChartData> getData() {
        return list;
    }
}

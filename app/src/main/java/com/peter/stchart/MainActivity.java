package com.peter.stchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.peter.stchart.data.MyInstance;
import com.peter.stchart.data.STChartData;
import com.peter.stchart.view.STChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        STChart stChart = findViewById(R.id.stchart);
        List<STChartData> stChartDataList = MyInstance.getInstance().getData();

//        Bundle bundle = getIntent().getBundleExtra("chartdata");
//        stChartDataList = bundle.getParcelableArrayList("chartdata");

        int time = stChartDataList.size() - 1;
        int interval = 60 / stChartDataList.get(0).getDataCount();

        stChart.setTime(time);
        stChart.setInterval(interval);
        stChart.setDataList(stChartDataList);
        stChart.invalidate();
        Log.d("MainactivityTest", "stchart invalidate()");
    }
}

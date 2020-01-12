package com.peter.stchart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.stchart.adapter.DataListAdapter;
import com.peter.stchart.data.MyInstance;
import com.peter.stchart.data.STChartData;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    private DataListAdapter mAdapter ;

    RecyclerView recyclerView;
    EditText et_time;
    EditText et_interval;
    LinearLayout ll_container, ll_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        recyclerView = findViewById(R.id.recyclerview);
        et_time = findViewById(R.id.time);
        et_interval = findViewById(R.id.interval);
        ll_show = findViewById(R.id.ll_show);
        ll_container = findViewById(R.id.ll_container);

        Button btn_adddata = findViewById(R.id.btn_adddata);
        Button btn_submit = findViewById(R.id.btn_submit);

        btn_adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_time.getText().toString().contains("+")){
                    Toast.makeText(DataActivity.this, "输入格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_interval.getText().toString().contains("+")){
                    Toast.makeText(DataActivity.this, "输入格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                int interval = Integer.parseInt(et_interval.getText().toString().trim());
                if (interval == 0 || interval > 60) {
                    Toast.makeText(DataActivity.this, "间隔需要为0-60的5的倍数并且能整除60，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (interval % 5 != 0 && 60 % interval != 0) {
                    Toast.makeText(DataActivity.this, "间隔需要为0-60的5的倍数并且能整除60，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                initData();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText()
                List<STChartData> list = mAdapter.getDataList();
//                STChartData data = list.get(3);
//                List<Boolean> list1 = data.getDataList();
//                Toast.makeText(getApplicationContext(), String.valueOf(list1.get(1)), Toast.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("chartdata", (Parcelable) list);
                MyInstance.getInstance().setData(list);

                Intent intent = new Intent(DataActivity.this, MainActivity.class);
//                intent.putExtra("chartdata",bundle);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        int time = Integer.parseInt(et_time.getText().toString().trim());
        time++;
        int interval = Integer.parseInt(et_interval.getText().toString().trim());
        int datacount = 60 / interval;

        List<STChartData> list = new ArrayList<>();

        for (int i = 0; i < time; i++) {
            STChartData stChartData = new STChartData();
            stChartData.setMinute(i);
            stChartData.setDataCount(datacount);
            list.add(stChartData);
        }

        ll_container.removeAllViews();
        ll_show.setVisibility(View.VISIBLE);

        mAdapter = new DataListAdapter(DataActivity.this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        for (int i = 1; i <= datacount;i++) {
            final TextView textView = new TextView(this);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMarginStart(getResources().getDimensionPixelSize(R.dimen.x80));
            textView.setLayoutParams(layoutParams);
            textView.setText(i*interval + "秒");
            textView.setTextColor(getResources().getColor(R.color.black, null));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.x50));
            ll_container.addView(textView);
        }
    }
}

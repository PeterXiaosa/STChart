package com.peter.stchart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.peter.stchart.R;
import com.peter.stchart.data.STChartData;

import java.util.List;

public class STChart extends View {

    private int time, sampleInterval;
    private float mChartWidth, mChartHeight;
    private Context mContext;


    float YIntervalWidth;
    float xIntervalHeight;

    int paddintLeft;
    int paddintRight;
    int paddintTop;
    int paddintBottom;

    List<STChartData> dataList;

    Paint mAxisPaint =  new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mTextPaint =  new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mChartPaint =  new Paint(Paint.ANTI_ALIAS_FLAG);

    public STChart(Context context) {
        this(context, null);
    }

    public STChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public STChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public STChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.stchart);
        time = ta.getInteger(R.styleable.stchart_time, 45);
        sampleInterval = ta.getInteger(R.styleable.stchart_sameinterval, 30);

        mContext = context;

        initPaint();
    }

    private void initPaint() {
        paddintLeft = mContext.getResources().getDimensionPixelSize(R.dimen.x120);
        paddintRight = mContext.getResources().getDimensionPixelSize(R.dimen.x80);
        paddintTop = mContext.getResources().getDimensionPixelSize(R.dimen.y50);
        paddintBottom = mContext.getResources().getDimensionPixelSize(R.dimen.y120);

        mAxisPaint.setStrokeWidth(mContext.getResources().getDimension(R.dimen.x2));
        mAxisPaint.setColor(mContext.getResources().getColor(R.color.black, null));

        mTextPaint.setTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.x30));
        mTextPaint.setColor(mContext.getResources().getColor(R.color.black, null));

        mChartPaint.setStrokeWidth(mContext.getResources().getDimension(R.dimen.x4));
        mChartPaint.setColor(mContext.getResources().getColor(R.color.colorAccent, null));
    }

    public void setDataList(List<STChartData> stChartDataList) {
        this.dataList = stChartDataList;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setInterval(int interval) {
        this.sampleInterval = interval;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mChartHeight = MeasureSpec.getSize(heightMeasureSpec);
        mChartWidth = MeasureSpec.getSize(widthMeasureSpec);

        caculateDrawValue();
    }

    private void caculateDrawValue() {
        // 先计算每根竖轴之间的距离(宽度)
        YIntervalWidth = (mChartWidth - paddintLeft - paddintRight) / time;
        // 再计算每根横轴之间的距离(高度)
        xIntervalHeight = (mChartHeight - paddintTop - paddintBottom) / time;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAxis(canvas);

        if (dataList != null && dataList.size()!= 0) {
            drawChart(canvas);
        }
    }

    /**
     * 根据数据绘制图表
     * @param canvas
     */
    private void drawChart(Canvas canvas) {
        int size = dataList.size();

        float oldxPoint = 0;
        float oldyPoint = 0;
        float newxPoint = 0;
        float newyPoint = 0;

        for (int i = 0; i < size; i++) {
            STChartData stChartData = dataList.get(i);
            List<Boolean> data = stChartData.getDataList();
            // 几分之size的间隔(总体为1的话)
            float interval = (float) 1 / (float) (stChartData.getDataCount());

            for(int j = 0; j < data.size(); j++) {
                if (data.get(j)) {
                    newxPoint += interval;
                } else {
                    newyPoint += interval;
                }
                Log.d("STChart", "old point : " + oldxPoint + ", " + oldyPoint
                 + "new point : " + newxPoint + ", " + newyPoint);

                canvas.drawLine(paddintLeft + oldxPoint * YIntervalWidth,
                        mChartHeight - paddintBottom - oldyPoint * xIntervalHeight,
                        paddintLeft + newxPoint * YIntervalWidth,
                        mChartHeight - paddintBottom - newyPoint * xIntervalHeight, mChartPaint);

                oldxPoint = newxPoint;
                oldyPoint = newyPoint;
            }
        }
    }

    private void drawAxis(Canvas canvas) {
        // 先画横坐标轴
        for (int i = time; i >= 0; i --) {
            canvas.drawLine(paddintLeft, paddintTop + (time - i) * xIntervalHeight,
                    mChartWidth - paddintRight, paddintTop + (time - i) * xIntervalHeight, mAxisPaint);
        }

        // 再画纵坐标轴
        for (int j = 0; j <= time; j++) {
            canvas.drawLine(paddintLeft + YIntervalWidth * j, paddintTop,
                    paddintLeft + YIntervalWidth * j, mChartHeight - paddintBottom, mAxisPaint);
        }


        //绘制原点
        canvas.drawText("0",
                paddintLeft - mContext.getResources().getDimensionPixelSize(R.dimen.x25),
                mChartHeight - paddintBottom + mContext.getResources().getDimensionPixelSize(R.dimen.y25), mTextPaint);

        // 坐横标轴画点的数目， 5,10,15...
        int pointCouont = time / 5;
        for (int i = 1; i <= pointCouont; i++) {
            Rect rect = new Rect();
            mTextPaint.getTextBounds(String.valueOf(i * 5), 0, String.valueOf(i * 5).length(), rect);
            int textWidth = rect.width();

            canvas.drawText(String.valueOf(i * 5),
                    paddintLeft + i * 5 * YIntervalWidth - textWidth/2,
                    mChartHeight - paddintBottom + mContext.getResources().getDimensionPixelSize(R.dimen.y35),mTextPaint);

            int textHeight = rect.height();
            // 坐竖标轴画点的数目， 5,10,15...
            canvas.drawText(String.valueOf(i * 5),
                    paddintLeft - mContext.getResources().getDimensionPixelSize(R.dimen.x15) - textWidth,
                    mChartHeight - paddintBottom - i * 5 * xIntervalHeight + textHeight,mTextPaint);
        }

        String yString = "T轴 单位（分钟）";
        Rect yrect = new Rect();
        mTextPaint.getTextBounds(yString, 0, yString.length(), yrect);
        int textWidth = yrect.width();

        float realChartWidth = mChartWidth - paddintLeft - paddintRight;
        canvas.drawText(yString, paddintLeft + realChartWidth/2 - (float) textWidth/2,
                mChartHeight - mContext.getResources().getDimensionPixelSize(R.dimen.y50), mTextPaint);

        String xString = "S轴 单位（分钟）";
        Rect xrect = new Rect();
        mTextPaint.getTextBounds(xString, 0, xString.length(), xrect);
        int xtextWidth = xrect.width();
        canvas.rotate(-90);
        float realChartHeight = mChartHeight - paddintTop - paddintBottom;
        canvas.drawText(xString, -paddintTop - realChartHeight /2 - (float)xtextWidth /2,
                mContext.getResources().getDimensionPixelSize(R.dimen.x50), mTextPaint);

        canvas.rotate(90);
    }
}

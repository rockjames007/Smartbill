package com.example.dipuj.smartbill.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dipuj.smartbill.R;
import com.example.dipuj.smartbill.graph.XDataPlotter;
import com.example.dipuj.smartbill.modal.Reading;
import com.example.dipuj.smartbill.view.VerticalTextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphFragment extends Fragment
{

    private ArrayList<Reading> mBarChartDataArray=new ArrayList<>();

    private BarChart mBarChart;

    private VerticalTextView mTxtYAxis;

    private Context mContext;

    private ArrayList<String> mArrayBar=new ArrayList<>();

    private  ArrayList<BarEntry> mArrayBarEntry=new ArrayList<>();

    private final static int TYPE_PRESSURE=0;

    private final static int TYPE_TEMPERATURE=1;

    private final static int TYPE_JOINTTIME=2;

    private final static int TXT_SIZE = 18;

    private HashMap<String, ArrayList<Reading>> childList;

    private ArrayList<String> headerList;

    /**
     * Default empty constructor.
     */
    public GraphFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        childList = new HashMap<>();
        Bundle b = this.getArguments();
        if(b != null && b.getSerializable("childList") != null)
            childList = (HashMap<String, ArrayList<Reading>>)b.getSerializable("childList");
        Log.e("Childlist",childList.toString());
        if(b != null && b.getSerializable("headerList") != null)
            headerList = (ArrayList<String>)b.getSerializable("headerList");
        Log.e("Headerlist",headerList.toString());

        return inflater.inflate(R.layout.fragment_graph,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

        initView(view);

        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context!=null)
        {
            mContext=context;
        }
    }

    /**
     *  Initialize view like chart, radio buttons.
     * @param view Root View of Fragment from which we can fetch it's children
     */
    private void initView(View view)
    {

        //mArrayLogFiles=getArguments().getParcelableArrayList(Constants.KEY_LOGFILES);

        mBarChart=(BarChart)view.findViewById(R.id.bar_chart);

        initBarConfig();

        mTxtYAxis= view.findViewById(R.id.txt_chart_yaxis);

        // setInFillPressure(TYPE_PRESSURE);

        fetchDataFromLogFiles();

    }

    /**
     * Initialize bar configuration like touch, drag, size etc.
     */
    private void initBarConfig()
    {


        // enable touch gestures
        mBarChart.setTouchEnabled(true);

        // enable scaling and dragging
        mBarChart.setDragEnabled(true);
        mBarChart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);

        mBarChart.setDoubleTapToZoomEnabled(false);

        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.getAxisLeft().setAxisLineColor(Color.WHITE);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.getXAxis().setDrawGridLines(false);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setTextSize(TXT_SIZE);

        YAxis yAxis=mBarChart.getAxisLeft();
        yAxis.setTextSize(TXT_SIZE);

    }


    /**
     * Generate graph according to selected radio button.
     * @param mType graph type
     */
    private void setInFillPressure(int mType)
    {

        mArrayBarEntry.clear();

        mArrayBar.clear();

        mTxtYAxis.setText("Reading value");

        int index =0;

        float max = 1000;

        for(int i=0; i<headerList.size();i++){
            for(int j=0; j<childList.get(headerList.get(i)).size();j++)
            {
                Reading mData = childList.get(headerList.get(i)).get(j);
                float yAxisValue=0;
                yAxisValue=mData.getReading();

                if(max<(float)mData.getReading()){
                    max = (float)mData.getReading();
                }

                BarEntry mBarEntry=new BarEntry(index,yAxisValue);

                mArrayBarEntry.add(mBarEntry);
                String fDate = new SimpleDateFormat("yyyy-MM-dd").format(mData.getTimestamp());
                mArrayBar.add(fDate);

                index++;
            }
        }

        final float MIN_VALUE = 0;
        final float MAX_VALUE = (max*2);

        mBarChart.getAxisLeft().setAxisMinimum(MIN_VALUE);
        mBarChart.getAxisLeft().setAxisMaximum(MAX_VALUE);

        mBarChart.getXAxis().setValueFormatter(new XDataPlotter(mArrayBar));

        mBarChart.setPinchZoom(false);

        setBarChartData();

    }


    /**
     * Set graph axis value and it's configuration.
     */
    private void setBarChartData()
    {

        final int VISIBLE_MAX_RANGE = 6;

        mBarChart.clear();

        BarDataSet mBarDataSet=new BarDataSet(mArrayBarEntry,"");

        mBarChart.getLegend().setEnabled(false);

        mBarDataSet.setColor(mContext.getResources().getColor(R.color.colorAccent));

        mBarChart.setExtraBottomOffset(5);

        mBarDataSet.setLabel("");

        mBarDataSet.setValueTextSize(TXT_SIZE);

        BarData mBarData=new BarData();

        mBarData.addDataSet(mBarDataSet);

        mBarChart.setDrawValueAboveBar(true);

        mBarChart.setData(mBarData);

        mBarChart.getXAxis().setGranularity(1F);

        mBarChart.getXAxis().setGranularityEnabled(true);

        if(mArrayBarEntry.size()>= VISIBLE_MAX_RANGE)
        {

            mBarChart.setVisibleXRangeMaximum(VISIBLE_MAX_RANGE);

            mBarData.setBarWidth(0.08f*VISIBLE_MAX_RANGE);

        }else
        {
            if(mArrayBarEntry.size()==1)
            {
                mBarChart.getXAxis().setLabelCount(0);
            }else{
                mBarChart.getXAxis().setLabelCount(mArrayBarEntry.size());
            }

            mBarChart.setVisibleXRangeMaximum(mArrayBarEntry.size());

            mBarData.setBarWidth(0.08f*mArrayBarEntry.size());
        }
    }

    private void fetchDataFromLogFiles()
    {
        setInFillPressure(TYPE_PRESSURE);
    }
}
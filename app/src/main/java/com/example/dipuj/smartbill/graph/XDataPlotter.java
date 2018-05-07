package com.example.dipuj.smartbill.graph;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class XDataPlotter implements IAxisValueFormatter {


    private ArrayList<String> mArrayJointIds=new ArrayList<>();

    /**
     * Initialize data.
     * @param mJointIds array list joint id
     */
    public XDataPlotter(ArrayList<String> mJointIds)
    {

        mArrayJointIds=mJointIds;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis)
    {


        return mArrayJointIds.get((int)value);
    }


}

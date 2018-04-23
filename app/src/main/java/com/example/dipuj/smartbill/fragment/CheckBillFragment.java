package com.example.dipuj.smartbill.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dipuj.smartbill.R;
import com.example.dipuj.smartbill.activity.UserActivity;
import com.example.dipuj.smartbill.modal.Reading;
import com.example.dipuj.smartbill.modal.User;
import com.example.dipuj.smartbill.utility.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CheckBillFragment extends Fragment {

    private final String TAG = "CheckBillFragment";

    private Context context;

    private User user;

    private TextView mTextViewName;
    private TextView mTextViewMeterId;
    private TextView mTextViewPreviousReading;
    private TextView mTextViewCurrentReading;
    private TextView mTextViewUnitConsumed;
    private TextView mTextViewBillAmount;
    private TextView mTextViewDueDate;

    private Spinner mSpinnerMonth;
    private int index = 0;

    private ArrayList<String> headerList;
    private ArrayList<Map<String, Object>> reading = new ArrayList<>();
    private Reading previousReading;
    private Reading currentReading;

    private long consumedUnits;
    private Date dueDate;
    private double billAmount = 0.0;

    public CheckBillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_bill, container, false);
        reading = ((UserActivity) getActivity()).getReading();
        user = ((UserActivity) getActivity()).getUser();
        initializeView(view);
        initializeMonthSpinner(view);
        getData();
        return view;
    }

    private void initializeView(View view) {
        mTextViewName = view.findViewById(R.id.txt_name);
        mTextViewMeterId = view.findViewById(R.id.txt_meter_id);
        mTextViewPreviousReading = view.findViewById(R.id.txt_previous_reading);
        mTextViewCurrentReading = view.findViewById(R.id.txt_current_reading);
        mTextViewUnitConsumed = view.findViewById(R.id.txt_unit_consumed);
        mTextViewBillAmount = view.findViewById(R.id.txt_bill_amount);
        mTextViewDueDate = view.findViewById(R.id.txt_bill_due_date);
    }

    private void initializeMonthSpinner(View view) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, Constant.months);

        mSpinnerMonth = view.findViewById(R.id.spinner_month);
        mSpinnerMonth.setAdapter(adapter);
        mSpinnerMonth.setSelection(0);

        mSpinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    index = position;
                    getData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //nothing to do.
            }
        });
    }

    private void getData() {
        for (int i = 0; i < reading.size(); i++) {
            if (index == i) {
                Log.e(TAG, "Index taken : " + index);
                Map<String, Object> map;
                map = reading.get(i);
                Map<String, Object> treeMap = new TreeMap<>(map);
                headerList = new ArrayList<>(treeMap.keySet());
                Object[] arr = treeMap.values().toArray();
                ArrayList<Object> readingObList;

                readingObList = (ArrayList<Object>) arr[0];
                Object previousObject = readingObList.get(0);
                previousReading = new Reading();
                HashMap<String, Object> previousHashMap = (HashMap<String, Object>) previousObject;
                previousReading.setReading((Long) previousHashMap.get(Constant.KEY_READING));
                previousReading.setTimestamp((Date) previousHashMap.get(Constant.KEY_TIME_STUMP));
                Log.e(TAG, "date : " + previousReading.getTimestamp().toString());

                readingObList = (ArrayList<Object>) arr[arr.length - 1];
                Object currentObject = readingObList.get(0);
                currentReading = new Reading();
                HashMap<String, Object> currentHashMap = (HashMap<String, Object>) currentObject;
                currentReading.setReading((Long) currentHashMap.get(Constant.KEY_READING));
                currentReading.setTimestamp((Date) currentHashMap.get(Constant.KEY_TIME_STUMP));
                Log.e(TAG, "date : " + previousReading.getTimestamp().toString());
            }
        }

        calculateBill();
    }

    private void calculateBill() {


        getDueDate(currentReading.getTimestamp());

        consumedUnits = currentReading.getReading() - previousReading.getReading();

        if (consumedUnits > 200) {
            billAmount = billAmount + ((consumedUnits - 200) * 0.690);
            consumedUnits = consumedUnits -200;
        }
        if (consumedUnits <= 200 && consumedUnits > 100) {
            billAmount = billAmount + ((consumedUnits - 100) * 0.590);
            consumedUnits = consumedUnits -100;
        }
        if (consumedUnits <= 100 && consumedUnits > 30) {
            billAmount = billAmount + ((consumedUnits - 30) * 0.440);
            consumedUnits = consumedUnits -30;
        }
        if (consumedUnits < 30 && consumedUnits > 0) {
            billAmount = billAmount + ((consumedUnits) * 0.300);
        }

        displayData();


    }

    private void getDueDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // manipulate date
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.HOUR, 1);
        c.add(Calendar.MINUTE, 1);
        c.add(Calendar.SECOND, 1);

        // convert calendar to date
        dueDate = c.getTime();
    }

    private void displayData(){
        mTextViewName.setText(user.getName());
        mTextViewMeterId.setText(user.getMeterId());
        mTextViewPreviousReading.setText(previousReading.getReading()+"");
        mTextViewCurrentReading.setText(currentReading.getReading()+"");
        mTextViewUnitConsumed.setText(consumedUnits + "");
        mTextViewBillAmount.setText(billAmount + "");
        mTextViewDueDate.setText(dueDate.toString());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

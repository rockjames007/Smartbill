package com.example.dipuj.smartbill.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.dipuj.smartbill.R;
import com.example.dipuj.smartbill.activity.UserActivity;
import com.example.dipuj.smartbill.adapter.ReadingExpandableListAdapter;
import com.example.dipuj.smartbill.modal.Reading;
import com.example.dipuj.smartbill.utility.Constant;
import com.example.dipuj.smartbill.utility.Pref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UsageFragment extends Fragment {

    private final String TAG = "UsageFragment";

    private Spinner mSpinnerMonth;
    private FloatingActionButton mFloatingActionButton;

    private ExpandableListView mExpandableListViewReading;
    private ReadingExpandableListAdapter mReadingExpandableListAdapter;
    private ArrayList<String> headerList;
    private HashMap<String, ArrayList<Reading>> childList;
    private ArrayList<Map<String, Object>> reading = new ArrayList<>();

    int index = 0;

    private Context context;

    public UsageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usage, container, false);
        initializeFloatingActionButton(view);
        initializeMonthSpinner(view);
        initializeExpandableListView(view);
        reading = ((UserActivity) getActivity()).getReading();

        loadData();

        //Log.i(TAG, "Fetched Data : " + reading.toString());
        return view;
    }

    private void initializeExpandableListView(View view){
        mExpandableListViewReading = view.findViewById(R.id.expandable_reading);
    }

    private void loadData(){

        for (int i = 0; i < reading.size(); i++) {
            if (index == i) {
                Log.e(TAG, "Index taken : " + index);
                Map<String, Object> map;
                map = reading.get(i);
                Map<String, Object> treeMap = new TreeMap<>(map);
                headerList = new ArrayList<>(treeMap.keySet());
                childList = new HashMap<>();
                Object[] arr = treeMap.values().toArray();
                ArrayList<Object> readingObList;
                for (int j = 0; j < headerList.size(); j++) {
                    readingObList = (ArrayList<Object>) arr[j];
                    ArrayList<Reading> readingList = new ArrayList<>();
                    for (Object ob : readingObList) {
                        Reading reading = new Reading();
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) ob;
                        reading.setReading((Long) hashMap.get(Constant.KEY_READING));
                        reading.setTimestamp((Date) hashMap.get(Constant.KEY_TIME_STUMP));
                        Log.e(TAG, "date : " + reading.getTimestamp().toString());
                        readingList.add(reading);
                    }
                    childList.put(headerList.get(j), readingList);
                }
            }
        }

        mReadingExpandableListAdapter = new ReadingExpandableListAdapter(
                context,headerList,childList);

        mExpandableListViewReading.setAdapter(mReadingExpandableListAdapter);
        mReadingExpandableListAdapter.notifyDataSetChanged();

    }

    private void initializeFloatingActionButton(View view){
        mFloatingActionButton = view.findViewById(R.id.fab_chart);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, new GraphFragment()).commit();
            }
        });
    }

    private void initializeMonthSpinner(View view) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, Constant.months);

        mSpinnerMonth = view.findViewById(R.id.spinner_month);
        mSpinnerMonth.setAdapter(adapter);
        mSpinnerMonth.setSelection(0);

        mSpinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position != 0)
                {
                    index = position;
                    loadData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                //nothing to do.
            }
        });
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

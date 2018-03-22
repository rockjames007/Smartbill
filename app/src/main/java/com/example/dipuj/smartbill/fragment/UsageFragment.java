package com.example.dipuj.smartbill.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.dipuj.smartbill.R;
import com.example.dipuj.smartbill.modal.Reading;
import com.example.dipuj.smartbill.utility.Constant;
import com.example.dipuj.smartbill.utility.Pref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UsageFragment extends Fragment {

    private final String TAG = "UsageFragment";
    FirebaseFirestore dataBase;
    Spinner mSpinnerYear;
    ArrayList<ArrayList<List<Reading>>> reading = new ArrayList<>();
    ArrayList<List<Reading>> monthData = null;
    List<Reading> weekData = null;

    private Context context;

    public UsageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFirebaseFirestore();

        retrieveFireBaseData(getPath());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usage, container, false);
        initializeSpinner(view);

        Log.i(TAG, "Feched Data : " + reading.toString());
        return view;
    }

    private void initializeSpinner(View view) {
        ArrayList<String> years = new ArrayList();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2015; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, years);

        mSpinnerYear = (Spinner) view.findViewById(R.id.spinner_year);
        mSpinnerYear.setAdapter(adapter);
        mSpinnerYear.setSelection(years.size() - 1);
    }

    private void initializeFirebaseFirestore() {

        dataBase = FirebaseFirestore.getInstance();
    }

    private void retrieveFireBaseData(final String path) {

        for (final String month : Constant.months) {
            DocumentReference documentReference = dataBase
                    .collection(path)
                    .document(month);
            documentReference.get().addOnCompleteListener(
                    new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (doc.exists()) {
                                    monthData = new ArrayList<>();
                                    for (String week : Constant.weeks) {
                                        weekData = new ArrayList<>();
                                        for (int i = 1; i <= 7; i++) {
                                            DocumentReference innerDocumentReference =
                                                    dataBase
                                                    .collection(path)
                                                    .document(month)
                                                    .collection(week)
                                                    .document(i + "");

                                            final int finalI = i;
                                            innerDocumentReference.get().
                                                    addOnCompleteListener(
                                                    new OnCompleteListener<DocumentSnapshot>()
                                                    {

                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> innerTask) {
                                                            if (innerTask.isSuccessful()) {
                                                                DocumentSnapshot innerDoc = innerTask.getResult();
                                                                if (innerDoc.exists()) {
                                                                    Reading reading = new Reading(
                                                                            innerDoc.get(Constant.KEY_READING),
                                                                            innerDoc.get(Constant.KEY_TIME_STUMP));
                                                                    weekData.add(reading);
                                                                }
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                        }
                                    }
                                    monthData.add(weekData);
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        reading.add(monthData);
    }

    private String getPath() {

        String year = (mSpinnerYear != null && mSpinnerYear.getSelectedItem().toString() != null)
                ? mSpinnerYear.getSelectedItem().toString() : "2018";

        String path = "meterDetails/" +
                Pref.getValue(context, Constant.KEY_METER_ID, "",
                        Constant.PREF_NAME) + "/" + year;

        Log.d(TAG, "path : " + path);

        return path;
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

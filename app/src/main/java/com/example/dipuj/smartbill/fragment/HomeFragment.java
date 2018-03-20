package com.example.dipuj.smartbill.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dipuj.smartbill.R;
import com.example.dipuj.smartbill.modal.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";
    FirebaseFirestore dataBase;
    private User user;
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private TextView mTextViewMobileNumber;
    private TextView mTextViewAddress;
    private TextView mTextViewMeterId;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeFirebaseFirestore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeView(view);
        this.retrieveFireBaseData();
        return view;
    }

    private void initializeView(View view) {
        mTextViewName = (TextView)view.findViewById(R.id.txt_name);
        mTextViewEmail = (TextView)view.findViewById(R.id.txt_email);
        mTextViewMobileNumber = (TextView)view.findViewById(R.id.txt_mobile_number);
        mTextViewAddress = (TextView)view.findViewById(R.id.txt_address);
        mTextViewMeterId = (TextView)view.findViewById(R.id.txt_meter_id);
    }

    private void initializeFirebaseFirestore() {

        dataBase = FirebaseFirestore.getInstance();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User();
        user.setUserId(currentFirebaseUser.getUid());
    }

    private void retrieveFireBaseData() {
        DocumentReference documentReference = dataBase.collection("users").document(user.getUserId());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    user.setName(doc.getString("name"));
                    user.setAddress(doc.getString("address"));
                    user.setMobileNo(doc.getString("mobileno"));
                    user.setEmail(doc.getString("email"));
                    user.setMeterId(doc.getString("meterid"));
                    Log.d(TAG, "Fetched Data : " + user.toString());

                    updateView(user);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void updateView(User user){
        mTextViewName.setText(user.getName());
        mTextViewMeterId.setText(user.getMeterId());
        mTextViewEmail.setText(user.getEmail());
        mTextViewMobileNumber.setText(user.getMobileNo());
        mTextViewAddress.setText(user.getAddress());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

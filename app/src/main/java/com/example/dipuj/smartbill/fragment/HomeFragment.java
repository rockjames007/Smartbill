package com.example.dipuj.smartbill.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dipuj.smartbill.R;

public class HomeFragment extends Fragment {

    private ImageView mImageViewUsage;
    private ImageView mImageViewUserDetails;
    private ImageView mImageViewBill;
    private ImageView mImageViewHelp;

    private Context mContext;


    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        mImageViewUsage = view.findViewById(R.id.image_usage);
        mImageViewUserDetails= view.findViewById(R.id.image_user_detail);
        mImageViewBill = view.findViewById(R.id.image_bill);
        mImageViewHelp = view.findViewById(R.id.image_help);

        mImageViewUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new UsageFragment());
            }
        });

        mImageViewUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new UserDetailsFragment());
            }
        });

        mImageViewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new CheckBillFragment());
            }
        });

        mImageViewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new HelpFragment());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadFragment(Fragment mFragment){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, mFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

}

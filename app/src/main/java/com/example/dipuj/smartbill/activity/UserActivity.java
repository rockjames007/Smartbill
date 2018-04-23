package com.example.dipuj.smartbill.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dipuj.smartbill.R;
import com.example.dipuj.smartbill.fragment.CheckBillFragment;
import com.example.dipuj.smartbill.fragment.LimitFragment;
import com.example.dipuj.smartbill.fragment.UsageFragment;
import com.example.dipuj.smartbill.fragment.UserDetailsFragment;
import com.example.dipuj.smartbill.modal.Reading;
import com.example.dipuj.smartbill.modal.User;
import com.example.dipuj.smartbill.utility.Constant;
import com.example.dipuj.smartbill.utility.Pref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "UserActivity";
    private User user;
    private ProgressBar mProgressBar;
    private CardView mCardViewProgress;
    private Spinner mSpinnerYear;
    private TextView mTextViewName;
    private TextView mTextViewEmail;

    private FirebaseFirestore dataBase;

    private ArrayList<Map<String, Object>> reading = new ArrayList<>();

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeFirebaseFirestore();

        setContentView(R.layout.activity_user);

        initializeProgressBar();
        initializeYearSpinner();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User();
        user.setUserId(currentFirebaseUser.getUid());
        Pref.setValue(getApplicationContext(), Constant.KEY_USER_ID, user.getUserId(), Constant.PREF_NAME);

        retrieveFireBaseUserData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        mTextViewName = headerView.findViewById(R.id.text_user_name);
        mTextViewEmail = headerView.findViewById(R.id.text_user_email);
    }

    private void initializeUserDetailFragment() {

            mTextViewName.setText(user.getName());
            mTextViewEmail.setText(user.getEmail());

            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = UserDetailsFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = new Fragment();
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            fragmentClass = UserDetailsFragment.class;
        } else if (id == R.id.nav_usage) {
            fragmentClass = UsageFragment.class;
        } else if (id == R.id.nav_limit) {
            fragmentClass = LimitFragment.class;
        } else if (id == R.id.nav_alert) {
            fragmentClass = CheckBillFragment.class;
        } else if (id == R.id.nav_help) {
            fragmentClass = UsageFragment.class;
        } else if (id == R.id.nav_aboutus) {
            fragmentClass = UsageFragment.class;
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initializeYearSpinner() {
        ArrayList<String> years = new ArrayList();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2015; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, years);

        mSpinnerYear = findViewById(R.id.spinner_year);
        mSpinnerYear.setAdapter(adapter);
        mSpinnerYear.setSelection(years.size() - 1);
    }

    private void initializeProgressBar() {
        mCardViewProgress = findViewById(R.id.card_google_progress);
        mProgressBar = findViewById(R.id.google_progress);
        mProgressBar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(getApplicationContext())
                .build());
    }

    private void initializeFirebaseFirestore() {

        dataBase = FirebaseFirestore.getInstance();
        user = new User();
        user.setUserId(Pref.getValue(getApplicationContext(),Constant.KEY_USER_ID,"",Constant.PREF_NAME));
    }

    private void retrieveFireStoreMeterData(final String path) {

        Log.e(TAG, "index : " + index);

        mCardViewProgress.bringToFront();
        mCardViewProgress.setVisibility(View.VISIBLE);

        DocumentReference documentReference = dataBase
                .collection(path)
                .document(Constant.months[index]);

        documentReference.get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()) {
                                Map<String, Object> map = doc.getData();
                                reading.add(map);
                            } else {
                                reading.add(null);
                            }
                        }
                        index++;

                        if (index < 12) {
                            retrieveFireStoreMeterData(path);
                        } else {
                            index = 0;
                            mCardViewProgress.setVisibility(View.INVISIBLE);
                            initializeUserDetailFragment();
                            Log.e(TAG, reading.toString());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private String getPath() {

        String year = (mSpinnerYear != null && mSpinnerYear.getSelectedItem().toString() != null)
                ? mSpinnerYear.getSelectedItem().toString() : "2018";

        String path = "meterDetails/" +
                Pref.getValue(getApplicationContext(), Constant.KEY_METER_ID, "",
                        Constant.PREF_NAME) + "/" + year;

        Log.d(TAG, "path : " + path);

        return path;
    }

    private void retrieveFireBaseUserData() {
        DocumentReference documentReference = dataBase.collection("users").document(user.getUserId());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    user.setName(doc.getString(Constant.KEY_NAME));
                    user.setAddress(doc.getString(Constant.KEY_ADDRESS));
                    user.setMobileNo(doc.getString(Constant.KEY_MOBILE_NO));
                    user.setEmail(doc.getString(Constant.KEY_EMAIL));
                    user.setMeterId(doc.getString(Constant.KEY_METER_ID));
                    Pref.setValue(getApplicationContext(), Constant.KEY_METER_ID, user.getMeterId(), Constant.PREF_NAME);
                    Log.d(TAG, "Fetched Data : " + user.toString());

                    retrieveFireStoreMeterData(getPath());

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public User getUser(){
        return user;
    }

    public ArrayList<Map<String, Object>> getReading() {
        return reading;
    }
}

package com.wam.travelplanner.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.wam.travelplanner.R;
import com.wam.travelplanner.model.BoardingCard;
import com.wam.travelplanner.ui.BoardingCardListFragment.BoardingCardListFragmentListener;

public class TravelPlannerActivity extends ActionBarActivity implements BoardingCardListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_planner);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, new BoardingCardListFragment());
            transaction.commit();
        }
    }


    @Override
    public void onBoardingCardPress(BoardingCard boardingCard) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        transaction.replace(R.id.container, BoardingCardDetailsFragment.newInstance(boardingCard));
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }
}

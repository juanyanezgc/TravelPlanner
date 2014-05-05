package com.wam.travelplanner.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.wam.travelplanner.R;

public class BoardingCardListFragment extends Fragment {

    ViewSwitcher mViewSwitcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewSwitcher = (ViewSwitcher) inflater.inflate(R.layout.fragment_boarding_cards_list, container, false);
        return mViewSwitcher;
    }


}

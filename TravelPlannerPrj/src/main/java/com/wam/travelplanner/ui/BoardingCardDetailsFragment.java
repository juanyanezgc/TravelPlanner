package com.wam.travelplanner.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.wam.travelplanner.R;
import com.wam.travelplanner.model.BoardingCard;
import com.wam.travelplanner.model.TransportType;

public class BoardingCardDetailsFragment extends Fragment {

    public static BoardingCardDetailsFragment newInstance(BoardingCard boardingCard) {
        Bundle args = new Bundle();
        args.putParcelable(BOARDING_CARD_KEY, boardingCard);
        BoardingCardDetailsFragment boardingCardDetailsFragment = new BoardingCardDetailsFragment();
        boardingCardDetailsFragment.setArguments(args);
        return boardingCardDetailsFragment;
    }

    private static final String BOARDING_CARD_KEY = "boardingCard";
    private BoardingCard mBoardingCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boarding_card_details, container, false);

        if (savedInstanceState == null) {
            mBoardingCard = getArguments().getParcelable(BOARDING_CARD_KEY);
        } else {
            mBoardingCard = savedInstanceState.getParcelable(BOARDING_CARD_KEY);
        }

        ImageView imgTransport = (ImageView) view.findViewById(R.id.imgTransport);
        TextView txtFrom = (TextView) view.findViewById(R.id.txtFrom);
        TextView txtTo = (TextView) view.findViewById(R.id.txtTo);

        TransportType transportType = mBoardingCard.getType();

        switch (transportType){
            case Train:
                imgTransport.setImageResource(R.drawable.ic_train);
                break;
            case Plane:
                imgTransport.setImageResource(R.drawable.ic_plane);
                break;
            case Bus:
                imgTransport.setImageResource(R.drawable.ic_bus);
                break;
        }

        txtFrom.setText(mBoardingCard.getFrom());
        txtTo.setText(mBoardingCard.getTo());

        String id = mBoardingCard.getId();

        if (id == null) {
            TableRow idRow = (TableRow) view.findViewById(R.id.rowID);
            idRow.setVisibility(View.GONE);
        } else {
            TextView txtID = (TextView) view.findViewById(R.id.txtID);
            txtID.setText(id);
        }

        String seat = mBoardingCard.getSeat();
        if (seat == null) {
            TableRow seatRow = (TableRow) view.findViewById(R.id.rowSeat);
            seatRow.setVisibility(View.GONE);
        } else {
            TextView txtSeat = (TextView) view.findViewById(R.id.txtSeat);
            txtSeat.setText(seat);
        }

        String gate = mBoardingCard.getGate();
        if (gate == null) {
            TableRow gateRow = (TableRow) view.findViewById(R.id.rowGate);
            gateRow.setVisibility(View.GONE);
        } else {
            TextView txtGate = (TextView) view.findViewById(R.id.txtGate);
            txtGate.setText(gate);
        }

        String baggage = mBoardingCard.getBaggage();
        if (baggage == null) {
            TableRow baggageRow = (TableRow) view.findViewById(R.id.rowBaggage);
            baggageRow.setVisibility(View.GONE);
        } else {
            TextView txtBaggage = (TextView) view.findViewById(R.id.txtBaggage);
            txtBaggage.setText(baggage);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
        ActionBarActivity hostActivity = (ActionBarActivity) getActivity();
        hostActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BOARDING_CARD_KEY, mBoardingCard);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            ActionBarActivity activity = (ActionBarActivity) getActivity();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            activity.getSupportActionBar().setHomeButtonEnabled(false);
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.popBackStack();
        }

        return true;
    }
}

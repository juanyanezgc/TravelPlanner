package com.wam.travelplanner.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.wam.travelplanner.R;
import com.wam.travelplanner.model.BoardingCard;
import com.wam.travelplanner.parser.Parser;
import com.wam.travelplanner.utils.Debug;
import com.wam.travelplanner.utils.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BoardingCardListFragment extends Fragment {

    private ViewSwitcher mViewSwitcher;
    private TextView mTxtLoading;
    private TextView mTxtListTitle;
    private ListView mListBoardingCards;

    private AsyncTask mTask;



    private View.OnClickListener mBtnSortOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTxtLoading.setText(getString(R.string.sorting_dialog));
            mViewSwitcher.showPrevious();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewSwitcher = (ViewSwitcher) inflater.inflate(R.layout.fragment_boarding_cards_list, container, false);
        Button btnSort = (Button) mViewSwitcher.findViewById(R.id.btnSort);
        btnSort.setOnClickListener(mBtnSortOnClickListener);

        mTxtLoading = (TextView) mViewSwitcher.findViewById(R.id.txtLoading);
        mTxtListTitle = (TextView) mViewSwitcher.findViewById(R.id.txtListTitle);
        mListBoardingCards = (ListView) mViewSwitcher.findViewById(R.id.listBoardingCards);

        return mViewSwitcher;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTask = new ParseBoardingCardsTask();
        mTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mViewSwitcher = null;
        mTxtLoading = null;
        mTxtListTitle = null;

        if(mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING){
            mTask.cancel(true);
        }
        
        mTask = null;
    }

    private void fillList(List<BoardingCard> boardingCards) {
        BoardingCardsListAdapter boardingCardsListAdapter = new BoardingCardsListAdapter(getActivity(), boardingCards);
        mListBoardingCards.setAdapter(boardingCardsListAdapter);
        mViewSwitcher.showNext();
    }


    private class ParseBoardingCardsTask extends AsyncTask<Void, Void, List<BoardingCard>> {

        @Override
        protected List<BoardingCard> doInBackground(Void... params) {
            try {
                InputStream inputStream = getResources().openRawResource(R.raw.boarding_cards);
                String boardingCardsJson = Utils.readStringFromStream(inputStream);
                return Parser.parseBoardingCards(boardingCardsJson);
            } catch (IOException e) {
                Debug.logError(e.getMessage());
            } catch (JSONException e) {
                Debug.logError(e.getMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<BoardingCard> boardingCards) {
            fillList(boardingCards);
        }
    }

}

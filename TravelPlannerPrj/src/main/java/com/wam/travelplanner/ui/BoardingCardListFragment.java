package com.wam.travelplanner.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.List;

public class BoardingCardListFragment extends Fragment {

    public interface BoardingCardListFragmentListener {
        public void onBoardingCardPress(BoardingCard boardingCard);
    }

    private static final String BOARDING_CARDS_KEY = "boardingCards";
    private static final String SORTED_KEY = "sorted";

    private ViewSwitcher mViewSwitcher;
    private TextView mTxtLoading;
    private Button mBtnSort;
    private TextView mTxtListTitle;
    private ListView mListBoardingCards;

    private List<BoardingCard> mBoardingCards;
    private AsyncTask<Void, Void, List<BoardingCard>> mTask;
    private BoardingCardListFragmentListener mListener;
    private boolean mSorted;


    private View.OnClickListener mBtnSortOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mTxtLoading.setText(getString(R.string.sorting_dialog));
            mViewSwitcher.showPrevious();
            mTask = new SortTripTask();
            mTask.execute();
        }
    };

    private AdapterView.OnItemClickListener mListBoardingCardItemLister = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            mListener.onBoardingCardPress(mBoardingCards.get(position));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewSwitcher = (ViewSwitcher) inflater.inflate(R.layout.fragment_boarding_cards_list, container, false);
        Button btnSort = (Button) mViewSwitcher.findViewById(R.id.btnSort);
        btnSort.setOnClickListener(mBtnSortOnClickListener);

        mTxtLoading = (TextView) mViewSwitcher.findViewById(R.id.txtLoading);
        mBtnSort = (Button) mViewSwitcher.findViewById(R.id.btnSort);
        mTxtListTitle = (TextView) mViewSwitcher.findViewById(R.id.txtListTitle);
        mListBoardingCards = (ListView) mViewSwitcher.findViewById(R.id.listBoardingCards);
        mListBoardingCards.setOnItemClickListener(mListBoardingCardItemLister);

        return mViewSwitcher;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            if (mBoardingCards == null) {
                mTask = new ParseBoardingCardsTask();
                mTask.execute();
            } else {
                fillList(mBoardingCards);
            }
        } else {
            mBoardingCards = savedInstanceState.getParcelableArrayList(BOARDING_CARDS_KEY);
            if (mBoardingCards != null) {
                mSorted = savedInstanceState.getBoolean(SORTED_KEY);
                fillList(mBoardingCards);
            } else {
                mTask = new ParseBoardingCardsTask();
                mTask.execute();
            }

        }

        if (mSorted) {
            mBtnSort.setVisibility(View.GONE);
            mTxtListTitle.setText(R.string.sorted_boarding_cards);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (BoardingCardListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BoardingCardListFragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mViewSwitcher = null;
        mTxtLoading = null;
        mBtnSort = null;
        mTxtListTitle = null;

        mBoardingCards = null;

        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }

        mTask = null;
        mListener = null;
    }

    private void fillList(List<BoardingCard> boardingCards) {
        mBoardingCards = boardingCards;
        BoardingCardsListAdapter boardingCardsListAdapter = new BoardingCardsListAdapter(getActivity(), mBoardingCards);
        mListBoardingCards.setAdapter(boardingCardsListAdapter);
        mViewSwitcher.showNext();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BOARDING_CARDS_KEY, (ArrayList<? extends android.os.Parcelable>) mBoardingCards);
        outState.putBoolean(SORTED_KEY, mSorted);
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

    private class SortTripTask extends AsyncTask<Void, Void, List<BoardingCard>> {

        @Override
        protected List<BoardingCard> doInBackground(Void... params) {
            List<BoardingCard> sortedList = new ArrayList<BoardingCard>();

            sortBoardingCards(0, mBoardingCards, sortedList);

            return sortedList;
        }

        @Override
        protected void onPostExecute(List<BoardingCard> boardingCards) {
            mBtnSort.setVisibility(View.GONE);
            mTxtListTitle.setText(getString(R.string.sorted_boarding_cards));
            mSorted = true;
            fillList(boardingCards);
        }

        /**
         * Finds a sorted trip by backtracking
         *
         * @param stage    Number of boarding cards sorted
         * @param unsorted List of remaining unsorted boarding cards
         * @param sorted   List of sorted boarding cards*
         */
        private void sortBoardingCards(int stage, List<BoardingCard> unsorted, List<BoardingCard> sorted) {

            if (stage != mBoardingCards.size()) {

                for (BoardingCard boardingCard : unsorted) {
                    List<BoardingCard> remainingBoardingCards = Utils.cloneBoardingCardsList(unsorted);
                    remainingBoardingCards.remove(boardingCard);

                    if (stage == 0) {

                        sorted.add(boardingCard);
                        sortBoardingCards(stage + 1, remainingBoardingCards, sorted);
                        if (sorted.size() == mBoardingCards.size()) {
                            break;
                        }

                        sorted.remove(boardingCard);

                    } else {

                        BoardingCard lastAddedBoardingCard = sorted.get(stage - 1);
                        if (lastAddedBoardingCard.getTo().equals(boardingCard.getFrom())) {
                            sorted.add(boardingCard);
                            sortBoardingCards(stage + 1, remainingBoardingCards, sorted);

                            if (sorted.size() == mBoardingCards.size()) {
                                break;
                            }

                            sorted.remove(boardingCard);

                        }
                    }
                }
            }
        }
    }

}

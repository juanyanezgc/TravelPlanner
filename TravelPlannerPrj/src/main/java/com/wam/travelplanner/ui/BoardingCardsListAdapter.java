package com.wam.travelplanner.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wam.travelplanner.R;
import com.wam.travelplanner.model.BoardingCard;
import com.wam.travelplanner.model.TransportType;

import java.util.List;

public class BoardingCardsListAdapter extends BaseAdapter {

    private List<BoardingCard> mBoardingCards;
    private LayoutInflater mInflater;

    public BoardingCardsListAdapter(Context context, List<BoardingCard> boardingCards) {
        mInflater = LayoutInflater.from(context);
        mBoardingCards = boardingCards;
    }

    @Override
    public int getCount() {
        return mBoardingCards.size();
    }

    @Override
    public Object getItem(int position) {
        return mBoardingCards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        BoardingCardViewHolder boardingCardViewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.row_boarding_card, viewGroup, false);
            boardingCardViewHolder = new BoardingCardViewHolder();
            boardingCardViewHolder.txtBoardingCard = (TextView) view.findViewById(R.id.txtBoardingCard);
            view.setTag(boardingCardViewHolder);
        } else {
            boardingCardViewHolder = (BoardingCardViewHolder) view.getTag();
        }

        BoardingCard boardingCard = mBoardingCards.get(position);
        String transportType = boardingCard.getType().name();
        String from = boardingCard.getFrom();
        String to = boardingCard.getTo();

        String boardingCardSummary = mInflater.getContext().getString(R.string.boarding_card_summary, transportType, from, to);
        boardingCardViewHolder.txtBoardingCard.setText(boardingCardSummary);

        switch (boardingCard.getType()){
            case Train:
                boardingCardViewHolder.txtBoardingCard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_train,0,0,0);
                break;
            case Plane:
                boardingCardViewHolder.txtBoardingCard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_plane,0,0,0);
                break;
            case Bus:
                boardingCardViewHolder.txtBoardingCard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bus,0,0,0);
                break;
        }

        return view;
    }

    private static class BoardingCardViewHolder {
        public TextView txtBoardingCard;
    }
}

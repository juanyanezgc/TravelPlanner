package com.wam.travelplanner.parser;

import com.wam.travelplanner.model.BoardingCard;
import com.wam.travelplanner.model.TransportType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final String BOARDING_CARDS_TAG = "boardingCards";
    private static final String ID_TAG = "id";
    private static final String FROM_TAG = "from";
    private static final String TO_TAG = "to";
    private static final String TYPE_TAG = "type";
    private static final String GATE_TAG = "gate";
    private static final String SEAT_TAG = "seat";
    private static final String BAGGAGE_TAG = "baggage";


    public static List<BoardingCard> parseBoardingCards(String json) throws JSONException {
        List<BoardingCard> boardingCards = new ArrayList<BoardingCard>();

        JSONArray boardingCardsArray = new JSONObject(json).getJSONArray(BOARDING_CARDS_TAG);

        for (int i = 0; i < boardingCardsArray.length(); i++) {
            JSONObject boardingCardJSON = boardingCardsArray.getJSONObject(i);
            boardingCards.add(parseBoardingCard(boardingCardJSON));
        }

        return boardingCards;
    }

    private static BoardingCard parseBoardingCard(JSONObject json) throws JSONException {
        String id = null;

        if (json.has(ID_TAG)) {
            id = json.getString(ID_TAG);
        }

        String from = json.getString(FROM_TAG);
        String to = json.getString(TO_TAG);
        int type = json.getInt(TYPE_TAG);
        TransportType transportType = TransportType.fromOrdinal(type);

        String gate = null;

        if (json.has(GATE_TAG)) {
            gate = json.getString(GATE_TAG);
        }

        String seat = null;

        if (json.has(SEAT_TAG)) {
            seat = json.getString(SEAT_TAG);
        }

        String baggage = null;

        if (json.has(BAGGAGE_TAG)) {
            baggage = json.getString(BAGGAGE_TAG);
        }

        BoardingCard boardingCard = new BoardingCard(id, from, to, seat, transportType, gate, baggage);
        return boardingCard;

    }
}

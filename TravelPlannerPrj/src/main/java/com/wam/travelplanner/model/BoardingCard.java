package com.wam.travelplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BoardingCard implements Parcelable {
    private String id;
    private String from;
    private String to;
    private String seat;
    private TransportType type;
    private String gate;
    private String baggage;

    public BoardingCard(String id, String from, String to, String seat, TransportType type, String gate, String baggage) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.seat = seat;
        this.type = type;
        this.gate = gate;
        this.baggage = baggage;
    }

    public BoardingCard(BoardingCard boardingCard) {
        this.id = boardingCard.getId();
        this.from = boardingCard.getFrom();
        this.to = boardingCard.getTo();
        this.seat = boardingCard.getSeat();
        this.type = boardingCard.getType();
        this.gate = boardingCard.getGate();
        this.baggage = boardingCard.getBaggage();
    }

    public BoardingCard(Parcel in) {
        this.id = in.readString();
        this.from = in.readString();
        this.to = in.readString();
        this.seat = in.readString();
        this.type = in.readParcelable(TransportType.class.getClassLoader());
        this.gate = in.readString();
        this.baggage = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardingCard that = (BoardingCard) o;

        if (!from.equals(that.from)) return false;
        if (!to.equals(that.to)) return false;

        return true;
    }

    public static final Creator<BoardingCard> CREATOR = new Parcelable.Creator<BoardingCard>() {
        public BoardingCard createFromParcel(Parcel in) {
            return new BoardingCard(in);
        }

        public BoardingCard[] newArray(int size) {
            return new BoardingCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeString(seat);
        parcel.writeParcelable(type, PARCELABLE_WRITE_RETURN_VALUE);
        parcel.writeString(gate);
        parcel.writeString(baggage);
    }
}

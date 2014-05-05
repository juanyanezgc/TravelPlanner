package com.wam.travelplanner.model;

public class BoardingCard {
    private String id;
    private String from;
    private String to;
    private String seat;
    private TransportType type;
    private String baggage;



    public BoardingCard(String id, String from, String to,String seat, TransportType type, String baggage) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.seat = seat;
        this.type = type;
        this.baggage = baggage;
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

    public String getSeat(){
        return seat;
    }

    public void setSeat(String seat){
        this.seat = seat;
    }

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }
}

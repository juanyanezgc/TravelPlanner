package com.wam.travelplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public enum TransportType implements Parcelable {
    Train, Plane, Bus;

    private static final Map<Integer, TransportType> map = new HashMap<Integer, TransportType>();

    static {
        for (TransportType transportType : TransportType.values()) {
            map.put(transportType.ordinal(), transportType);
        }
    }

    public static TransportType fromOrdinal(int transportType) {
        return map.get(transportType);
    }


    public static final Creator<TransportType> CREATOR = new Creator<TransportType>() {
        @Override
        public TransportType createFromParcel(final Parcel source) {
            return TransportType.values()[source.readInt()];
        }

        @Override
        public TransportType[] newArray(final int size) {
            return new TransportType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ordinal());
    }
}

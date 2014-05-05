package com.wam.travelplanner.model;

import java.util.HashMap;
import java.util.Map;

public enum TransportType {
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


}

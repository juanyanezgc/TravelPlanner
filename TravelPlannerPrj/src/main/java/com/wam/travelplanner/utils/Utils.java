package com.wam.travelplanner.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {

    /**
     * Reads an input stream an convert it to a string
     *
     * @param stream Input stream to read
     *               *
     */
    public static String readStringFromStream(InputStream stream)
            throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }

        return total.toString();
    }

}

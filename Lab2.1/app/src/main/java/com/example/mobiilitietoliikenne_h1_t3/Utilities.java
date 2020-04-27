package com.example.mobiilitietoliikenne_h1_t3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utilities {

    final public static String ERROR_DATA_NOT_FOUND = "x3xfd533435dfdfdxc";
    final public static String ERROR_HTTP_REQUEST = "x3xfd533435dfdfdxf";

    public static String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }
}

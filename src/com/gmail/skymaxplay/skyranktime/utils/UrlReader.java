package com.gmail.skymaxplay.skyranktime.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by RaV on 27.12.2017.
 */
public class UrlReader {
    public static String readUrl(String urlString) throws IOException {
        BufferedReader reader = null;
        try {

            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}

package org.fosstrak.ale.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides a method to convert an input stream into a string.
 */
public class StreamUtil {

    /**
     * This method converts an input stream into a string.
     *
     * @param in to convert
     * @return string
     */
    public static String inputStream2String(InputStream in) {

        try {

            StringBuffer buf = new StringBuffer();
            while (in.available() > 0) {
                int i = in.read();
                buf.append((char) i);
            }
            return buf.toString();

        } catch (IOException e) {
            return null;
        }

    }

}
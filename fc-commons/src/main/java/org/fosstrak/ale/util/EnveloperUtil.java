package org.fosstrak.ale.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This class provides some methods to packaging a message into a soap envelope.
 */
public class EnveloperUtil {

    /**
     * xml version tag
     */
    private static final String XML_VERSION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    /**
     * opening soap envelope and body tag
     */
    private static final String ENVELOPE_OPEN = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Body>";

    /**
     * closing soap body and envelope tag
     */
    private static final String ENVELOPE_CLOSE = "</soap:Body></soap:Envelope>";

    /**
     * This method packaging a message from an input stream into a soap envelope and returns it as an input stream.
     *
     * @param in input stream with the message to envelope
     * @return input stream with the soap message
     */
    public static InputStream envelope(InputStream in) {

        return new ByteArrayInputStream(envelope(StreamUtil.inputStream2String(in)).getBytes());

    }

    /**
     * This method packaging a message from a string into a soap envelope and returns it as a string.
     *
     * @param s string with the message to envelope
     * @return string with the soap message
     */
    public static String envelope(String s) {

        String xmlVersion;
        String content;
        int index;
        if ((index = s.indexOf("?>") + 2) > 2) {
            xmlVersion = s.substring(0, index);
            content = s.substring(index);
        } else {
            xmlVersion = XML_VERSION;
            content = s;
        }

        StringBuffer buf = new StringBuffer();
        buf.append(xmlVersion);
        buf.append(ENVELOPE_OPEN);
        buf.append(content);
        buf.append(ENVELOPE_CLOSE);

        return buf.toString();

    }

}
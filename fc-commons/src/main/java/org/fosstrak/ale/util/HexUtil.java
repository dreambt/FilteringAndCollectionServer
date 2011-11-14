package org.fosstrak.ale.util;

import java.math.BigInteger;

/**
 * This method provides some methods to convert byte arrays into hexadecimal strings and vice versa.
 */
public class HexUtil {

    /**
     * This method converts a byte array into a hexadecimal string.
     *
     * @param byteArray to convert
     * @return hexadecimal string
     */
    public static String byteArrayToHexString(byte[] byteArray) {

        return new BigInteger(byteArray).toString(16).toUpperCase();

    }

    /**
     * This method converts a hexadecimal string into a byte array.
     *
     * @param hexString to convert
     * @return byte array
     */
    public static byte[] hexStringToByteArray(String hexString) {

        return new BigInteger(hexString, 16).toByteArray();

    }

}
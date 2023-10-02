package com.castlabs.fileanalyser.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteArrayUtils {
    public static int byteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static String byteArrayToString(byte[] bytes, int position, int length) {
        return new String(bytes, position, length, StandardCharsets.UTF_8);
    }
}

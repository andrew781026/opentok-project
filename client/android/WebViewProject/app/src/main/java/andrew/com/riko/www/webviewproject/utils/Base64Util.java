package andrew.com.riko.www.webviewproject.utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Base64Util {

    public static byte[] encode(final byte[] src){
        return Base64.encode(src,Base64.DEFAULT);
    }

    public static byte[] decode(final byte[] src){
        return Base64.decode(src,Base64.DEFAULT);
    }

    public static byte[] decodeFromString(final String str){
        return Base64.decode(str,Base64.DEFAULT);
    }

    public static byte[] decode(final String str){
        return decodeFromString(str);
    }

    public static String compressAndEncode(final byte[] src) throws IOException {
        byte[] compressed = GzipUtil.compress(src);
        return new String(encode(compressed));
    }

    public static byte[] decodeAndDepress(final byte[] src) throws IOException {
        byte[] bytes = decode(src);
        return GzipUtil.decompress(bytes);
    }

    public static byte[] decodeAndDepress(final String str) throws IOException {
        return decodeAndDepress(str.getBytes());
    }

    public static String encode(final ByteArrayOutputStream baos) throws IOException {
        byte[] bytes = baos.toByteArray();
        return compressAndEncode(bytes);
    }

    public static byte[] decode(final ByteArrayOutputStream baos) throws IOException {
        byte[] bytes = baos.toByteArray();
        return decodeAndDepress(bytes);
    }


}

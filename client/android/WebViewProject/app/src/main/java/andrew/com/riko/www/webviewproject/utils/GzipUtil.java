package andrew.com.riko.www.webviewproject.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {

    public static byte[] compress(final byte[] input) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        GZIPOutputStream gzipper = new GZIPOutputStream(bout);

        gzipper.write(input, 0, input.length);
        gzipper.close();
        bout.close();

        return bout.toByteArray();
    }

    public static byte[] compress(final String str,final String charset) throws IOException {
        return compress(str.getBytes(charset));
    }

    public static byte[] compress(final String str) throws IOException {
        // 預定 string 用 utf-8 編碼
        return compress(str.getBytes("UTF-8"));
    }

    public static byte[] decompress(final byte[] compressed) throws IOException {

        if ((compressed == null) || (compressed.length == 0)) {
            return compressed ;
        }
        if (isCompressed(compressed)) {
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed));

            byte[] buffer = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int len;
            while ((len = gis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            gis.close();
            out.close();
            return out.toByteArray();

        } else {
            return compressed ;
        }

    }

    public static boolean isCompressed(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }


}

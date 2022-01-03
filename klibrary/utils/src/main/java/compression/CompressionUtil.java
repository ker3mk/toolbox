package compression;

import org.xerial.snappy.Snappy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Kerem
 * 3/20/2019
 */
public class CompressionUtil {
    public static ByteBuffer compressSnappy(byte[] input) throws IOException {
        return ByteBuffer.wrap(Snappy.compress(input));
    }

    public static ByteBuffer deCompressSnappy(byte[] input) throws IOException {
        return ByteBuffer.wrap(Snappy.uncompress(input));
    }

    public static ByteBuffer compressQuickLZ(byte[] input) throws IOException {
        return ByteBuffer.wrap(QuickLZ.compress(input, 1));
    }

    public static ByteBuffer deCompressQuickLZ(byte[] input) throws IOException {
        return ByteBuffer.wrap(QuickLZ.decompress(input));
    }

    public static ByteBuffer deCompressZLib(byte[] input) throws IOException {
        java.util.zip.GZIPInputStream gzis = new GZIPInputStream(new ByteArrayInputStream(input));
        ByteArrayOutputStream uncompressOut = new ByteArrayOutputStream();
        byte[] buffer = new byte[8096];
        int len;
        while ((len = gzis.read(buffer)) > 0) {
            uncompressOut.write(buffer, 0, len);
        }
        ByteBuffer decompressedZlibBuffer = ByteBuffer.wrap(uncompressOut.toByteArray());
        return decompressedZlibBuffer;
    }

    public static ByteBuffer compressZLib(byte[] input) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        java.util.zip.GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
        zipStream.write(input);
        zipStream.close();
        byteStream.close();
        return ByteBuffer.wrap(byteStream.toByteArray());
    }


}

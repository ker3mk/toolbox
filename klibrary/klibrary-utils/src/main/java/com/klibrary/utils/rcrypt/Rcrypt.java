// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Rcrypt.java

package com.klibrary.utils.rcrypt;




import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

public class Rcrypt {
    static String DEFAULT_CRYPT_SECRET = "secretKey";
    //https://www.ietf.org/rfc/rfc4880.txt
    Rcrypt() {
    }

    public static String decode(String crypt, String secret) {
        byte plaintext[];
        ByteArrayInputStream bais = new ByteArrayInputStream(crypt.getBytes());
        try {
            InputStream decodedStream = MimeUtility.decode(bais, "base64");
            byte decoded[] = new byte[decodedStream.available()];
            decodedStream.read(decoded);
            byte salt[] = new byte[2];
            System.arraycopy(decoded, 0, salt, 0, salt.length);
            byte xor[] = new byte[decoded.length - salt.length];
            System.arraycopy(decoded, salt.length, xor, 0,
                    decoded.length - salt.length);
            xor = rtrim(xor, (xor.length / 16) * 16);
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            byte saltAndSecret[] = new byte[secret.length() + 2];
            byte secretByteArray[] = secret.getBytes();
            System.arraycopy(salt, 0, saltAndSecret, 0, salt.length);
            System.arraycopy(secretByteArray, 0, saltAndSecret, salt.length,
                    secretByteArray.length);
            msgDigest.update(saltAndSecret);
            byte hash[] = msgDigest.digest();
            int repeat = (xor.length + 15) / 16;
            byte hashrep[] = new byte[repeat * hash.length];
            for (int r = 0; r < repeat; r++) {
                System.arraycopy(hash, 0, hashrep, r * hash.length, hash.length);

            }
            BitSet xorBS = fromByteArray(xor);
            BitSet hashrepBS = fromByteArray(hashrep);
            hashrepBS.xor(xorBS);
            plaintext = toByteArray(hashrepBS);
            plaintext = rtrim(plaintext);
            plaintext = ltrim(plaintext);
            return new String(plaintext);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (MessagingException ex) {
            return null;
        }
    }

    public static String encode(String password, String secret) {
        int num = (new Random()).nextInt(65535);
        return encode(password, secret, num);
    }

    public static boolean verify(String password, String secret, String crypt)
            throws RcryptException {
        if (decode(crypt, secret) == null)
            return false;
        else
            return decode(crypt, secret).equals(password);
    }

    private static String encode(String password, String secret, int num) {
        StringBuffer encodedString;
        byte salt[] = getShortBytes(num);
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            byte saltAndSecret[] = new byte[secret.length() + 2];
            byte secretByteArray[] = secret.getBytes();
            System.arraycopy(salt, 0, saltAndSecret, 0, salt.length);
            System.arraycopy(secretByteArray, 0, saltAndSecret, salt.length,
                    secretByteArray.length);
            msgDigest.update(saltAndSecret);
            byte hash[] = msgDigest.digest();
            int repeat = (password.length() + 16) / 16;
            byte hashrep[] = new byte[repeat * hash.length];
            for (int r = 0; r < repeat; r++) {
                System.arraycopy(hash, 0, hashrep, r * hash.length, hash.length);

            }
            BitSet passwordBS = fromByteArray(password.getBytes());
            BitSet hashrepBS = fromByteArray(hashrep);
            hashrepBS.xor(passwordBS);
            byte xor[] = toByteArray(hashrepBS);
            byte cipher[] = new byte[xor.length + salt.length];
            System.arraycopy(salt, 0, cipher, 0, salt.length);
            System.arraycopy(xor, 0, cipher, salt.length, xor.length);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream encodedStream = MimeUtility.encode(baos, "base64");
            encodedStream.write(cipher, 0, cipher.length);
            encodedStream.flush();
            String base64String = baos.toString();
            encodedString = new StringBuffer();
            for (int newlineIndex = base64String.indexOf("\n"); newlineIndex >= 0;
                 newlineIndex = base64String.indexOf("\n")) {
                encodedString.append(base64String.substring(0, newlineIndex - 1));
                base64String = base64String.substring(newlineIndex + 1);
            }

            encodedString.append(base64String);
            return encodedString.toString();
        } catch (IOException ex) {
            return null;
        } catch (MessagingException ex) {
            return null;
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    private static byte[] getShortBytes(int num) {
        byte b[] = new byte[2];
        int i = 0;
        for (int shift = 8; i < 2; shift -= 8) {
            b[i] = (byte) (0xff & num >> shift);
            i++;
        }

        return b;
    }

    private static BitSet fromByteArray(byte bytes[]) {
        BitSet bits = new BitSet(bytes.length * 8);
        for (int i = 0; i < bytes.length * 8; i++)
            if ((bytes[bytes.length - i / 8 - 1] & 1 << i % 8) > 0)
                bits.set(i);
            else
                bits.clear(i);

        return bits;
    }

    private static byte[] toByteArray(BitSet bits) {
        int byteArrayLen = bits.size() / 8;
        if (bits.size() % 8 != 0)
            byteArrayLen++;
        byte bytes[] = new byte[byteArrayLen];
        for (int i = 0; i < bits.size(); i++)
            if (bits.get(i))
                bytes[bytes.length - i / 8 - 1] |= 1 << i % 8;

        return bytes;
    }

    private static byte[] ltrim(byte byteArray[]) {
        byte result[] = byteArray;
        int i;
        for (i = 0; i < byteArray.length; i++)
            if (byteArray[i] != 0)
                break;

        if (i > 0) {
            byte tmpByteArray[] = new byte[byteArray.length - i];
            System.arraycopy(byteArray, i, tmpByteArray, 0, byteArray.length - i);
            result = tmpByteArray;
        }
        return result;
    }

    private static byte[] rtrim(byte byteArray[]) {
        return rtrim(byteArray, 0);
    }

    private static byte[] rtrim(byte byteArray[], int minLength) {
        byte result[] = byteArray;
        int i;
        for (i = byteArray.length - 1; i >= minLength; i--)
            if (byteArray[i] != 0)
                break;

        if (++i < byteArray.length) {
            byte tmpByteArray[] = new byte[i];
            System.arraycopy(byteArray, 0, tmpByteArray, 0, i);
            result = tmpByteArray;
        }
        return result;
    }

    private static String toHexString(byte byteArray[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            long l = unsignedByte(byteArray[i]);
            if (l < 10L)
                sb.append("0");
            sb.append(Long.toHexString(l));
        }

        return sb.toString();
    }

    private static long unsignedByte(byte b) {
        return b >= 0 ? b : b + 256;
    }

    public static void main(String[] args) {
        try {
            String encode = Rcrypt.encode("admin", DEFAULT_CRYPT_SECRET);
            String decode = Rcrypt.decode(encode, DEFAULT_CRYPT_SECRET);
            System.out.println(Rcrypt.verify("admin", DEFAULT_CRYPT_SECRET, encode));
        } catch (RcryptException e) {
            e.printStackTrace();
        }
    }

    public static String computeHash(char[] input) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] bytes = charArrayToByteArray(input, "UTF-8");
            byte[] digest = instance.digest(bytes);
            StringBuffer buffer = new StringBuffer();

            for (byte b : digest) {
                String hex = Integer.toHexString(0xFF & b);
                buffer.append(hex.length() == 2 ? hex : "0" + hex);
            }

            Arrays.fill(bytes, (byte) 0);
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static byte[] charArrayToByteArray(char[] chars, String charsetName) {
        Charset charSet = charsetName == null ? Charset.forName("UTF-8") : Charset.forName(charsetName);
        ByteBuffer byteBuffer = charSet.encode(CharBuffer.wrap(chars));
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes, 0, bytes.length);
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }

}

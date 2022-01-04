package com.klibrary.utils.network;


/**
 * User: Oktay CEKMEZ<br>
 * Date: 18.08.2016<br>
 * Time: 21:36<br>
 */
public class StringUtil {
    public static boolean validIp(String ip){
        return IPUtil.validIp(ip);
    }


    public static boolean isEmptyString(String string){
        return !(string != null && !string.trim().isEmpty());
    }


    public static String capitalize(final String str) {//
        final int delimLen = -1;
        if (isEmptyString(str)) {
            return str;
        }
        final char[] buffer = str.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; i++) {
            final char ch = buffer[i];
            if (isDelimiter(ch)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer[i] = Character.toTitleCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(buffer);
    }

    public static String uncapitalize(final String str) {
        final int delimLen = -1;
        if (isEmptyString(str)) {
            return str;
        }
        final char[] buffer = str.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; i++) {
            final char ch = buffer[i];
            if (isDelimiter(ch)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer[i] = Character.toLowerCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(buffer);
    }


        public static boolean isNumeric(final String cs) {
                if (isEmptyString(cs)) {
                        return false;
                    }
                final int sz = cs.length();
                for (int i = 0; i < sz; i++) {
                        if (Character.isDigit(cs.charAt(i)) == false) {
                                return false;
                            }
                    }
                return true;
            }


    //-----------------------------------------------------------------------
    /**
     * Is the character a delimiter.
     *
     * @param ch  the character to check
     * @return true if it is a delimiter
     */
    private static boolean isDelimiter(final char ch) {
            return Character.isWhitespace(ch);

    }
}

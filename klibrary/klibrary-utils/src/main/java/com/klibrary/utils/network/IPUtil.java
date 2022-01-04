package com.klibrary.utils.network;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * User: Oktay CEKMEZ<br>
 * Date: 1.09.2016<br>
 * Time: 10:09<br>
 */

public class IPUtil {
    public static final Pattern IpAddressPattern =
            Pattern.compile( "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");


    public static boolean validIp(String ip){
        return !StringUtil.isEmptyString(ip) && IpAddressPattern.matcher(ip).matches();
    }

    public static long calculateNumOfIpBlock(int subnet) {
        return Math.round(Math.pow(2, 32 - subnet));
    }


    /**
     * Returns the long format of the provided IP address.
     *
     * @param ipAddress the IP address
     * @return the long format of <code>ipAddress</code>
     * @throws IllegalArgumentException if <code>ipAddress</code> is invalid
     */
    public static long convert(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("ip address cannot be null or empty");
        }
        String[] octets = ipAddress.split(Pattern.quote("."));
        if (octets.length != 4) {
            throw new IllegalArgumentException("invalid ip address:"+ipAddress);
        }
        long ip = 0;
        for (int i = 3; i >= 0; i--) {
            long octet = Long.parseLong(octets[3 - i]);
            if (octet > 255 || octet < 0) {
                throw new IllegalArgumentException("invalid ip address:"+ipAddress);
            }
            ip |= octet << (i * 8);
        }

        if(ip<0)
            throw new IllegalArgumentException("Not expected ip number found as negative:"+ipAddress);
        return ip;
    }

    /**
     * Returns the 32bit dotted format of the provided long ip.
     *
     * @param ip the long ip
     * @return the 32bit dotted format of <code>ip</code>
     * @throws IllegalArgumentException if <code>ip</code> is invalid
     */
    public static String convert(long ip) {
        // if ip is bigger than 255.255.255.255 or smaller than 0.0.0.0
        if (ip > 4294967295L || ip < 0) {
            throw new IllegalArgumentException("invalid ip");
        }
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            int shift = i * 8;
            ipAddress.append((ip & (0xff << shift)) >> shift);
            if (i > 0) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }


    public List<String> createPool(String startIp, long numOfIp) {
        List<String> ips = new ArrayList<String>();
        long startIpIntegerValue = convert(startIp);
        for (long i = (numOfIp - 1); i > 0; i--) { // host addresses range
            long ipAsNumeric = ((Long) (startIpIntegerValue + i)).longValue();
            ips.add(convert(ipAsNumeric));

        }
        return ips;
    }

    public static void main(String[] args) {

    }
}

package com.klibrary.utils.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


public class DbUtil {
    private static final Logger log = LoggerFactory.getLogger(DbUtil.class);

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final String CIDR_PART = "/[0-9]+";


    public static Set<Device> readTanDevices() {
        final Set<Device> devices = new HashSet<>();

        log.info("Please wait, Reading Tan devices...");
        try {
            Connection c = getConnection();
            PreparedStatement stmt = c.prepareStatement("select * from test");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                final String tanName = rs.getString(1);
                final String ndc = rs.getString(2);
                final String interfaceIp = discardCidr(rs.getString(3));

                log.info("Read device - Tan Name:{}, ndc:{}, interfaceIp: {}", tanName, ndc, interfaceIp);

                if (interfaceIp == null || !validateIp(interfaceIp)) {
                    log.warn("Skipping Tan {}: Invalid Ip Address, {}", tanName, interfaceIp);
                    continue;
                }

                DbUtil.Device device = new DbUtil.Device(tanName, interfaceIp, "location", "location", "", "deviceType");
                devices.add(device);
            }
        } catch (SQLException e) {
        }

        log.info("Completed reading Tan devices, count: {}", devices.size());

        return devices;
    }


    public static Connection getConnection() {
        Connection connection = null;
        final String dbUrl = "test";
        final String username = "test";
        final String password = "test";
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e) {

        }
        return connection;
    }

    public static boolean validateIp(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    public static String discardCidr(final String ip) {
        if (ip == null || ip.isEmpty()) {
            return ip;
        }
        return ip.replaceFirst(CIDR_PART, "");
    }


    static class Device {
        private String name;
        private String ipAddress;
        private String location;
        private String probe;
        private String probeInterface;

        private String deviceType;

        private Map<String, Object> data;

        public Device() {
        }

        public Device(String name, String ipAddress, String location, String deviceType) {
            this(name, ipAddress, location, location, deviceType);
        }

        public Device(String name, String ipAddress, String location, String probe, String deviceType) {
            this.name = name;
            this.ipAddress = ipAddress;
            this.location = location;
            this.deviceType = deviceType;
            this.probe = probe;
        }

        public Device(String name, String ipAddress, String location, String probe, String probeInterface, String deviceType) {
            this.name = name;
            this.ipAddress = ipAddress;
            this.location = location;
            this.deviceType = deviceType;
            this.probe = probe;
            this.probeInterface = probeInterface;
        }
    }

}

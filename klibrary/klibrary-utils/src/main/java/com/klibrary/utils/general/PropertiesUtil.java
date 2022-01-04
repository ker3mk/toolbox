package com.klibrary.utils.general;


import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesUtil {
    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties properties = new Properties();


    static void loadProperties(Properties props) throws IOException {
        properties = props;
        PropertyConfigurator.configure(props);
    }

    static int getSize() {
        return properties.size();
    }

    static String getProperty(String property, String defaultValue) {
        if (getProperties().isEmpty()) {

            try {
                if (System.getProperty("producer.config") != null) {
                    System.out.println(("Properties are loading from System Environment (\"producer.config\")"));
                    System.out.println("Producer Config Path:" + System.getProperty("producer.config"));

                    Path propertyFile = Paths.get(System.getProperty("producer.config"));
                    if (Files.exists(propertyFile)) {
                        System.out.println("Loading: " + propertyFile);
                        getProperties().load(Files.newInputStream(propertyFile));
                    }
                } else {
                    System.out.println("Properties are loading from Jar (\"krondc.properties\")");
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    getProperties().load(loader
                            .getResourceAsStream("krondc.properties"));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }}
        return properties.getProperty(property, defaultValue);
    }
    public static Properties getProperties() {
        return properties;
    }

    public static boolean hasProperty(String property) {
        return getProperty(property) != null
                && !"".equals(getProperty(property).trim());
    }

    public static boolean getBooleanProperty(String property,
                                             Boolean defaultValue) {
        return Boolean.parseBoolean(getProperty(property,
                defaultValue.toString()).trim());
    }


    public static String getProperty(String string) {
        return getProperty(string, null);
    }

    public static int getIntegerProperty(String string, int i) {
        try {
            return Integer.parseInt(getProperty(string, Integer.toString(i)));
        } catch (NumberFormatException e) {
            return i;
        }
    }
    /**
     *  Property:<strong>cassandra.localdc</strong>
     *  Cassandra Local DC
     *  Default: DC1
     *  @return Cassandra DC Information
     */
    public static String getCassandraLocalDC() {
        return getProperty("cassandra.localdc", "null");
    }
    /**
     *  Property:<strong>cassandra.nodes</strong>
     *  Cassandra Connection Nodes List Ex Val. 192.168.1.2,192.168.1.3
     *  Default: 127.0.0.1
     *  @return Cassandra Host Array
     */
    public static String[] getCassandraHosts() {
        return getProperty("cassandra.nodes", "127.0.0.1").split(",");
    }
    /**
     *  Property:<strong>cassandra.is.auth.provider</strong>
     *  Cassandra Authentication Enable/Disable  Ex Val. true or false
     *  Default: false
     *  @return String Array
     */
    public static String getCassandraAuthProvider() {
        return getProperty("cassandra.is.auth.provider", "false");
    }
    /**
     *  Property:<strong>cassandra.user</strong>
     *  Cassandra Authentication User  Ex Val. aioc
     *  Default: aioc
     *  @return String
     */
    public static String getCassandraUser() {
        return getProperty("cassandra.user", "aioc");
    }
    /**
     *  Property:<strong>cassandra.password</strong>
     *  Cassandra Authentication Password  Ex Val. aioc
     *  Default: aioc
     *  @return String
     */
    public static String getCassandraPassword() {
        return getProperty("cassandra.password", "aioc");
    }

    /**
     *  Property:<strong>zookeeper.node</strong>
     *  Zookeeper Node Connection Info  Ex Val. 127.0.0.1:2181
     *  Default: 127.0.0.1:2181
     *  @return String
     */
    public static String getZookeeperNode() {
        return getProperty("zookeeper.node", "10.20.40.207:2181,10.20.40.206:2181");
    }
    /**
     * @deprecated
     *  Property:<strong>secondary.zookeeper.enabled</strong>
     *  Zookeeper Secondary enabled for alarmpolicy update
     *  Default: 127.0.0.1:2181
     *  @return Boolean
     */
    public static Boolean getSecondaryZookeeperEnabled() {
        return getBooleanProperty("secondary.zookeeper.enabled", false);
    }
    /**
     * @deprecated
     *  Property:<strong>secondary.zookeeper.enabled</strong>
     *  Zookeeper Secondary Ip Node for alarmpolicy update
     *  Default: 127.0.0.1:2181
     *  @return String
     */
    public static String getSecondaryZookeeperNode() {
        return getProperty("secondary.zookeeper.node", "10.20.40.207:2181");
    }
    /**
     * @deprecated
     *  Property:<strong>zookeeper.batchsize</strong>
     *  Zookeeper Batch Size
     *  Default: 1000
     *  @return String
     */
    public static String getZookeeperBatchSize() {
        return getProperty("zookeeper.batchsize", "1000");
    }
    /**
     *  Property:<strong>zookeeper.serviceid</strong>
     *  Zookeeper Service Id it used for clustering unique producer.war nodes paths
     *  Default: /probecontroller
     *  @return String
     */
    public static String getServiceId() {
        return getProperty("zookeeper.serviceid", "/probecontroller");
    }
    /**
     *  Property:<strong>zookeeper.cglogger.path</strong>
     *  Zookeeper Cglogger config paths
     *  Default: /cglogger
     *  @return String
     */
    public static String getZookeeperCgloggerPath() {
        return getProperty("zookeeper.cglogger.path", "/cglogger");
    }
    /**
     *  Property:<strong>heartbeat.desturl</strong>
     *  Heartbeat Destination Url
     *  Default: http://127.0.0.1:8080/probe-inventory-ui/ProbeHeartbeatServlet
     *  @return String
     */
    public static String getHeartbeatDestUrl() {
        return getProperty("heartbeat.desturl", "http://127.0.0.1:8080/probe-inventory-ui/ProbeHeartbeatServlet");
    }
    /**
     *  Property:<strong>isqm.version</strong>
     *  Isqm Version
     *  Default: v2
     *  @return String
     */
    public static String getIsqmVersion() {
        return getProperty("isqm.version", "v2");
    }
    /**
     *  Property:<strong>kafka.bootstrap.servers</strong>
     *  Kafka Servers Connection List Ex Val. 10.20.40.39:9092,10.20.40.50:9092
     *  Default: localhost:9092
     *  @return String
     */
    public static String getKafkaBootstrapServer() {
        return getProperty("kafka.bootstrap.servers", "localhost:9092");
    }
    /**
     *  Property:<strong>kafka.consumer.thread.count</strong>
     *  Kafka test-queue listener size it used for consuming test start/stop request
     *  Default: 1
     *  @return Integer
     */
    public static int getKafkaConsumerThreadCount() {
        return getIntegerProperty("kafka.consumer.thread.count", 1);
    }

    /**
     *  Property:<strong>producer.discard.settings</strong>
     *  Controller Discard test result given logic and metric types.
     *  Default: [{"testType":"IPQM","key":"AVERAGE_JITTER","threshold":null,"checkType":"=="},{"testType":"IPQM","subTestType":"TWAMP","key":"AVERAGE_LATENCY","threshold":null,"checkType":">"},{"testType":"NSQM","subTestType":"HTTP","key":"RESPONSE_CODE","threshold":null,"checkType":"=="},{"testType":"NSQM","subTestType":"POP3","key":"RESPONSE_CODE","threshold":null,"checkType":"=="},{"testType":"NSQM","subTestType":"FTP","key":"RESPONSE_CODE","threshold":null,"checkType":"=="},{"testType":"NSQM","subTestType":"DNS","key":"RESPONSE_CODE","threshold":null,"checkType":"=="},{"testType":"NSQM","subTestType":"IPTV","key":"DHCP_DELAY","threshold":null,"checkType":"=="},{"testType":"ISQM","key":"UPLOAD_RATE","threshold":null,"checkType":">"},{"testType":"VOQM","key":"SER","threshold":null,"checkType":">"},{"testType":"RTSP","key":"AVERAGE_LATENCY","threshold":null,"checkType":">"},{"testType":"RSQM","key":"JITTER","threshold":null,"checkType":">"},{"testType":"NTP","key":"RESPONSE_TIME","threshold":null,"checkType":">"},{"testType":"DHCP","key":"RESPONSE_TIME","threshold":null,"checkType":">"},{"testType":"TRACEROUTE","key":"RESPONSE_TIME","threshold":null,"checkType":">"},{"testType":"API","key":"AVERAGE_LATENCY","threshold":null,"checkType":">"}]
     *  @return String
     */
    public static String getDiscardSettings() {
        return getProperty("producer.discard.settings","[{\"testType\":\"IPQM\",\"key\":\"AVERAGE_JITTER\",\"threshold\":null,\"checkType\":\"==\"},{\"testType\":\"IPQM\",\"subTestType\":\"TWAMP\",\"key\":\"AVERAGE_LATENCY\",\"threshold\":null,\"checkType\":\">\"},{\"testType\":\"NSQM\",\"subTestType\":\"HTTP\",\"key\":\"RESPONSE_CODE\",\"threshold\":null,\"checkType\":\"==\"},{\"testType\":\"NSQM\",\"subTestType\":\"POP3\",\"key\":\"RESPONSE_CODE\",\"threshold\":null,\"checkType\":\"==\"},{\"testType\":\"NSQM\",\"subTestType\":\"FTP\",\"key\":\"RESPONSE_CODE\",\"threshold\":null,\"checkType\":\"==\"},{\"testType\":\"NSQM\",\"subTestType\":\"DNS\",\"key\":\"RESPONSE_CODE\",\"threshold\":null,\"checkType\":\"==\"},{\"testType\":\"NSQM\",\"subTestType\":\"IPTV\",\"key\":\"DHCP_DELAY\",\"threshold\":null,\"checkType\":\"==\"},{\"testType\":\"ISQM\",\"key\":\"UPLOAD_RATE\",\"threshold\":null,\"checkType\":\">\"},{\"testType\":\"VOQM\",\"key\":\"SER\",\"threshold\":null,\"checkType\":\">\"},{\"testType\":\"RTSP\",\"key\":\"AVERAGE_LATENCY\",\"threshold\":null,\"checkType\":\">\"},{\"testType\":\"RSQM\",\"key\":\"JITTER\",\"threshold\":null,\"checkType\":\">\"},{\"testType\":\"NTP\",\"key\":\"RESPONSE_TIME\",\"threshold\":null,\"checkType\":\">\"},{\"testType\":\"DHCP\",\"key\":\"RESPONSE_TIME\",\"threshold\":null,\"checkType\":\">\"},{\"testType\":\"TRACEROUTE\",\"key\":\"RESPONSE_TIME\",\"threshold\":null,\"checkType\":\">\"},{\"testType\":\"API\",\"key\":\"AVERAGE_LATENCY\",\"threshold\":null,\"checkType\":\">\"}]");
    }
    /**
     *  Property:<strong>json.probe.version</strong>
     *  Probe Json Version
     *  Default: 2019.1
     *  @return Integer
     */
    public static String getJsonProbeVersion() {
        return getProperty("json.probe.version", "2019.1");
    }
    /**
     *  Property:<strong>status.update.url</strong>
     *  Test Status Update  after controller starts stops test it updated on postgre db and netright.
     *  Default: http://127.0.0.1:8080/test-definition-ui/update_status
     *  @return String
     */
    public static String getStatusUpdateUrl() {
        return getProperty("status.update.url", "http://127.0.0.1:8080/test-definition-ui/update_status");
    }
    /**
     *  Property:<strong>status.update.url.second</strong>
     *  Test Status Update  after controller starts stops test it updated on postgre db and netright. Secondary
     *  Default: http://127.0.0.1:8080/test-definition-ui/update_status
     *  @return String
     */
    public static String getStatusUpdateUrlSecond() {
        return getProperty("status.update.url.second", "http://127.0.0.1:8080/test-definition-ui/update_status");
    }
    /**
     *  Property:<strong>cglogger.traffic.server.nat</strong>
     *  Cglogger traffic Server Nat Ip
     *  Default: 127.0.0.1
     *  @return Integer
     */
    public static String getTrafficServerNatIp() {
        return getProperty("cglogger.traffic.server.nat", "127.0.0.1");
    }
}
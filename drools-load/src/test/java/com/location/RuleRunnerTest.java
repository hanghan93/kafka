package com.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class RuleRunnerTest extends RuleRunner<Measurement> {

    Measurement container;

    @Test
    @DisplayName("load from file")
    void fired_01()  {
        Supplier<Measurement> factory = () -> JsonMapper.load("json/Measurement", Measurement.class);
        container = factory.get();
        run(container);
        Assertions.assertEquals(1,1);
    }

    @Test
    @DisplayName("load from string")
    void fired_02()  {
        String json = "{\n" +
                "    \"task\": {\n" +
                "      \"count\": -1,\n" +
                "      \"filter\": null,\n" +
                "      \"parameters\": {\n" +
                "        \"target\": \"i2.cdn.turner.com\",\n" +
                "        \"server\": \"\"\n" +
                "      },\n" +
                "      \"created\": 1329944169241156,\n" +
                "      \"start_time\": null,\n" +
                "      \"interval_sec\": 600.0,\n" +
                "      \"priority\": null,\n" +
                "      \"tag\": null,\n" +
                "      \"end_time\": null,\n" +
                "      \"type\": \"dns_lookup\",\n" +
                "      \"id\": \"3\"\n" +
                "    },\n" +
                "    \"parameters\": {\n" +
                "      \"count\": -1,\n" +
                "      \"target\": \"i2.cdn.turner.com\",\n" +
                "      \"parameters\": null,\n" +
                "      \"start_time\": \"2012-06-06T00:06:14.593Z\",\n" +
                "      \"interval_sec\": 600,\n" +
                "      \"server\": \"\",\n" +
                "      \"priority\": 0,\n" +
                "      \"end_time\": \"2012-06-06T23:26:14.593Z\",\n" +
                "      \"key\": \"3\",\n" +
                "      \"type\": \"dns_lookup\"\n" +
                "    },\n" +
                "    \"success\": true,\n" +
                "    \"timestamp\": 1338941175490000,\n" +
                "    \"device_properties\": {\n" +
                "      \"battery_level\": 96,\n" +
                "      \"cell_info\": null,\n" +
                "      \"timestamp\": 1338941175487000,\n" +
                "      \"network_type\": \"EDGE\",\n" +
                "      \"os_version\": \"INCREMENTAL:eng.root.20120210.171733, RELEASE:4.0.3, SDK_INT:15\",\n" +
                "      \"device_info\": {\n" +
                "        \"model\": \"Full Android on Crespo\",\n" +
                "        \"os\": \"INCREMENTAL:eng.root.20120210.171733, RELEASE:4.0.3, SDK_INT:15\",\n" +
                "        \"manufacturer\": \"unknown\"\n" +
                "      },\n" +
                "      \"carrier\": \"T-Mobile\",\n" +
                "      \"location\": {\n" +
                "        \"latitude\": 47.649999999999999,\n" +
                "        \"longitude\": -122.297\n" +
                "      },\n" +
                "      \"rssi\": 8,\n" +
                "      \"app_version\": \"0.2\",\n" +
                "      \"location_type\": \"network\",\n" +
                "      \"is_battery_charging\": false\n" +
                "    },\n" +
                "    \"values\": {\n" +
                "      \"time_ms\": \"777\",\n" +
                "      \"real_hostname\": \"\\\"8.27.224.126\\\"\",\n" +
                "      \"address\": \"\\\"8.27.224.126\\\"\"\n" +
                "    },\n" +
                "    \"type\": \"dns_lookup\",\n" +
                "    \"id\": \"538794\"\n" +
                "  }";
        Supplier<Measurement> factory = () -> JsonMapper.from(json, Measurement.class);
        container = factory.get();
        run(container);
        Assertions.assertEquals(1,1);
    }
}
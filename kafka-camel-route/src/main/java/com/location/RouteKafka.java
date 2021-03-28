package com.location;

import java.util.Properties;
import org.apache.camel.CamelContext;


public class RouteKafka extends org.apache.camel.builder.RouteBuilder {

    @Override
    public void configure() throws java.lang.Exception {
        doConfig();
    }

    public void doConfig() throws Exception {
        final /* org.apache.camel.model.Model */ CamelContext camelContext = getContext();

        final org.apache.camel.impl.SimpleRegistry registry = new org.apache.camel.impl.SimpleRegistry();
        final org.apache.camel.impl.CompositeRegistry compositeRegistry = new org.apache.camel.impl.CompositeRegistry();
        compositeRegistry.addRegistry(camelContext.getRegistry());
        compositeRegistry.addRegistry(registry);
        ((org.apache.camel.impl.DefaultCamelContext) camelContext).setRegistry(compositeRegistry);

        from("kafka:location-events" + "?brokers=localhost:9092" + "&connectionMaxIdleMs=540000"
                + "&receiveBufferBytes=1048576" + "&metadataMaxAgeMs=300000" + "&reconnectBackoffMs=50"
                + "&securityProtocol=PLAINTEXT" + "&retryBackoffMs=100" + "&autoCommitEnable=" + true
                + "&autoCommitIntervalMs=5000" + "&fetchMinBytes=1" + "&fetchWaitMaxMs=100" + "&autoOffsetReset=latest"
                + "&heartbeatIntervalMs=3000" + "&maxPartitionFetchBytes=1048576" + "&sessionTimeoutMs=30000"
                + "&partitionAssignor=org.apache.kafka.clients.consumer.RangeAssignor"
                + "&consumerRequestTimeoutMs=40000").routeId("route_Kafka_1")
                .process(new org.apache.camel.Processor() {
                    public void process(org.apache.camel.Exchange exchange) throws Exception {
                        RuleRunner ruleRunner = new RuleRunner();
                        Supplier<Measurement> factory = () -> JsonMapper.from(exchange.getIn().getBody().toString(),
                                Measurement.class);
                        Measurement container = factory.get();
                        Measurement stdcontainer = (Measurement) ruleRunner.run(container);

                        // convert standardised object to string

                        exchange.getOut().setBody(JsonMapper.json(stdcontainer));
                    }

                }).id("route_Processor_1").to("log:route_Log_1" + "?level=WARN")

                .id("route_Log_1");
    }

    private org.apache.camel.main.Main main;

    private void run() throws java.lang.Exception {
        main = new org.apache.camel.main.Main() {

            protected CamelContext createContext() {
                final org.apache.camel.impl.DefaultCamelContext camelContext = new org.apache.camel.spring.SpringCamelContext(
                        new org.springframework.context.support.ClassPathXmlApplicationContext(
                                "META-INF/spring/RouteKafka.xml"));
                camelContext.setName("RouteKafka");
                // add notifier
                java.util.Collection<org.apache.camel.management.JmxNotificationEventNotifier> jmxEventNotifiers = camelContext
                        .getRegistry().findByType(org.apache.camel.management.JmxNotificationEventNotifier.class);
                if (jmxEventNotifiers != null && !jmxEventNotifiers.isEmpty()) {
                    camelContext.getManagementStrategy().addEventNotifier(jmxEventNotifiers.iterator().next());
                }
                return camelContext;
            }
        };

        // add route
        main.addRouteBuilder(this);

        main.run();
    }

    public void stop() throws java.lang.Exception {
        if (main != null) {
            main.stop();
        }
    }

    public void shutdown() throws java.lang.Exception {
        if (main != null) {
            main.shutdown();
        }
    }

    public static void main(String[] args) {
        int exitCode = new RouteKafka().runJob(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    public int runJob(String[] args) {
        try {
            run();
        } catch (java.lang.Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}


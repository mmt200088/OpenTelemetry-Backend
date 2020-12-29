package com.tongji.javaEE.Service.Metrics;

import io.opentelemetry.api.common.Labels;
import io.opentelemetry.api.metrics.GlobalMetricsProvider;
import io.opentelemetry.api.metrics.LongValueObserver;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.MeterProvider;
import io.opentelemetry.exporter.prometheus.PrometheusCollector;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
/**
 * Example of using the {@link PrometheusCollector} to convert OTel metrics to Prometheus format and
 * expose these to a Prometheus instance via a {@link HTTPServer} exporter.
 *
 * <p>A {@link LongValueObserver} is used to periodically measure how many incoming messages are
 * awaiting processing. The {@link LongValueObserver} Updater gets executed every collection
 * interval.
 * @author Raven
 */
public class ValueObserver {

    private final MeterProvider meterSdkProvider = GlobalMetricsProvider.get();
    private final Meter meter = meterSdkProvider.get("PrometheusExample", "0.13.1");
    private final HTTPServer server;
    public long incomingMessageCount;

    public ValueObserver(HTTPServer server) throws IOException {
        LongValueObserver observer =
                meter
                        .longValueObserverBuilder("incoming.messages")
                        .setDescription("No of incoming messages awaiting processing")
                        .setUnit("message")
                        .setUpdater(result -> result.observe(incomingMessageCount, Labels.empty()))
                        .build();
        this.server = server;
    }
}

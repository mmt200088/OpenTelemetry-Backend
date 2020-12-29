package com.tongji.javaEE.Service.Metrics;

import io.opentelemetry.api.DefaultOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Labels;
import io.opentelemetry.api.metrics.GlobalMetricsProvider;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongCounter.BoundLongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.MeterProvider;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.prometheus.PrometheusCollector;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;

/**
 * @author Raven
 */
public class LongCounterSet {
    private static final OpenTelemetry openTelemetry = DefaultOpenTelemetry.builder().build();
    private static final Tracer tracer =
            openTelemetry.getTracer("io.opentelemetry.example.metrics", "0.13.1");

    private final MeterProvider meterSdkProvider = GlobalMetricsProvider.get();
    private final Meter sampleMeter = meterSdkProvider.get("PrometheusExample", "0.13.1");
    private final HTTPServer server;
    public LongCounter directoryCounter =
            sampleMeter
                    .longCounterBuilder("directories_access_count")
                    .setDescription("Counts directories accessed.")
                    .setUnit("unit")
                    .build();


    public LongCounterSet(HTTPServer server) throws IOException {

        BoundLongCounter homeDirectoryCounter =
                directoryCounter.bind(Labels.of("root api", "test"));

        PrometheusCollector.builder()
                .setMetricProducer(((SdkMeterProvider) meterSdkProvider).getMetricProducer())
                .buildAndRegister();

        this.server = server;
    }
}

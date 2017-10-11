package io.bandit.limbo.limbo.infrastructure.metrics.config;

import io.micrometer.influx.InfluxConfig;

import java.time.Duration;

public class InfluxMetricsConfig implements InfluxConfig {
    private final String db;
    private final String username;
    private final String password;
    private long seconds;

    public InfluxMetricsConfig(final String db, final String username, final String password, final long seconds) {
        this.db = db;
        this.username = username;
        this.password = password;
        this.seconds = seconds;
    }

    @Override
    public String get(String s) {
        return null;
    }

    @Override
    public String db() {
        return this.db;
    }

    @Override
    public String userName() {
        return this.username;
    }

    @Override
    public String password() {
        return this.password;
    }

    @Override
    public Duration step() {
        return Duration.ofSeconds(this.seconds);
    }
}

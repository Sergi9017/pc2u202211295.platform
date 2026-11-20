package com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects;

public record PlanCode(String code) {
    public PlanCode {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Plan code must not be null or blank");
        }
    }
}

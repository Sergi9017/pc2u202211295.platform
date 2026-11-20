package com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects;

public record SampleVolume(Integer quantity, String unit) {
    public SampleVolume {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Sample volume quantity must be greater than zero");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Sample volume unit must not be null or blank");
        }
    }

    public SampleVolume addVolume(Integer additionalVolume) {
        if (additionalVolume == null || additionalVolume <= 0) {
            throw new IllegalArgumentException("Additional volume must be greater than zero");
        }
        try {
            return new SampleVolume(Math.addExact(quantity, additionalVolume), unit);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException("Resulting sample volume exceeds the maximum allowed value", exception);
        }
    }
}

package com.aquamonitor.platform.u202211295.monitoring.domain.exceptions;

public class DuplicatedPlanCodeException extends RuntimeException {
    private final String planCode;

    public DuplicatedPlanCodeException(String planCode, Throwable cause) {
        super("A sampling plan with plan code '%s' already exists".formatted(planCode), cause);
        this.planCode = planCode;
    }

    public String getPlanCode() {
        return planCode;
    }
}

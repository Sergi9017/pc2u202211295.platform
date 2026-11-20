package com.aquamonitor.platform.u202211295.monitoring.domain.model.aggregates;

import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.*;
import com.aquamonitor.platform.u202211295.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.Instant;

@Getter
public class SamplingPlan extends AbstractDomainAggregateRoot<SamplingPlan> {
    public static final int MIN_PRIORITY_LEVEL = 1;

    public static final int MAX_PRIORITY_LEVEL = 5;

    private Long id;
    private PlanCode planCode;
    private StationId stationId;
    private SamplingWindow samplingWindow;
    private SampleVolume sampleVolume;
    private Integer priorityLevel;
    private SamplingPlanStatus status;
    private String samplingNotes;
    private Instant createdAt;
    private Instant updatedAt;

    public SamplingPlan(PlanCode planCode,
                        StationId stationId,
                        SamplingWindow samplingWindow,
                        SampleVolume sampleVolume,
                        Integer priorityLevel,
                        SamplingPlanStatus status,
                        String samplingNotes) {
        this.planCode = requireValue(planCode, "Plan code must not be null");
        this.stationId = requireValue(stationId, "Station id must not be null");
        this.samplingWindow = requireValue(samplingWindow, "Sampling window must not be null");
        this.sampleVolume = requireValue(sampleVolume, "Sample volume must not be null");
        this.priorityLevel = validatePriorityLevel(priorityLevel);
        this.status = status != null ? status : SamplingPlanStatus.PENDING;
        this.samplingNotes = samplingNotes;
    }

    public static SamplingPlan reconstitute(Long id,
                                            PlanCode planCode,
                                            StationId stationId,
                                            SamplingWindow samplingWindow,
                                            SampleVolume sampleVolume,
                                            Integer priorityLevel,
                                            SamplingPlanStatus status,
                                            String samplingNotes,
                                            Instant createdAt,
                                            Instant updatedAt) {
        var samplingPlan = new SamplingPlan(planCode, stationId, samplingWindow, sampleVolume,
                priorityLevel, status, samplingNotes);
        samplingPlan.id = id;
        samplingPlan.createdAt = createdAt;
        samplingPlan.updatedAt = updatedAt;
        return samplingPlan;
    }

    public String getPlanCodeValue() {
        return planCode.code();
    }

    private static Integer validatePriorityLevel(Integer priorityLevel) {
        if (priorityLevel == null) {
            throw new IllegalArgumentException("Priority level must not be null");
        }
        if (priorityLevel < MIN_PRIORITY_LEVEL || priorityLevel > MAX_PRIORITY_LEVEL) {
            throw new IllegalArgumentException("Priority level must be between %d and %d"
                    .formatted(MIN_PRIORITY_LEVEL, MAX_PRIORITY_LEVEL));
        }
        return priorityLevel;
    }

    private static <T> T requireValue(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}

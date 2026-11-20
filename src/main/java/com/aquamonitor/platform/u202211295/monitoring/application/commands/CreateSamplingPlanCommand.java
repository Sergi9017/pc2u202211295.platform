package com.aquamonitor.platform.u202211295.monitoring.application.commands;

import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.SamplingPlanStatus;

import java.time.LocalDate;
import java.util.UUID;

public record CreateSamplingPlanCommand(String planCode,
                                        UUID stationId,
                                        LocalDate samplingWindowStartDate,
                                        LocalDate samplingWindowEndDate,
                                        Integer sampleVolumeQuantity,
                                        String sampleVolumeUnit,
                                        Integer priorityLevel,
                                        SamplingPlanStatus status,
                                        String samplingNotes) {
}

package com.aquamonitor.platform.u202211295.monitoring.interfaces.rest.transform;

import com.aquamonitor.platform.u202211295.monitoring.application.commands.CreateSamplingPlanCommand;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.SamplingPlanStatus;
import com.aquamonitor.platform.u202211295.monitoring.interfaces.rest.resources.CreateSamplingPlanResource;

import java.util.UUID;

public class CreateSamplingPlanCommandFromResourceAssembler {
    public static CreateSamplingPlanCommand toCommandFromResource(CreateSamplingPlanResource resource) {
        return new CreateSamplingPlanCommand(
                resource.getPlanCode(),
                UUID.fromString(resource.getStationId()),
                resource.getSamplingWindowStartDate(),
                resource.getSamplingWindowEndDate(),
                resource.getSampleVolumeQuantity(),
                resource.getSampleVolumeUnit(),
                resource.getPriorityLevel(),
                toStatusFromResource(resource),
                resource.getSamplingNotes());
    }

    private static SamplingPlanStatus toStatusFromResource(CreateSamplingPlanResource resource) {
        if (!resource.isStatusProvided()) {
            return null;
        }
        if (resource.getStatus() == null) {
            throw new IllegalArgumentException("Status must not be null when it is sent");
        }
        return SamplingPlanStatus.valueOf(resource.getStatus());
    }
}

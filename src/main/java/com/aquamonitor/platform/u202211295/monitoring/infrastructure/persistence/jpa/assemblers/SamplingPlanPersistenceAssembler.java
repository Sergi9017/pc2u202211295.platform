package com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.assemblers;

import com.aquamonitor.platform.u202211295.monitoring.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.SampleVolume;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.SamplingWindow;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.embeddables.SampleVolumePersistenceEmbeddable;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.embeddables.SamplingWindowPersistenceEmbeddable;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.entities.SamplingPlanPersistenceEntity;

public final class SamplingPlanPersistenceAssembler {
    private SamplingPlanPersistenceAssembler() {
    }

    public static SamplingPlan toDomainFromPersistence(SamplingPlanPersistenceEntity entity) {
        if (entity == null) return null;
        return SamplingPlan.reconstitute(
                entity.getId(),
                entity.getPlanCode(),
                entity.getStationId(),
                toDomainFromPersistence(entity.getSamplingWindow()),
                toDomainFromPersistence(entity.getSampleVolume()),
                entity.getPriorityLevel(),
                entity.getStatus(),
                entity.getSamplingNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static SamplingPlanPersistenceEntity toPersistenceFromDomain(SamplingPlan samplingPlan) {
        var entity = new SamplingPlanPersistenceEntity();
        entity.setId(samplingPlan.getId());
        entity.setPlanCode(samplingPlan.getPlanCode());
        entity.setStationId(samplingPlan.getStationId());
        entity.setSamplingWindow(toPersistenceFromDomain(samplingPlan.getSamplingWindow()));
        entity.setSampleVolume(toPersistenceFromDomain(samplingPlan.getSampleVolume()));
        entity.setPriorityLevel(samplingPlan.getPriorityLevel());
        entity.setStatus(samplingPlan.getStatus());
        entity.setSamplingNotes(samplingPlan.getSamplingNotes());
        return entity;
    }

    private static SamplingWindow toDomainFromPersistence(SamplingWindowPersistenceEmbeddable value) {
        return value == null ? null : new SamplingWindow(value.getStartDate(), value.getEndDate());
    }

    private static SampleVolume toDomainFromPersistence(SampleVolumePersistenceEmbeddable value) {
        return value == null ? null : new SampleVolume(value.getQuantity(), value.getUnit());
    }

    private static SamplingWindowPersistenceEmbeddable toPersistenceFromDomain(SamplingWindow value) {
        return value == null ? null : new SamplingWindowPersistenceEmbeddable(value.startDate(), value.endDate());
    }

    private static SampleVolumePersistenceEmbeddable toPersistenceFromDomain(SampleVolume value) {
        return value == null ? null : new SampleVolumePersistenceEmbeddable(value.quantity(), value.unit());
    }
}

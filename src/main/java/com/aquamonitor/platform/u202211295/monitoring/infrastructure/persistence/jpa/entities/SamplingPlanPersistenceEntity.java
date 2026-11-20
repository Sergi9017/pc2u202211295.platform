package com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.entities;

import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.PlanCode;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.SamplingPlanStatus;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.StationId;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.converters.PlanCodePersistenceConverter;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.converters.StationIdPersistenceConverter;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.embeddables.SampleVolumePersistenceEmbeddable;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.embeddables.SamplingWindowPersistenceEmbeddable;
import com.aquamonitor.platform.u202211295.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sampling_plans",
        uniqueConstraints = @UniqueConstraint(
                name = SamplingPlanPersistenceEntity.PLAN_CODE_UNIQUE_CONSTRAINT,
                columnNames = "plan_code"))
@Getter
@Setter
@NoArgsConstructor
public class SamplingPlanPersistenceEntity extends AuditableAbstractPersistenceEntity {
    public static final String PLAN_CODE_UNIQUE_CONSTRAINT = "uk_sampling_plans_plan_code";

    @Convert(converter = PlanCodePersistenceConverter.class)
    @Column(name = "plan_code", nullable = false, length = 50)
    private PlanCode planCode;

    @Convert(converter = StationIdPersistenceConverter.class)
    @Column(name = "station_id", nullable = false, length = 36)
    private StationId stationId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startDate", column = @Column(name = "sampling_window_start_date", nullable = false)),
            @AttributeOverride(name = "endDate", column = @Column(name = "sampling_window_end_date", nullable = false))})
    private SamplingWindowPersistenceEmbeddable samplingWindow;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "quantity", column = @Column(name = "sample_volume_quantity", nullable = false)),
            @AttributeOverride(name = "unit", column = @Column(name = "sample_volume_unit", nullable = false, length = 30))})
    private SampleVolumePersistenceEmbeddable sampleVolume;

    @Column(name = "priority_level", nullable = false)
    private Integer priorityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SamplingPlanStatus status;

    @Column(name = "sampling_notes", length = 500)
    private String samplingNotes;
}

package com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SampleVolumePersistenceEmbeddable {
    @Column(name = "sample_volume_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "sample_volume_unit", nullable = false, length = 30)
    private String unit;
}

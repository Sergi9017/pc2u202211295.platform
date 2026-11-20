package com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SamplingWindowPersistenceEmbeddable {
    @Column(name = "sampling_window_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "sampling_window_end_date", nullable = false)
    private LocalDate endDate;
}

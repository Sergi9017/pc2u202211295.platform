package com.aquamonitor.platform.u202211295.monitoring.domain.repositories;

import com.aquamonitor.platform.u202211295.monitoring.domain.exceptions.DuplicatedPlanCodeException;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.PlanCode;

public interface SamplingPlanRepository {
    SamplingPlan save(SamplingPlan samplingPlan);

    boolean existsByPlanCode(PlanCode planCode);
}

package com.aquamonitor.platform.u202211295.monitoring.application.commandservices;

import com.aquamonitor.platform.u202211295.monitoring.application.commands.CreateSamplingPlanCommand;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u202211295.shared.application.result.ApplicationError;
import com.aquamonitor.platform.u202211295.shared.application.result.Result;

public interface SamplingPlanCommandService {
    Result<SamplingPlan, ApplicationError> handle(CreateSamplingPlanCommand command);
}

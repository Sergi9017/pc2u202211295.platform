package com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.adapters;

import com.aquamonitor.platform.u202211295.monitoring.domain.exceptions.DuplicatedPlanCodeException;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u202211295.monitoring.domain.model.valueobjects.PlanCode;
import com.aquamonitor.platform.u202211295.monitoring.domain.repositories.SamplingPlanRepository;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.assemblers.SamplingPlanPersistenceAssembler;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.entities.SamplingPlanPersistenceEntity;
import com.aquamonitor.platform.u202211295.monitoring.infrastructure.persistence.jpa.repositories.SamplingPlanPersistenceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public class SamplingPlanRepositoryImpl implements SamplingPlanRepository {
    private final SamplingPlanPersistenceRepository samplingPlanPersistenceRepository;

    public SamplingPlanRepositoryImpl(SamplingPlanPersistenceRepository samplingPlanPersistenceRepository) {
        this.samplingPlanPersistenceRepository = samplingPlanPersistenceRepository;
    }

    @Override
    public SamplingPlan save(SamplingPlan samplingPlan) {
        try {
            var savedEntity = samplingPlanPersistenceRepository.saveAndFlush(
                    SamplingPlanPersistenceAssembler.toPersistenceFromDomain(samplingPlan));
            return SamplingPlanPersistenceAssembler.toDomainFromPersistence(savedEntity);
        } catch (DataIntegrityViolationException exception) {
            if (isPlanCodeUniqueViolation(exception)) {
                throw new DuplicatedPlanCodeException(samplingPlan.getPlanCodeValue(), exception);
            }
            throw exception;
        }
    }

    @Override
    public boolean existsByPlanCode(PlanCode planCode) {
        return samplingPlanPersistenceRepository.countByPlanCode(planCode) > 0;
    }

    private boolean isPlanCodeUniqueViolation(DataIntegrityViolationException exception) {
        var constraintName = SamplingPlanPersistenceEntity.PLAN_CODE_UNIQUE_CONSTRAINT;
        var message = String.valueOf(exception.getMessage()).toLowerCase(Locale.ROOT);
        var causeMessage = String.valueOf(exception.getMostSpecificCause().getMessage()).toLowerCase(Locale.ROOT);
        return message.contains(constraintName) || causeMessage.contains(constraintName);
    }
}

package com.aquamonitor.platform.u202211295.monitoring.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "SamplingPlanResponse",
        description = "Sampling plan information response",
        example = "{\"id\": 1, \"planCode\": \"SAM-001\", \"stationId\": \"550e8400-e29b-41d4-a716-446655440000\", "
                + "\"samplingWindowStartDate\": \"2026-11-21\", \"samplingWindowEndDate\": \"2026-11-25\", "
                + "\"sampleVolumeQuantity\": 10, \"sampleVolumeUnit\": \"LITER\", \"priorityLevel\": 3, "
                + "\"status\": \"PENDING\", \"samplingNotes\": \"Water quality sampling for river monitoring\"}"
)
public record SamplingPlanResource(
        @Schema(description = "Sampling plan unique identifier", example = "1")
        Long id,

        @Schema(description = "Unique sampling plan code", example = "SAM-001")
        String planCode,

        @Schema(description = "Monitoring station identifier (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
        String stationId,

        @Schema(description = "Sampling window start date (yyyy-MM-dd)", example = "2026-11-21")
        String samplingWindowStartDate,

        @Schema(description = "Sampling window end date (yyyy-MM-dd)", example = "2026-11-25")
        String samplingWindowEndDate,

        @Schema(description = "Volume of water to collect", example = "10")
        Integer sampleVolumeQuantity,

        @Schema(description = "Unit of measure of the sample volume", example = "LITER")
        String sampleVolumeUnit,

        @Schema(description = "Priority level, from 1 (lowest) to 5 (highest)", example = "3")
        Integer priorityLevel,

        @Schema(description = "Sampling plan status", example = "PENDING")
        String status,

        @Schema(description = "Sampling notes", example = "Water quality sampling for river monitoring")
        String samplingNotes
) {
}

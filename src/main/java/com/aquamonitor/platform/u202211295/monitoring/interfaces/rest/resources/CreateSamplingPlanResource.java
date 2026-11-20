package com.aquamonitor.platform.u202211295.monitoring.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Schema(
        name = "CreateSamplingPlanRequest",
        description = "Request payload for registering a new water sampling plan",
        example = "{\"planCode\": \"SAM-001\", \"stationId\": \"550e8400-e29b-41d4-a716-446655440000\", "
                + "\"samplingWindowStartDate\": \"2026-11-21\", \"samplingWindowEndDate\": \"2026-11-25\", "
                + "\"sampleVolumeQuantity\": 10, \"sampleVolumeUnit\": \"LITER\", \"priorityLevel\": 3, "
                + "\"status\": \"PENDING\", \"samplingNotes\": \"Water quality sampling for river monitoring\"}"
)
@Getter
@Setter
@NoArgsConstructor
public class CreateSamplingPlanResource {
    public static final String UUID_PATTERN =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static final String STATUS_PATTERN = "^(PENDING|APPROVED|COMPLETED|CANCELLED)$";

    @NotBlank(message = "{validation.plan-code.not-blank}")
    @Size(max = 50, message = "{validation.plan-code.size}")
    @Schema(description = "Unique sampling plan code", example = "SAM-001", maxLength = 50)
    private String planCode;

    @NotBlank(message = "{validation.station-id.not-blank}")
    @Pattern(regexp = UUID_PATTERN, message = "{validation.station-id.uuid}")
    @Schema(description = "Monitoring station identifier (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
    private String stationId;

    @NotNull(message = "{validation.sampling-window-start-date.not-null}")
    @Schema(description = "Sampling window start date (ISO 8601, yyyy-MM-dd)", example = "2026-11-21", type = "string", format = "date")
    private LocalDate samplingWindowStartDate;

    @NotNull(message = "{validation.sampling-window-end-date.not-null}")
    @Schema(description = "Sampling window end date (ISO 8601, yyyy-MM-dd). Must be after the start date", example = "2026-11-25", type = "string", format = "date")
    private LocalDate samplingWindowEndDate;

    @NotNull(message = "{validation.sample-volume-quantity.not-null}")
    @Positive(message = "{validation.sample-volume-quantity.positive}")
    @Schema(description = "Volume of water to collect, in whole units", example = "10", minimum = "1")
    private Integer sampleVolumeQuantity;

    @NotBlank(message = "{validation.sample-volume-unit.not-blank}")
    @Size(max = 30, message = "{validation.sample-volume-unit.size}")
    @Schema(description = "Unit of measure of the sample volume", example = "LITER", maxLength = 30)
    private String sampleVolumeUnit;

    @NotNull(message = "{validation.priority-level.not-null}")
    @Min(value = 1, message = "{validation.priority-level.range}")
    @Max(value = 5, message = "{validation.priority-level.range}")
    @Schema(description = "Priority level, from 1 (lowest) to 5 (highest)", example = "3", minimum = "1", maximum = "5")
    private Integer priorityLevel;

    @Pattern(regexp = STATUS_PATTERN, message = "{validation.status.invalid}")
    @Schema(description = "Initial status. Optional: PENDING is assigned when omitted. It cannot be sent as null",
            example = "PENDING", allowableValues = {"PENDING", "APPROVED", "COMPLETED", "CANCELLED"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String status;

    @Size(max = 500, message = "{validation.sampling-notes.size}")
    @Schema(description = "Optional notes about the sampling", example = "Water quality sampling for river monitoring",
            maxLength = 500, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String samplingNotes;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean statusProvided;

    public void setStatus(String status) {
        this.status = status;
        this.statusProvided = true;
    }

    @JsonIgnore
    @AssertTrue(message = "{validation.status.not-null}")
    public boolean isStatusNotNullWhenPresent() {
        return !statusProvided || status != null;
    }

    @JsonIgnore
    @AssertTrue(message = "{validation.sampling-window.order}")
    public boolean isSamplingWindowValid() {
        if (samplingWindowStartDate == null || samplingWindowEndDate == null) {
            return true;
        }
        return samplingWindowEndDate.isAfter(samplingWindowStartDate);
    }
}

package com.aquamonitor.platform.u202211295.monitoring.interfaces.rest;

import com.aquamonitor.platform.u202211295.monitoring.application.commandservices.SamplingPlanCommandService;
import com.aquamonitor.platform.u202211295.monitoring.interfaces.rest.resources.CreateSamplingPlanResource;
import com.aquamonitor.platform.u202211295.monitoring.interfaces.rest.resources.SamplingPlanResource;
import com.aquamonitor.platform.u202211295.monitoring.interfaces.rest.transform.CreateSamplingPlanCommandFromResourceAssembler;
import com.aquamonitor.platform.u202211295.monitoring.interfaces.rest.transform.SamplingPlanResourceFromEntityAssembler;
import com.aquamonitor.platform.u202211295.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/sampling-plans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sampling Plans", description = "Water sampling plan management endpoints")
public class SamplingPlansController {
    private final SamplingPlanCommandService samplingPlanCommandService;

    public SamplingPlansController(SamplingPlanCommandService samplingPlanCommandService) {
        this.samplingPlanCommandService = samplingPlanCommandService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new sampling plan",
            description = "Registers a water quality sampling plan for a monitoring station. "
                    + "Status is optional and defaults to PENDING when omitted. "
                    + "Error messages are localized in English (default) or Spanish according to Accept-Language.",
            parameters = @Parameter(in = ParameterIn.HEADER, name = "Accept-Language",
                    description = "Preferred language for messages (en, en-US, es, es-PE)",
                    example = "en", schema = @Schema(type = "string"))
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Sampling plan created successfully",
                    content = @Content(schema = @Schema(implementation = SamplingPlanResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - a sampling plan with the same plan code already exists"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    public ResponseEntity<?> createSamplingPlan(@Valid @RequestBody CreateSamplingPlanResource resource) {
        var createSamplingPlanCommand = CreateSamplingPlanCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = samplingPlanCommandService.handle(createSamplingPlanCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SamplingPlanResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}

package com.boleia.boleia.entity.infra.http;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boleia.boleia.entity.application.AvailableVehicle;
import com.boleia.boleia.entity.application.NonAvailableVehicle;
import com.boleia.boleia.entity.application.RegisterVehicle;
import com.boleia.boleia.entity.application.SuspendVehicle;
import com.boleia.boleia.entity.application.VehicleFinder;
import com.boleia.boleia.entity.domain.DriverNotFoundError;
import com.boleia.boleia.entity.domain.VehicleIsAlreadyExistsError;
import com.boleia.boleia.entity.domain.VehicleNotFoundError;
import com.boleia.boleia.entity.domain.VehicleOutput;
import com.boleia.boleia.shared.types.HttpResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/boleia/api/v1")
@Tag(name = "Vehicle", description = "Vehicle management")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleFinder finder;
    private final RegisterVehicle registerVehicle;
    private final VehicleInputMapper inputMapper;
    private final AvailableVehicle availableVehicle;
    private final NonAvailableVehicle nonavailableVehicle;
    private final SuspendVehicle suspendVehicle;
    
    @PostMapping("/vehicle")
    @Operation(
        summary = "Register a vehicle",
        responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput"))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> create(@RequestBody RegisterVehicleRequest body) {
        var input = inputMapper.toRegisterVehicleInput(body);
        var out = registerVehicle.execute(input);

        if(out.isError() && out.unwrapError().getClass().equals(VehicleNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(VehicleIsAlreadyExistsError.class)) return HttpResponse.conflict(out.unwrapError().getMsg());
        if(out.isError() && out.unwrapError().getClass().equals(DriverNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        if(out.isError()) return HttpResponse.serverError(out.unwrapError().getMsg());

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/vehicle/{id}")
    @Operation(
        summary = "Get vehicle by id",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput", implementation = VehicleOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findById(@PathVariable String id) {
        var out = finder.findById(id);
        if(out.isError() && out.unwrapError().getClass().equals(VehicleNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        
        return ResponseEntity.ok(out.unwrap());
    }


    @GetMapping("/vehicle/plate/{plate}")
    @Operation(
        summary = "Get vehicle by plate",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput", implementation = VehicleOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findByPlate(@PathVariable String plate) {
        var out = finder.findByPlate(plate);
        if(out.isError() && out.unwrapError().getClass().equals(VehicleNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());

        return ResponseEntity.ok(out.unwrap());

    }


    @GetMapping("/vehicle")
    @Operation(
        summary = "Get All vehicle",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput", implementation = VehicleOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<List<VehicleOutput>> findAll() {
        var out = finder.findAll();
        return ResponseEntity.ok(out.unwrap());
    }

    @PatchMapping("/vehicle/{id}/available")
    @Operation(
        summary = "Available vehicle",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput", implementation = VehicleOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> available(@PathVariable String id) {
        var out = this.availableVehicle.execute(UUID.fromString(id));
        if(out.isError() && out.unwrapError().getClass().equals(VehicleNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        
        return ResponseEntity.ok(out.unwrap());
    }

    @PatchMapping("/vehicle/{id}/nonavailable")
    @Operation(
        summary = "Available vehicle",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput", implementation = VehicleOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> nonavailable(@PathVariable String id) {
        var out = this.nonavailableVehicle.execute(UUID.fromString(id));
        if(out.isError() && out.unwrapError().getClass().equals(VehicleNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        
        return ResponseEntity.ok(out.unwrap());
    }

    @PatchMapping("/vehicle/{id}/suspend")
    @Operation(
        summary = "Available vehicle",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput", implementation = VehicleOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> suspend(@PathVariable String id) {
        var out = this.suspendVehicle.execute(UUID.fromString(id));
        if(out.isError() && out.unwrapError().getClass().equals(VehicleNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        
        return ResponseEntity.ok(out.unwrap());
    }

    @GetMapping("/vehicle/driver/{driverId}")
    @Operation(
        summary = "Get vehicle by driver",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput", implementation = VehicleOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findByDriverId(@PathVariable String driverId) {
        var out = finder.findByDriver(driverId);
        if(out.isError() && out.unwrapError().getClass().equals(VehicleNotFoundError.class)) return HttpResponse.notFound(out.unwrapError().getMsg());
        
        return ResponseEntity.ok(out.unwrap());
    }


    @GetMapping("/vehicle/driver/all/{driverId}")
    @Operation(
        summary = "Get all vehicles by driver",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(name = "VehicleOutput", implementation = VehicleOutput.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
            @ApiResponse(responseCode = "404",content = @Content(mediaType = "application/json",schema = @Schema(name = "ErrorResponse",implementation = HttpResponse.class))),
        }
    )
    public ResponseEntity<?> findAllByDriver(@PathVariable String driverId) {
        var out = finder.findAllByDriver(driverId);
        return ResponseEntity.ok(out.unwrap());
    }

    
}

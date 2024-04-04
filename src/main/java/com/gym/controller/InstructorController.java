package com.gym.controller;


import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.mapper.InstructorMapper;
import com.gym.requestDto.instructorRequest.CreateInstructorRequestDto;
import com.gym.requestDto.instructorRequest.UpdateInstructorProfileRequestDto;
import com.gym.responseDto.instructorResponse.CreateInstructorResponseDto;
import com.gym.responseDto.instructorResponse.GetInstructorProfileResponseDto;
import com.gym.responseDto.instructorResponse.GetNotAssignedOnCustomerInstructorsResponseDto;
import com.gym.responseDto.instructorResponse.UpdateInstructorProfileResponseDto;
import com.gym.service.AuthenticationService;
import com.gym.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
@Tag(name = "InstructorController", description = "API for Instructor operations")
public class InstructorController {
    private final InstructorService instructorService;
    private final InstructorMapper instructorMapper;
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    @Operation(summary = "Create an instructor", description = "Creates a new instructor and returns their data")
    public ResponseEntity<CreateInstructorResponseDto> createInstructor(
        @Valid @RequestBody CreateInstructorRequestDto instructorDto) {
        GymUserEntity userEntity = instructorMapper.mapCreateInstructorRequestDtoToUserEntity(instructorDto);
        InstructorEntity savedInstructor = instructorService.createInstructor(userEntity,
            instructorDto.getSpecialization());
        return new ResponseEntity<>(instructorMapper.mapToResponseDto(savedInstructor.getGymUserEntity()),
            HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get instructor data", description = "Returns the data of an instructor by their username")
    public ResponseEntity<GetInstructorProfileResponseDto> getInstructor(@PathVariable String username,
                                                                         @RequestParam String loginUserName,
                                                                         @RequestParam String loginPassword) {
        authenticationService.matchInstructorCredentials(loginUserName, loginPassword);
        InstructorEntity instructorEntity = instructorService.getInstructorByUsername(username);
        return new ResponseEntity<>(instructorMapper.mapInstructorEntityToGetInstructorResponseDto(instructorEntity),
            HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update instructor data", description =
        "Updates an instructor's data and returns the updated data")
    public ResponseEntity<UpdateInstructorProfileResponseDto> updateInstructor(
        @Valid @RequestBody UpdateInstructorProfileRequestDto newData,
        @RequestParam String loginUserName,
        @RequestParam String loginPassword) {
        authenticationService.matchInstructorCredentials(loginUserName, loginPassword);
        GymUserEntity gymUserEntityFromNewData = instructorMapper.mapUpdateInstructorRequestDtoToUserEntity(newData);
        InstructorEntity updatedInstructor = instructorService.updateInstructor(gymUserEntityFromNewData,
            newData.getSpecialization());
        return new ResponseEntity<>(
            instructorMapper.mapInstructorEntityToUpdateInstructorResponseDto(updatedInstructor),
            HttpStatus.OK);
    }

    @GetMapping("/unassigned-trainers/{username}")
    @Operation(summary = "Get unassigned instructors", description =
        "Returns a list of instructors not assigned to a specific customer")
    public ResponseEntity<List<GetNotAssignedOnCustomerInstructorsResponseDto>> getNotAssignedInstructors(
        @PathVariable String username,
        @RequestParam String loginUserName,
        @RequestParam String loginPassword) {
        authenticationService.matchInstructorCredentials(loginUserName, loginPassword);
        List<InstructorEntity> instructorEntities =
            instructorService.getInstructorsNotAssignedToCustomerByCustomerUserName(username);
        return new ResponseEntity<>(instructorMapper.mapInstructorEntitiesToInstructorDtos(instructorEntities),
            HttpStatus.OK);
    }

    @PatchMapping("/activate/{username}")
    @Operation(summary = "Activate an instructor", description = "Changes the activation status of an instructor")
    public ResponseEntity<String> instructorActivation(@PathVariable String username,
                                                       @RequestParam String loginUserName,
                                                       @RequestParam String loginPassword) {
        authenticationService.matchInstructorCredentials(loginUserName, loginPassword);
        instructorService.changeInstructorActivity(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

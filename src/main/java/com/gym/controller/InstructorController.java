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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;
    private final InstructorMapper instructorMapper;
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<CreateInstructorResponseDto> createInstructor(
            @Valid @RequestBody CreateInstructorRequestDto instructorDto) {
        GymUserEntity userEntity = instructorMapper.mapCreateInstructorInstructorRequestDtoToUserEntity(instructorDto);
        InstructorEntity savedInstructor = instructorService.createInstructor(userEntity,
                instructorDto.getSpecialization());
        return new ResponseEntity<>(instructorMapper.mapToResponseDto(savedInstructor.getGymUserEntity()),
                HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetInstructorProfileResponseDto> getInstructor(@PathVariable String username,
                                                                         @RequestParam String loginUserName,
                                                                         @RequestParam String loginPassword) {
        if (!authenticationService.matchInstructorCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        InstructorEntity instructorEntity = instructorService.getInstructorByUsername(username);
        return new ResponseEntity<>(instructorMapper.mapInstructorEntityToGetInstructorResponseDto(instructorEntity),
                HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateInstructorProfileResponseDto> updateInstructor(
            @Valid @RequestBody UpdateInstructorProfileRequestDto newData,
            @RequestParam String loginUserName,
            @RequestParam String loginPassword) {
        if (!authenticationService.matchInstructorCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        GymUserEntity gymUserEntityFromNewData = instructorMapper.mapUpdateInstructorRequestDtoToUserEntity(newData);
        InstructorEntity updatedInstructor = instructorService.updateInstructor(gymUserEntityFromNewData,
                newData.getSpecialization());
        return new ResponseEntity<>(instructorMapper.mapInstructorEntityToUpdateInstructorResponseDto(updatedInstructor),
                HttpStatus.OK);
    }

    @GetMapping("/unassigned-trainers/{username}")
    public ResponseEntity<List<GetNotAssignedOnCustomerInstructorsResponseDto>> getNotAssignedInstructors(
            @PathVariable String username,
            @RequestParam String loginUserName,
            @RequestParam String loginPassword) {
        if (!authenticationService.matchInstructorCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        List<InstructorEntity> instructorEntities =
                instructorService.getInstructorsNotAssignedToCustomerByCustomerUserName(username);
        return new ResponseEntity<>(instructorMapper.mapInstructorEntitiesToInstructorDtos(instructorEntities),
                HttpStatus.OK);
    }

    @PatchMapping("/activate/{username}")
    public ResponseEntity<String> instructorActivation(@PathVariable String username,
                                     @RequestParam Boolean isActive,
                                     @RequestParam String loginUserName,
                                     @RequestParam String loginPassword) {
        if (!authenticationService.matchInstructorCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        instructorService.changeInstructorActivity(username, isActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

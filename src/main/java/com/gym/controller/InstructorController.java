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
import lombok.RequiredArgsConstructor;
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
    public CreateInstructorResponseDto createInstructor(@RequestBody CreateInstructorRequestDto instructorDto) {
        GymUserEntity userEntity = instructorMapper.mapCreateInstructorInstructorRequestDtoToUserEntity(instructorDto);
        InstructorEntity savedInstructor = instructorService.createInstructor(userEntity, instructorDto.getSpecialization());
        return instructorMapper.mapToResponseDto(savedInstructor.getGymUserEntity());
    }

    @GetMapping("/{username}")
    public GetInstructorProfileResponseDto getInstructor(@PathVariable String username,
                                                         @RequestParam String loginUserName,
                                                         @RequestParam String loginPassword) {
        if (!authenticationService.matchInstructorCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        InstructorEntity instructorEntity = instructorService.getInstructorByUsername(username);
        return instructorMapper.mapInstructorEntityToGetInstructorResponseDto(instructorEntity);
    }

    @PutMapping("/update")
    public UpdateInstructorProfileResponseDto updateInstructor(@RequestBody UpdateInstructorProfileRequestDto newData,
                                                               @RequestParam String loginUserName,
                                                               @RequestParam String loginPassword) {
        if (!authenticationService.matchInstructorCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        GymUserEntity gymUserEntityFromNewData = instructorMapper.mapUpdateInstructorRequestDtoToUserEntity(newData);
        InstructorEntity updatedInstructor = instructorService.updateInstructor(gymUserEntityFromNewData, newData.getSpecialization());
        return instructorMapper.mapInstructorEntityToUpdateInstructorResponseDto(updatedInstructor);
    }

    @GetMapping("/unassigned-trainers/{username}")
    public List<GetNotAssignedOnCustomerInstructorsResponseDto> getNotAssignedInstructors(
            @PathVariable String username,
            @RequestParam String loginUserName,
            @RequestParam String loginPassword) {
        if (!authenticationService.matchInstructorCredentials(loginUserName, loginPassword)) {
            throw new SecurityException("Unauthorized");
        }
        List<InstructorEntity> instructorEntities = instructorService.getInstructorsNotAssignedToCustomerByCustomerUserName(
                username);
        return instructorMapper.mapInstructorEntitiesToInstructorDtos(instructorEntities);
    }
}

package com.gym.controller;


import com.gym.dto.request.instructor.CreateInstructorRequestDto;
import com.gym.dto.request.instructor.UpdateInstructorProfileRequestDto;
import com.gym.dto.response.instructor.CreateInstructorResponseDto;
import com.gym.dto.response.instructor.GetInstructorProfileResponseDto;
import com.gym.dto.response.instructor.GetNotAssignedOnCustomerInstructorsResponseDto;
import com.gym.dto.response.instructor.UpdateInstructorProfileResponseDto;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.mapper.InstructorMapper;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
@Tag(name = "InstructorController", description = "API for Instructor operations")
public class InstructorController {
    private final InstructorService instructorService;
    private final InstructorMapper instructorMapper;

    /**
     * This is @PostMapping("/create") or createInstructor method.
     * It creates a new instructor entity and maps those details into
     * a response data transfer object to be returned to the client.
     *
     * @param instructorDto The client request body containing details for instructor creation.
     * @return {@code ResponseEntity<CreateInstructorResponseDto>} The created instructor details mapped into a response data transfer object.
     */
    @PostMapping("/create")
    @Operation(summary = "Create an instructor", description = "Creates a new instructor and returns their data")
    public ResponseEntity<CreateInstructorResponseDto> createInstructor(
        @Valid @RequestBody CreateInstructorRequestDto instructorDto) {
        GymUserEntity userEntity = instructorMapper.mapCreateInstructorRequestDtoToUserEntity(instructorDto);
        String password = userEntity.getPassword();
        InstructorEntity savedInstructor = instructorService.createInstructor(userEntity,
            instructorDto.getSpecialization());
        return new ResponseEntity<>(instructorMapper.mapToResponseDto(savedInstructor.getGymUserEntity(), password),
            HttpStatus.CREATED);
    }

    /**
     * This is @GetMapping("/{username}") or getInstructor method.
     * It returns the details of an instructor entity identified by
     * the supplied username as a response data transfer object.
     *
     * @param username      The unique identifier of the instructor whose data is to be retrieved.
     * @return {@code ResponseEntity<GetInstructorProfileResponseDto>} The response data transfer object of the identified instructor.
     */
    @GetMapping("/{username}")
    @Operation(summary = "Get instructor data", description = "Returns the data of an instructor by their username")
    public ResponseEntity<GetInstructorProfileResponseDto> getInstructor(@PathVariable String username) {
        InstructorEntity instructorEntity = instructorService.getInstructorByUsername(username);
        return new ResponseEntity<>(instructorMapper.mapInstructorEntityToGetInstructorResponseDto(instructorEntity),
            HttpStatus.OK);
    }

    /**
     * This is @PutMapping("/update") or updateInstructor method.
     * It updates an instructor entity's details with the supplied new information
     * and returns the updated details as a response data transfer object.
     *
     * @param newData       New details to update into the instructor profile.
     * @return {@code ResponseEntity<UpdateInstructorProfileResponseDto>} The updated instructor details in a response data transfer object.
     */
    @PutMapping("/update")
    @Operation(summary = "Update instructor data", description =
        "Updates an instructor's data and returns the updated data")
    public ResponseEntity<UpdateInstructorProfileResponseDto> updateInstructor(
        @Valid @RequestBody UpdateInstructorProfileRequestDto newData) {
        GymUserEntity gymUserEntityFromNewData = instructorMapper.mapUpdateInstructorRequestDtoToUserEntity(newData);
        InstructorEntity updatedInstructor = instructorService.updateInstructor(gymUserEntityFromNewData,
            newData.getSpecialization());
        return new ResponseEntity<>(
            instructorMapper.mapInstructorEntityToUpdateInstructorResponseDto(updatedInstructor),
            HttpStatus.OK);
    }

    /**
     * This is @GetMapping("/unassigned-trainers/{username}") or getNotAssignedInstructors method.
     * It fetches a list of instructors who are not assigned to the specified customer.
     *
     * @param username      The username of the customer for whom instructors are to be retrieved.
     * @return {@code ResponseEntity<List<GetNotAssignedOnCustomerInstructorsResponseDto>>} The list of instructors available for assignment.
     */
    @GetMapping("/unassigned-trainers/{username}")
    @Operation(summary = "Get unassigned instructors", description =
        "Returns a list of instructors not assigned to a specific customer")
    public ResponseEntity<List<GetNotAssignedOnCustomerInstructorsResponseDto>> getNotAssignedInstructors(
        @PathVariable String username) {
        List<InstructorEntity> instructorEntities =
            instructorService.getInstructorsNotAssignedToCustomerByCustomerUserName(username);
        return new ResponseEntity<>(instructorMapper.mapInstructorEntitiesToInstructorDtos(instructorEntities),
            HttpStatus.OK);
    }

    /**
     * This is @PatchMapping("/activate/{username}") or instructorActivation method.
     * It changes the status of a specified instructor from active to inactive or vice versa.
     *
     * @param username      The username of the instructor to be activated/deactivated.
     * @return {@code ResponseEntity<String>} An HTTP status indicating the success or failure of the activation/deactivation operation.
     */
    @PatchMapping("/activate/{username}")
    @Operation(summary = "Activate an instructor", description = "Changes the activation status of an instructor")
    public ResponseEntity<String> instructorActivation(@PathVariable String username) {
        instructorService.changeInstructorActivity(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package instructorTest;

import com.gym.entity.InstructorEntity;
import com.gym.repository.InstructorRepository;
import com.gym.service.InstructorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class InstructorServiceTest {
    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private InstructorService instructorService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void createInstructorTest() {
        when(instructorRepository.getInstructorIds()).thenReturn(new HashSet<>());
        when(instructorRepository.createInstructor(any(InstructorEntity.class))).thenReturn(
                new InstructorEntity(1, "Test", "Instructor",
                        "Test.Instructor", "123", true, "Fitness")
        );

        InstructorEntity inputInstructor = new InstructorEntity();
        inputInstructor.setFirstName("Test");
        inputInstructor.setLastName("Instructor");
        inputInstructor.setPassword("123");
        inputInstructor.setIsActive(true);
        inputInstructor.setSpecialization("Fitness");
        InstructorEntity createdInstructor = instructorService.createInstructor(inputInstructor);

        assertThat(createdInstructor.getUserId()).isEqualTo(1);
        assertThat(createdInstructor.getUserName()).isEqualTo("Test.Instructor");
    }

    @Test
    public void getInstructorByIdTest() {
        when(instructorRepository.getInstructorById(1)).thenReturn
                (new InstructorEntity(1, "Test", "Instructor",
                        "Test.Customer", "123", true, "Fitness")
                );

        InstructorEntity retrievedCustomer = instructorRepository.getInstructorById(1);

        assertThat(retrievedCustomer.getUserId()).isEqualTo(1);
    }

    @Test
    public void deleteInstructorTest() {
        when(instructorRepository.getInstructorById(1)).thenReturn(
                new InstructorEntity(1, "Test", "Instructor",
                        "Test.Customer", "123", true, "Fitness"));

        instructorService.deleteInstructor(1);

        verify(instructorRepository).deleteInstructor(1);
    }

    @Test
    public void updateInstructorTest() {
        InstructorEntity existingInstructor = new InstructorEntity(1, "Test", "Instructor",
                "Test.Customer", "123", true, "Fitness");

        when(instructorRepository.getInstructorIds()).thenReturn(new HashSet<>());
        when(instructorRepository.getInstructorById(1)).thenReturn(existingInstructor);
        when(instructorRepository.updateInstructor(any(InstructorEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        InstructorEntity newData = new InstructorEntity(1, "Kek", "W",
                "Kek.W", "123", true, "Fitness");
        InstructorEntity updatedInstructor = instructorService.updateInstructor(newData);

        assertThat(updatedInstructor.getUserId()).isEqualTo(1);
        assertThat(updatedInstructor.getUserName()).isEqualTo("Kek.W");
    }

    @Test
    public void instructorNotFoundTest() {
        when(instructorRepository.getInstructorById(1)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> instructorService.updateInstructor(
                new InstructorEntity(1, "Test", "Instructor",
                        "Test.Customer", "123", true, "Fitness")));
    }
}

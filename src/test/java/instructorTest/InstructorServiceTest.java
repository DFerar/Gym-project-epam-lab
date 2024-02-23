package instructorTest;

import com.gym.entities.Instructor;
import com.gym.repositories.InstructorRepository;
import com.gym.services.InstructorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

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
        when(instructorRepository.getInstructorStorage()).thenReturn(new ConcurrentHashMap<>());
        when(instructorRepository.createInstructor(any(Instructor.class))).thenReturn(
                new Instructor(1, "Test", "Instructor",
                        "Test.Instructor", "123", true, "Fitness")
        );

        Instructor inputInstructor = new Instructor();
        inputInstructor.setFirstName("Test");
        inputInstructor.setLastName("Instructor");
        inputInstructor.setPassword("123");
        inputInstructor.setIsActive(true);
        inputInstructor.setSpecialization("Fitness");
        Instructor createdInstructor = instructorService.createInstructor(inputInstructor);

        assertThat(createdInstructor.getUserId()).isEqualTo(1);
        assertThat(createdInstructor.getUserName()).isEqualTo("Test.Instructor");
    }

    @Test
    public void getInstructorByIdTest() {
        when(instructorRepository.getInstructorById(1)).thenReturn
                (new Instructor(1, "Test", "Instructor",
                        "Test.Customer", "123", true, "Fitness")
                );

        Instructor retrievedCustomer = instructorRepository.getInstructorById(1);

        assertThat(retrievedCustomer.getUserId()).isEqualTo(1);
    }

    @Test
    public void deleteInstructorTest() {
        when(instructorRepository.getInstructorById(1)).thenReturn(
                new Instructor(1, "Test", "Instructor",
                        "Test.Customer", "123", true, "Fitness"));

        instructorService.deleteInstructor(1);

        verify(instructorRepository).deleteInstructor(1);
    }

    @Test
    public void updateInstructorTest() {
        Instructor existingInstructor = new Instructor(1, "Test", "Instructor",
                "Test.Customer", "123", true, "Fitness");

        when(instructorRepository.getInstructorStorage()).thenReturn(new ConcurrentHashMap<>());
        when(instructorRepository.getInstructorById(1)).thenReturn(existingInstructor);
        when(instructorRepository.updateInstructor(any(Instructor.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Instructor newData = new Instructor(1, "Kek", "W",
                "Kek.W", "123", true, "Fitness");
        Instructor updatedInstructor = instructorService.updateInstructor(newData);

        assertThat(updatedInstructor.getUserId()).isEqualTo(1);
        assertThat(updatedInstructor.getUserName()).isEqualTo("Kek.W");
    }

    @Test
    public void instructorNotFoundTest() {
        when(instructorRepository.getInstructorById(1)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> instructorService.updateInstructor(
                new Instructor(1, "Test", "Instructor",
                        "Test.Customer", "123", true, "Fitness")));
    }
}

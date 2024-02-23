package instructorTest;


import com.gym.entities.Instructor;
import com.gym.repositories.InstructorRepository;
import com.gym.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

public class InstructorRepositoryTests {
    @Mock
    private Storage storage;

    @InjectMocks
    private InstructorRepository instructorRepository;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void createInstructorTest() {
        Map<Integer, Instructor> instructorStorage = new ConcurrentHashMap<>();
        Mockito.when(storage.getInstructorStorage()).thenReturn(instructorStorage);

        Instructor newInstructor = new Instructor();
        newInstructor.setUserId(1);
        newInstructor.setUserName("Test.Instructor");
        newInstructor.setFirstName("Test");
        newInstructor.setLastName("Instructor");
        newInstructor.setSpecialization("Fitness");
        newInstructor.setIsActive(true);
        Instructor createdInstructor = instructorRepository.createInstructor(newInstructor);

        assertThat(createdInstructor).isEqualTo(newInstructor);
        assertThat(instructorStorage).containsKey(1);
    }

    @Test
    public void getInstructorByIdTest() {
        Map<Integer, Instructor> instructorStorage = new ConcurrentHashMap<>();
        instructorStorage.put(1, new Instructor());
        Mockito.when(storage.getInstructorStorage()).thenReturn(instructorStorage);

        Instructor retrievedInstructor = instructorRepository.getInstructorById(1);

        assertThat(retrievedInstructor).isEqualTo(instructorStorage.get(1));
    }

    @Test
    public void deleteInstructorTest() {
        Map<Integer, Instructor> instructorStorage = new ConcurrentHashMap<>();
        instructorStorage.put(1, new Instructor());
        Mockito.when(storage.getInstructorStorage()).thenReturn(instructorStorage);

        instructorRepository.deleteInstructor(1);

        assertThat(instructorStorage.containsKey(1)).isFalse();
    }

    @Test
    public void updateInstructorTest() {
        Map<Integer, Instructor> instructorStorage = new ConcurrentHashMap<>();
        Instructor updatedInstructor = new Instructor();
        updatedInstructor.setUserId(1);
        updatedInstructor.setUserName("Test.Instructor");
        updatedInstructor.setIsActive(true);
        instructorStorage.put(1, updatedInstructor);
        Mockito.when(storage.getInstructorStorage()).thenReturn(instructorStorage);

        Instructor result = instructorRepository.updateInstructor(updatedInstructor);

        assertThat(result).isEqualTo(updatedInstructor);
        assertThat(instructorStorage.get(1).getUserName()).isEqualTo("Test.Instructor");


    }

    @Test
    public void ifUserNameExistsTest() {
        Map<Integer, Instructor> instructorStorage = new ConcurrentHashMap<>();
        Instructor instructor = new Instructor();
        instructor.setUserId(1);
        instructor.setUserName("Existing.Username");
        instructorStorage.put(1, instructor);
        Mockito.when(storage.getInstructorStorage()).thenReturn(instructorStorage);

        boolean exists = instructorRepository.ifUsernameExists("Existing.Username");

        assertThat(exists).isTrue();
    }

    @Test
    public void testGetInstructorMap() {
        Map<Integer, Instructor> instructorStorage = new ConcurrentHashMap<>();
        instructorStorage.put(1, new Instructor());
        Mockito.when(storage.getInstructorStorage()).thenReturn(instructorStorage);

        Map<Integer, Instructor> result = instructorRepository.getInstructorStorage();

        assertThat(instructorStorage).isEqualTo(result);
    }


}


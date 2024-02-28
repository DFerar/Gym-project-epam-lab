package instructorTest;


import com.gym.entity.InstructorEntity;
import com.gym.repository.InstructorRepository;
import com.gym.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class InstructorRepositoryTest {
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
        InstructorEntity newInstructor = new InstructorEntity();
        newInstructor.setUserId(1);
        newInstructor.setUserName("Test.Instructor");
        newInstructor.setFirstName("Test");
        newInstructor.setLastName("Instructor");
        newInstructor.setSpecialization("Fitness");
        newInstructor.setIsActive(true);

        when(storage.addInstructor(any(InstructorEntity.class))).thenReturn(newInstructor);

        InstructorEntity createdInstructor = instructorRepository.createInstructor(newInstructor);

        assertThat(createdInstructor).isEqualTo(newInstructor);
    }

    @Test
    public void getInstructorByIdTest() {
        Map<Integer, InstructorEntity> instructorStorage = new ConcurrentHashMap<>();
        instructorStorage.put(1, new InstructorEntity());
        when(storage.getInstructorById(1)).thenReturn(instructorStorage.get(1));

        InstructorEntity retrievedInstructor = instructorRepository.getInstructorById(1);

        assertThat(retrievedInstructor).isEqualTo(instructorStorage.get(1));
    }

    @Test
    public void deleteInstructorTest() {
       Integer instructorId = 1;

       instructorRepository.deleteInstructor(1);

       verify(storage, times(1)).deleteInstructor(instructorId);
    }

    @Test
    public void updateInstructorTest() {
        InstructorEntity updatedInstructor = new InstructorEntity();
        updatedInstructor.setUserId(1);
        updatedInstructor.setUserName("Test.Instructor");
        updatedInstructor.setIsActive(true);

        InstructorEntity result = instructorRepository.updateInstructor(updatedInstructor);

        assertThat(result).isEqualTo(updatedInstructor);
    }

    @Test
    public void ifUserNameExistsTest() {
        String userName = "existingUserName";

        when(storage.checkIfInstructorUserNameExists(userName)).thenReturn(true);

        Boolean result = instructorRepository.ifUsernameExists(userName);

        assertThat(result).isTrue();
    }

    @Test
    public void testGetInstructorIds() {
        Set<Integer> instructorIds = new HashSet<>();
        when(storage.getInstructorIds()).thenReturn(instructorIds);

        Set<Integer> result = instructorRepository.getInstructorIds();

        assertThat(instructorIds).isEqualTo(result);
    }


}


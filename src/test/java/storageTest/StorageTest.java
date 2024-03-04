package storageTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.gym.entity.CustomerEntity;
import com.gym.entity.InstructorEntity;
import com.gym.entity.TrainingEntity;
import com.gym.entity.TrainingType;
import com.gym.storage.Storage;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StorageTest {
    @InjectMocks
    private Storage storage;

    @Test
    public void addCustomerTest() {
        CustomerEntity customer = new CustomerEntity(
                1, "Test", "Customer", LocalDate.parse("1990-01-01"), "test_address",
                "Test.Customer", "123", true
        );

        CustomerEntity addedCustomer = storage.addCustomer(customer);

        assertThat(storage.getCustomerById(addedCustomer.getUserId())).isEqualTo(addedCustomer);
    }

    @Test
    public void getCustomerByIdTest() {
        CustomerEntity expectedCustomer = new CustomerEntity(
                1, "Test", "Customer", LocalDate.parse("1990-01-01"), "test_address",
                "Test.Customer", "123", true
        );
        storage.addCustomer(expectedCustomer);

        CustomerEntity customer = storage.getCustomerById(expectedCustomer.getUserId());

        assertThat(customer).isEqualTo(expectedCustomer);
    }

    @Test
    public void deleteCustomerTest() {
        CustomerEntity customer = new CustomerEntity(
                1, "Test", "Customer", LocalDate.parse("1990-01-01"), "test_address",
                "Test.Customer", "123", true
        );
        storage.addCustomer(customer);

        storage.deleteCustomer(customer.getUserId());

        assertThat(storage.getCustomerById(customer.getUserId())).isNull();
    }

    @Test
    public void checkIfUserCustomerNameExistsTest() {
        String userName = "nonExistingUserName";

        Boolean result = storage.checkIfCustomerUserNameExists(userName);

        assertThat(result).isFalse();
    }

    @Test
    public void addInstructorTest() {
        InstructorEntity instructor = new InstructorEntity(1, "Test", "Instructor",
                "Test.Instructor", "123", true, "Cardio");

        InstructorEntity addedInstructor = storage.addInstructor(instructor);

        assertThat(storage.getInstructorById(addedInstructor.getUserId())).isEqualTo(addedInstructor);
    }

    @Test
    public void getInstructorByIdTest() {
        InstructorEntity expectedInstructor = new InstructorEntity(1, "Test", "Instructor",
                "Test.Instructor", "123", true, "Cardio");
        storage.addInstructor(expectedInstructor);

        InstructorEntity instructor = storage.getInstructorById(expectedInstructor.getUserId());

        assertThat(instructor).isEqualTo(expectedInstructor);
    }

    @Test
    public void deleteInstructorTest() {
        InstructorEntity instructor = new InstructorEntity(1, "Test", "Instructor",
                "Test.Instructor", "123", true, "Cardio");
        storage.addInstructor(instructor);

        storage.deleteInstructor(instructor.getUserId());

        assertThat(storage.getInstructorById(instructor.getUserId())).isNull();
    }

    @Test
    public void checkIfInstructorUserNameExistsTest() {
        String userName = "nonExistingUserName";

        Boolean result = storage.checkIfInstructorUserNameExists(userName);

        assertThat(result).isFalse();
    }

    @Test
    public void addTrainingTest() {
        TrainingEntity training = new TrainingEntity(1, 1, 1, "Test_training",
                TrainingType.CARDIO, LocalDate.parse("1990-01-01"), "1 hour");

        TrainingEntity addedTraining = storage.addTraining(training);

        assertThat(storage.getTrainingById(training.getTrainingId())).isEqualTo(addedTraining);
    }

    @Test
    public void getTrainingByIdTest() {
        TrainingEntity expectedTraining = new TrainingEntity(1, 1, 1, "Test_training",
                TrainingType.CARDIO, LocalDate.parse("1990-01-01"), "1 hour");
        storage.addTraining(expectedTraining);

        TrainingEntity training = storage.getTrainingById(expectedTraining.getTrainingId());

        assertThat(training).isEqualTo(expectedTraining);
    }

    @Test
    public void getCustomerIdsTest() {
        Set<Integer> expectedSet = new HashSet<>();
        assertThat(storage.getCustomerIds()).isEqualTo(expectedSet);
    }

    @Test
    public void getInstructorIdsTest() {
        Set<Integer> expectedSet = new HashSet<>();
        assertThat(storage.getInstructorIds()).isEqualTo(expectedSet);
    }

    @Test
    public void getTrainingsIdsTest() {
        Set<Integer> expectedSet = new HashSet<>();
        assertThat(storage.getTrainingIds()).isEqualTo(expectedSet);
    }
}

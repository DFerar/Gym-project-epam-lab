package instructorTest;

import com.gym.AppConfig;
import com.gym.GymCRMFacade;
import com.gym.customer.Customer;
import com.gym.instructor.Instructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class InstructorTest {
    @Autowired
    private GymCRMFacade gymCRMFacade;

    @Test
    void instructorTest() {
        Instructor newInstructor = new Instructor();
        newInstructor.setFirstName("Test");
        newInstructor.setLastName("Instructor");
        newInstructor.setSpecialization("Fitness");
        newInstructor.setIsActive(true);
        Instructor instructor = gymCRMFacade.createInstructor(newInstructor);

        assertNotNull(instructor);
        assertNotNull(instructor.getUserId());
        assertEquals("Test.Instructor", instructor.getUserName());
        //getThisCustomer
        Integer instructorId = instructor.getUserId();
        Instructor instructorFromBase = gymCRMFacade.getInstructorById(instructorId);

        assertNotNull(instructorFromBase);
        assertEquals(instructor.getUserId(), instructorFromBase.getUserId());
        //updateThisCustomer
        Instructor newData = new Instructor();
        newData.setUserId(instructorId);
        newData.setFirstName("Test");
        newData.setLastName("NewInstructor");
        newData.setSpecialization("Fitness");
        newData.setIsActive(false);
        Instructor updatedInstructor = gymCRMFacade.updateInstructor(newData);

        assertEquals(instructorId, updatedInstructor.getUserId());
        assertEquals("Test.NewInstructor", updatedInstructor.getUserName());
        assertFalse(updatedInstructor.getIsActive());
        //delete this customer and check exception
        gymCRMFacade.deleteInstructor(instructorId);
        assertThrows(NoSuchElementException.class, () -> gymCRMFacade.getInstructorById(instructorId));
    }
}

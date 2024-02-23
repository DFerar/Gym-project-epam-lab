package customerTests;

import com.gym.AppConfig;
import com.gym.GymCRMFacade;
import com.gym.entities.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class CustomerIntegrationTest {
    @Autowired
    private GymCRMFacade gymCRMFacade;

    @Test
    void customerTest() {
        //creating customer
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Test");
        newCustomer.setLastName("Customer");
        newCustomer.setAddress("TestAddress");
        newCustomer.setDateOfBirth("15.01.1990");
        newCustomer.setIsActive(true);
        Customer customer = gymCRMFacade.createCustomer(newCustomer);

        assertNotNull(customer);
        assertNotNull(customer.getUserId());
        assertEquals("Test.Customer", customer.getUserName());
        //getThisCustomer
        Integer customerId = customer.getUserId();
        Customer customerFromBase = gymCRMFacade.getCustomerById(customerId);

        assertNotNull(customerFromBase);
        assertEquals(customer.getUserId(), customerFromBase.getUserId());
        //updateThisCustomer
        Customer newData = new Customer();
        newData.setUserId(customerId);
        newData.setFirstName("Test");
        newData.setLastName("NewCustomer");
        newData.setAddress("NewTestAddress");
        newData.setDateOfBirth("15.01.1990");
        newData.setIsActive(false);
        Customer updatedCustomer = gymCRMFacade.updateCustomer(newData);

        assertEquals(customerId, updatedCustomer.getUserId());
        assertEquals("Test.NewCustomer", updatedCustomer.getUserName());
        assertEquals("NewTestAddress", updatedCustomer.getAddress());
        assertFalse(updatedCustomer.getIsActive());
        //delete this customer and check exception
        gymCRMFacade.deleteCustomer(customerId);
        assertThrows(NoSuchElementException.class, () -> gymCRMFacade.getCustomerById(customerId));
    }
}

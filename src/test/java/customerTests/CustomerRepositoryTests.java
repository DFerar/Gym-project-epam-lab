package customerTests;


import com.gym.entities.Customer;
import com.gym.repositories.CustomerRepository;
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

public class CustomerRepositoryTests {
    @Mock
    private Storage storage;

    @InjectMocks
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void createCustomerTest() {
        Map<Integer, Customer> customerStorage = new ConcurrentHashMap<>();
        Mockito.when(storage.getCustomerStorage()).thenReturn(customerStorage);

        Customer newCustomer = new Customer();
        newCustomer.setUserId(1);
        newCustomer.setUserName("Test.Customer");
        newCustomer.setFirstName("Test");
        newCustomer.setLastName("Customer");
        newCustomer.setAddress("TestAddress");
        newCustomer.setDateOfBirth("15.01.1990");
        newCustomer.setIsActive(true);
        Customer createdCustomer = customerRepository.createCustomer(newCustomer);

        assertThat(createdCustomer).isEqualTo(newCustomer);
        assertThat(customerStorage).containsKey(1);
    }

    @Test
    public void getCustomerByIdTest() {
        Map<Integer, Customer> customerStorage = new ConcurrentHashMap<>();
        customerStorage.put(1, new Customer());
        Mockito.when(storage.getCustomerStorage()).thenReturn(customerStorage);

        Customer retrievedCustomer = customerRepository.getCustomerById(1);

        assertThat(retrievedCustomer).isEqualTo(customerStorage.get(1));
    }

    @Test
    public void deleteCustomerTest() {
        Map<Integer, Customer> customerStorage = new ConcurrentHashMap<>();
        customerStorage.put(1, new Customer());
        Mockito.when(storage.getCustomerStorage()).thenReturn(customerStorage);

        customerRepository.deleteCustomer(1);

        assertThat(customerStorage.containsKey(1)).isFalse();
    }

    @Test
    public void updateCustomerTest() {
        Map<Integer, Customer> customerStorage = new ConcurrentHashMap<>();
        Customer updatedCustomer = new Customer();
        updatedCustomer.setUserId(1);
        updatedCustomer.setUserName("Test.Customer");
        updatedCustomer.setIsActive(true);
        customerStorage.put(1, updatedCustomer);
        Mockito.when(storage.getCustomerStorage()).thenReturn(customerStorage);

        Customer result = customerRepository.updateCustomer(updatedCustomer);

        assertThat(updatedCustomer).isEqualTo(result);
        assertThat(customerStorage.get(1).getUserName()).isEqualTo("Test.Customer");
    }

    @Test
    public void ifUserNameExistsTest() {
        Map<Integer, Customer> customerStorage = new ConcurrentHashMap<>();
        Customer customer = new Customer();
        customer.setUserId(1);
        customer.setUserName("Existing.Username");
        customerStorage.put(1, customer);
        Mockito.when(storage.getCustomerStorage()).thenReturn(customerStorage);

        boolean exists = customerRepository.ifUsernameExists("Existing.Username");

        assertThat(exists).isTrue();
    }

    @Test
    public void testGetCustomerMap() {
        Map<Integer, Customer> customerStorage = new ConcurrentHashMap<>();
        customerStorage.put(1, new Customer());
        Mockito.when(storage.getCustomerStorage()).thenReturn(customerStorage);

        Map<Integer, Customer> result = customerRepository.getCustomerMap();

        assertThat(result).isEqualTo(customerStorage);
    }
}

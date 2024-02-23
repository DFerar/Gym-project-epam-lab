package customerTests;

import com.gym.entities.Customer;
import com.gym.repositories.CustomerRepository;
import com.gym.services.CustomerService;
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

public class CustomerServiceTests {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void createCustomerTest() {
        when(customerRepository.getCustomerMap()).thenReturn(new ConcurrentHashMap<>());
        when(customerRepository.createCustomer(any(Customer.class))).thenReturn(
                new Customer(1, "Test", "Customer", "15.01.1990", "TestAddress",
                        "Test.Customer", "123", true)
        );

        Customer inputCustomer = new Customer();
        inputCustomer.setFirstName("Test");
        inputCustomer.setLastName("Customer");
        inputCustomer.setAddress("TestAddress");
        inputCustomer.setDateOfBirth("15.01.1990");
        inputCustomer.setPassword("123");
        inputCustomer.setIsActive(true);
        Customer createdCustomer = customerService.createCustomer(inputCustomer);

        assertThat(createdCustomer.getUserId()).isEqualTo(1);
        assertThat(createdCustomer.getUserName()).isEqualTo("Test.Customer");
    }

    @Test
    public void getCustomerByIdTest() {
        when(customerRepository.getCustomerById(1)).thenReturn
                (new Customer(1, "Test", "Customer", "15.01.1990", "TestAddress",
                        "Test.Customer", "123", true)
                );

        Customer retrievedCustomer = customerRepository.getCustomerById(1);

        assertThat(retrievedCustomer.getUserId()).isEqualTo(1);
    }

    @Test
    public void deleteCustomerTest() {
        when(customerRepository.getCustomerById(1)).thenReturn(
                new Customer(1, "Test", "Customer", "15.01.1990", "TestAddress",
                        "Test.Customer", "123", true));

        customerService.deleteCustomer(1);

        verify(customerRepository).deleteCustomer(1);
    }

    @Test
    public void updateCustomerTest() {
        Customer existingCustomer = new Customer(1, "Test", "Customer", "15.01.1990", "TestAddress",
                "Test.Customer", "123", true);

        when(customerRepository.getCustomerMap()).thenReturn(new ConcurrentHashMap<>());
        when(customerRepository.getCustomerById(1)).thenReturn(existingCustomer);
        when(customerRepository.updateCustomer(any(Customer.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Customer newData = new Customer(1, "Kek", "W", "15.01.1990", "TestAddress",
                "Kek.W", "123", true);
        Customer updatedCustomer = customerService.updateCustomer(newData);

        assertThat(updatedCustomer.getUserId()).isEqualTo(1);
        assertThat(updatedCustomer.getUserName()).isEqualTo("Kek.W");
    }

    @Test
    public void customerNotFoundTest() {
        when(customerRepository.getCustomerById(1)).thenReturn(null);

       assertThrows(NoSuchElementException.class, () -> customerService.updateCustomer(
               new Customer(1, "Kek", "W", "15.01.1990", "TestAddress",
                       "Kek.W", "123", true)));
    }
}

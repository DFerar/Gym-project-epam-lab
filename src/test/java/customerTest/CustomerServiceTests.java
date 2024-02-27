package customerTest;

import com.gym.entity.CustomerEntity;
import com.gym.repository.CustomerRepository;
import com.gym.service.CustomerService;
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
        when(customerRepository.getCustomerIds()).thenReturn(new HashSet<>());
        when(customerRepository.createCustomer(any(CustomerEntity.class))).thenReturn(
                new CustomerEntity(1, "Test", "Customer", "15.01.1990", "TestAddress",
                        "Test.Customer", "123", true)
        );

        CustomerEntity inputCustomer = new CustomerEntity();
        inputCustomer.setFirstName("Test");
        inputCustomer.setLastName("Customer");
        inputCustomer.setAddress("TestAddress");
        inputCustomer.setDateOfBirth("15.01.1990");
        inputCustomer.setPassword("123");
        inputCustomer.setIsActive(true);
        CustomerEntity createdCustomer = customerService.createCustomer(inputCustomer);

        assertThat(createdCustomer.getUserId()).isEqualTo(1);
        assertThat(createdCustomer.getUserName()).isEqualTo("Test.Customer");
    }

    @Test
    public void getCustomerByIdTest() {
        when(customerRepository.getCustomerById(1)).thenReturn
                (new CustomerEntity(1, "Test", "Customer", "15.01.1990", "TestAddress",
                        "Test.Customer", "123", true)
                );

        CustomerEntity retrievedCustomer = customerRepository.getCustomerById(1);

        assertThat(retrievedCustomer.getUserId()).isEqualTo(1);
    }

    @Test
    public void deleteCustomerTest() {
        when(customerRepository.getCustomerById(1)).thenReturn(
                new CustomerEntity(1, "Test", "Customer", "15.01.1990", "TestAddress",
                        "Test.Customer", "123", true));

        customerService.deleteCustomer(1);

        verify(customerRepository).deleteCustomer(1);
    }

    @Test
    public void updateCustomerTest() {
        CustomerEntity existingCustomer = new CustomerEntity(1, "Test", "Customer", "15.01.1990", "TestAddress",
                "Test.Customer", "123", true);

        when(customerRepository.getCustomerIds()).thenReturn(new HashSet<>());
        when(customerRepository.getCustomerById(1)).thenReturn(existingCustomer);
        when(customerRepository.updateCustomer(any(CustomerEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        CustomerEntity newData = new CustomerEntity(1, "Kek", "W", "15.01.1990", "TestAddress",
                "Kek.W", "123", true);
        CustomerEntity updatedCustomer = customerService.updateCustomer(newData);

        assertThat(updatedCustomer.getUserId()).isEqualTo(1);
        assertThat(updatedCustomer.getUserName()).isEqualTo("Kek.W");
    }

    @Test
    public void customerNotFoundTest() {
        when(customerRepository.getCustomerById(1)).thenReturn(null);

       assertThrows(NoSuchElementException.class, () -> customerService.updateCustomer(
               new CustomerEntity(1, "Kek", "W", "15.01.1990", "TestAddress",
                       "Kek.W", "123", true)));
    }
}

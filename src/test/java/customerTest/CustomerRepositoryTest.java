package customerTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gym.entity.CustomerEntity;
import com.gym.repository.CustomerRepository;
import com.gym.storage.Storage;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryTest {
    @Mock
    private Storage storage;

    @InjectMocks
    private CustomerRepository customerRepository;

    @Test
    public void createCustomerTest() {
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setUserId(1);
        newCustomer.setUserName("Test.Customer");
        newCustomer.setFirstName("Test");
        newCustomer.setLastName("Customer");
        newCustomer.setAddress("TestAddress");
        newCustomer.setDateOfBirth(LocalDate.parse("1990-01-01"));
        newCustomer.setIsActive(true);

        Mockito.when(storage.addCustomer(any(CustomerEntity.class))).thenReturn(newCustomer);

        CustomerEntity createdCustomer = customerRepository.createCustomer(newCustomer);

        assertThat(createdCustomer).isEqualTo(newCustomer);
    }

    @Test
    public void getCustomerByIdTest() {
        Map<Integer, CustomerEntity> customerStorage = new ConcurrentHashMap<>();
        customerStorage.put(1, new CustomerEntity());
        Mockito.when(storage.getCustomerById(1)).thenReturn(customerStorage.get(1));

        CustomerEntity retrievedCustomer = customerRepository.getCustomerById(1);

        assertThat(retrievedCustomer).isEqualTo(customerStorage.get(1));
    }

    @Test
    @SneakyThrows
    public void deleteCustomerTest() {
        Integer customerId = 1;

        customerRepository.deleteCustomer(customerId);

        verify(storage, times(1)).deleteCustomer(customerId);
        verify(storage, times(1)).updateDatasource();
    }

    @Test
    public void updateCustomerTest() {
        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setUserId(1);
        updatedCustomer.setUserName("Test.Customer");
        updatedCustomer.setIsActive(true);

        CustomerEntity result = customerRepository.updateCustomer(updatedCustomer);

        assertThat(updatedCustomer).isEqualTo(result);
    }

    @Test
    public void ifUserNameExistsTest() {
        String userName = "nonExistingUserName";
        when(storage.checkIfCustomerUserNameExists(userName)).thenReturn(false);

        Boolean result = customerRepository.ifUsernameExists(userName);

        assertThat(result).isFalse();
    }

    @Test
    public void testGetCustomerIds() {
        Set<Integer> customerIds = new HashSet<>();
        Mockito.when(storage.getCustomerIds()).thenReturn(customerIds);

       Set<Integer> result = customerRepository.getCustomerIds();

        assertThat(result).isEqualTo(customerIds);
    }
}

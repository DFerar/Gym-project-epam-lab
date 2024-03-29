package customerTest;

import com.gym.controller.CustomerController;
import com.gym.entity.CustomerEntity;
import com.gym.entity.GymUserEntity;
import com.gym.entity.InstructorEntity;
import com.gym.mapper.CustomerMapper;
import com.gym.mapper.InstructorMapper;
import com.gym.requestDto.customerRequest.CreateCustomerRequestDto;
import com.gym.requestDto.customerRequest.UpdateCustomerInstructorsRequestDto;
import com.gym.requestDto.customerRequest.UpdateCustomerProfileRequestDto;
import com.gym.responseDto.customerResponse.CreateCustomerResponseDto;
import com.gym.responseDto.customerResponse.GetCustomerProfileResponseDto;
import com.gym.responseDto.customerResponse.InstructorForCustomerResponseDto;
import com.gym.responseDto.customerResponse.UpdateCustomerProfileResponseDto;
import com.gym.service.AuthenticationService;
import com.gym.service.CustomerService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private InstructorMapper instructorMapper;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    public void shouldCreateCustomer() {
        // Given
        CreateCustomerRequestDto requestDto = new CreateCustomerRequestDto();
        CustomerEntity customerEntity = new CustomerEntity();
        when(customerMapper.mapCreateCustomerRequestDtoToUserEntity(requestDto)).thenReturn(new GymUserEntity());
        when(customerMapper.mapCreateCustomerRequestDtoToCustomerEntity(requestDto)).thenReturn(customerEntity);
        when(customerService.createCustomer(customerEntity, new GymUserEntity())).thenReturn(customerEntity);
        when(customerMapper.mapCustomerEntityToCreateResponseDto(customerEntity.getGymUserEntity())).thenReturn
                (new CreateCustomerResponseDto());

        // When
        ResponseEntity<CreateCustomerResponseDto> response = customerController.createCustomer(requestDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldGetCustomerByUsername() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);
        CustomerEntity customerEntity = new CustomerEntity();

        when(customerService.getCustomerByUserName(username)).thenReturn(customerEntity);
        when(customerMapper.mapCustomerEntityToGetCustomerResponseDto(customerEntity)).
                thenReturn(new GetCustomerProfileResponseDto());

        // When
        ResponseEntity<GetCustomerProfileResponseDto> response = customerController.getCustomerByUsername(
                username, loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldUpdateCustomer() {
        // Given
        UpdateCustomerProfileRequestDto newData = new UpdateCustomerProfileRequestDto();
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        ;
        String loginPassword = RandomStringUtils.randomAlphabetic(7);
        ;
        GymUserEntity userEntityFromNewData = new GymUserEntity();
        CustomerEntity customerEntityFromNewData = new CustomerEntity();

        when(customerMapper.mapUpdateCustomerRequestDtoToUserEntity(newData)).thenReturn(userEntityFromNewData);
        when(customerMapper.mapUpdateCustomerRequestDtoToCustomerEntity(newData)).thenReturn(customerEntityFromNewData);
        when(customerService.updateCustomer(userEntityFromNewData, customerEntityFromNewData))
                .thenReturn(customerEntityFromNewData);
        when(customerMapper.mapCustomerEntityToUpdateCustomerResponseDto(customerEntityFromNewData))
                .thenReturn(new UpdateCustomerProfileResponseDto());

        // When
        ResponseEntity<UpdateCustomerProfileResponseDto> response = customerController.updateCustomer(
                newData, loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldDeleteCustomer() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);


        // When
        ResponseEntity<String> response = customerController.deleteCustomer(username, loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldChangeCustomerActivity() {
        // Given
        String username = RandomStringUtils.randomAlphabetic(7);
        Boolean isActive = true;
        String loginUserName = RandomStringUtils.randomAlphabetic(7);
        String loginPassword = RandomStringUtils.randomAlphabetic(7);

        // When
        ResponseEntity<String> response = customerController.customerActivation(
                username, isActive, loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldUpdateCustomerInstructors() {
        // Given
        UpdateCustomerInstructorsRequestDto requestDto = new UpdateCustomerInstructorsRequestDto();
        String loginUserName = "loginUser";
        String loginPassword = "password";
        Set<InstructorEntity> instructorEntities = new HashSet<>();

        when(customerService.changeCustomerInstructors(requestDto.getCustomerUserName(),
                requestDto.getInstructorUserNames())).thenReturn(instructorEntities);
        when(instructorMapper.mapInstructorEntitiesToInstructorResponseDto(instructorEntities))
                .thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<InstructorForCustomerResponseDto>> response = customerController.updateCustomerInstructors
                (requestDto, loginUserName, loginPassword);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
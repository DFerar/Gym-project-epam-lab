package com.gym.repository;

import com.gym.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findCustomerEntityByGymUserEntityUserName(String userName);

    boolean existsByGymUserEntityUserNameAndGymUserEntityPassword(String username, String password);
}

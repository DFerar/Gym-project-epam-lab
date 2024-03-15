package com.gym.repository;

import com.gym.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    CustomerEntity findCustomerEntityByGymUserEntityUserName(String userName);

    // TODO
    boolean existsByGymUserEntity_UserNameAndGymUserEntity_Password(String username, String password);
}


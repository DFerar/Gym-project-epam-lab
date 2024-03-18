package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gym_user", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "gym_user_SEQ", sequenceName = "gym_user_seq",  allocationSize = 1)
public class GymUserEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gym_user_SEQ")
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "is_active")
    private Boolean isActive;
}

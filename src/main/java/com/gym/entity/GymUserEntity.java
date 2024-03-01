package com.gym.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gym_user", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;
    @Basic
    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}

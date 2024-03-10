package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "instructor", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "specialization")
    private TrainingTypeEntity trainingTypeEntity;
    @OneToOne
    @JoinColumn(name = "user_id")
    private GymUserEntity gymUserEntity;
    @ManyToMany(mappedBy = "instructors")
    private Set<CustomerEntity> customers = new HashSet<>();
}

package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instructor", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "customers")
@SequenceGenerator(name = "instructor_SEQ", sequenceName = "instructor_seq", allocationSize = 1)
public class InstructorEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instructor_SEQ")
    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "specialization")
    private TrainingTypeEntity trainingTypeEntity;
    @OneToOne
    @JoinColumn(name = "user_id")
    private GymUserEntity gymUserEntity;
    @ManyToMany(mappedBy = "instructors", fetch = FetchType.EAGER)
    private Set<CustomerEntity> customers = new HashSet<>();
}

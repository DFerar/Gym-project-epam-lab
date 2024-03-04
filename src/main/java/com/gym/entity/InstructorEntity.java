package com.gym.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Basic
    @Column(name = "specialization", nullable = true)
    private Integer specialization;
    @OneToOne
    @JoinColumn(name = "user_id")
    private GymUserEntity gymUserEntity;
    @ManyToMany(mappedBy = "customers")
    private Set<CustomerEntity> customers = new HashSet<>();
}

//Same stuff as customerEntity
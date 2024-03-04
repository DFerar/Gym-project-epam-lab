package com.gym.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "date_of_birth", nullable = true)
    private Date dateOfBirth;
    @Basic
    @Column(name = "address", nullable = true, length = 255)
    private String address;
    @OneToOne(mappedBy = "customer")
    private GymUserEntity gymUserEntity;
    @ManyToMany
    @JoinTable(name = "customer_instructor",
    joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    private Set<InstructorEntity> instructors = new HashSet<>();
}

//How I can handle @ManyToMany while CustomerEntity instance creation(problems in service)

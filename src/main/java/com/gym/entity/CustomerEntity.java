package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "instructors")
@SequenceGenerator(name = "customer_SEQ", sequenceName = "customer_seq", allocationSize = 1)
public class CustomerEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_SEQ")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "address")
    private String address;
    @OneToOne
    @JoinColumn(name = "user_id")
    private GymUserEntity gymUserEntity;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_instructor",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    private Set<InstructorEntity> instructors = new HashSet<>();
}

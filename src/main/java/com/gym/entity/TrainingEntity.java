package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "training", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private InstructorEntity instructor;
    @Basic
    @Column(name = "training_name", nullable = false, length = 225)
    private String trainingName;
    @OneToOne
    @JoinColumn(name = "training_type_id")
    private TrainingTypeEntity trainingType;
    @Basic
    @Column(name = "training_date", nullable = false)
    private Date trainingDate;
    @Basic
    @Column(name = "training_duration", nullable = false)
    private Integer trainingDuration;
}

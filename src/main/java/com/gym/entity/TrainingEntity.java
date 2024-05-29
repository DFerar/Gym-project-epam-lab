package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "training", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"customer", "instructor", "trainingType"})
@SequenceGenerator(name = "training_SEQ", sequenceName = "training_seq", allocationSize = 1)
public class TrainingEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_SEQ")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private InstructorEntity instructor;
    @Column(name = "training_name")
    private String trainingName;
    @OneToOne
    @JoinColumn(name = "training_type_id")
    private TrainingTypeEntity trainingType;
    @Column(name = "training_date")
    private LocalDate trainingDate;
    @Column(name = "training_duration")
    private Integer trainingDuration;
}

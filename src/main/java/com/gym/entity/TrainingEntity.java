package com.gym.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private CustomerEntity customerId;
    @Basic
    @Column(name = "instructor_id", nullable = true)
    private Integer instructorId;
    @Basic
    @Column(name = "training_name", nullable = false, length = 225)
    private String trainingName;
    @Basic
    @Column(name = "training_type_id", nullable = true)
    private Integer trainingTypeId;
    @Basic
    @Column(name = "training_date", nullable = false)
    private Date trainingDate;
    @Basic
    @Column(name = "training_duration", nullable = false)
    private Integer trainingDuration;
}

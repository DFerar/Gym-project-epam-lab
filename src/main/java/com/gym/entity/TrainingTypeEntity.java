package com.gym.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "training_type", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "training_type_name", nullable = false, length = 225)
    private String trainingTypeName; // TODO ENUM
}

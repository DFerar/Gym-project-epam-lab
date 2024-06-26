package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "training_type", schema = "public", catalog = "gym")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "training_type_SEQ", sequenceName = "training_type_seq", allocationSize = 1)
public class TrainingTypeEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_type_SEQ")
    @Id
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "training_type_name")
    private TrainingType trainingTypeName;
}

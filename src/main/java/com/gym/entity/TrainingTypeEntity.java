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

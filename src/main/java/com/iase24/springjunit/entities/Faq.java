package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "faq")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question", columnDefinition = "text")
    private String question;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "date_question_create")
    private LocalDateTime dateQuestionCreate;

    @Column(name = "answer", columnDefinition = "text")
    private String answer;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "date_answer_create")
    private LocalDateTime dateAnswerCreate;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "description_id", referencedColumnName = "id")
    private DescriptionCategory descriptionCategory;
}

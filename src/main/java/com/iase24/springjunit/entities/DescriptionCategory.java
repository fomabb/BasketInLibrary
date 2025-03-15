package com.iase24.springjunit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "description")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionCategory {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "descriptionCategory"
            , cascade = CascadeType.ALL
            , orphanRemoval = true
            , fetch = FetchType.LAZY)
    private List<Faq> faq = new ArrayList<>();
}

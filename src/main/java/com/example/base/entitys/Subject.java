package com.example.base.entitys;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name  = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

//    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Mark> marks;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Mark> getMarks() {
//        return marks;
//    }
//
//    public void setMarks(List<Mark> marks) {
//        this.marks = marks;
//    }
}

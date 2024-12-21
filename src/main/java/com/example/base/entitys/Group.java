package com.example.base.entitys;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "\"groups\"")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

//    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Person> people;

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

//    public List<Person> getPeople() {
//        return people;
//    }
//
//    public void setPeople(List<Person> people) {
//        this.people = people;
//    }
}

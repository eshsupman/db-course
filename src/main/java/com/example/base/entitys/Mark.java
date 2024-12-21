package com.example.base.entitys;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.sql.Date;

@Entity
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "student_id"/*,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_marks_people1"),
            insertable = false, updatable = false*/
    )
    private Person student;

    @ManyToOne
    @JoinColumn(
            name = "subject_id"/*,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_marks_subjects"),
            insertable = false, updatable = false*/
    )
    private Subject subject;

    @ManyToOne
    @JoinColumn(
            name = "teacher_id"/*,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_marks_people2"),
            insertable = false, updatable = false*/
    )
    private Person teacher;

    @Column(name = "date", nullable = true)
    private Date date;

    @Column(name = "value", nullable = false)
    private Integer value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

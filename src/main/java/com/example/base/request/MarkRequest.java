package com.example.base.request;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.sql.Date;

public class MarkRequest {
    private Long id;
    private Long student_id;
    private Long subject_id;
    private Long teacher_id;
    private Date date;
    private Integer value;
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }
    public Long getStudent_id() {
        return student_id;
    }
    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }
    public Long getSubject_id() {
        return subject_id;
    }
    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }
    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate(){
        return date;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
}

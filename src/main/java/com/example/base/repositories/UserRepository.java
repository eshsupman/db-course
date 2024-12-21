package com.example.base.repositories;

import com.example.base.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface UserRepository extends JpaRepository<User, Long> {
    @Procedure("get_subjects_avg_by_year")
    Double getSubjectsAvgByYear(int year);

}
package com.example.base.repositories;

import com.example.base.entitys.Mark;
import com.example.base.request.AvgSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    @Query(value = "SELECT * FROM get_avg_marks_by_group_in_interval_func(:startYear, :endYear)", nativeQuery = true)
    List<Object[]> findAverageMarksByGroup(@Param("startYear") int startYear, @Param("endYear") int endYear);
    @Query(value = "SELECT * FROM get_avg_marks_by_subject_in_interval_func(:startYear, :endYear)", nativeQuery = true)
    List<Object[]> findAverageMarksBySubject(@Param("startYear") int startYear, @Param("endYear") int endYear);
    @Query(value = "SELECT * FROM get_avg_marks_by_student_in_interval_func(:startYear, :endYear)", nativeQuery = true)
    List<Object[]> findAverageMarksByStudent(@Param("startYear") int startYear, @Param("endYear") int endYear);
    @Query(value = "SELECT * FROM get_avg_marks_by_teacher_in_interval_func(:startYear, :endYear)", nativeQuery = true)
    List<Object[]> findAverageMarksByTeacher(@Param("startYear") int startYear, @Param("endYear") int endYear);
    @Query(value = "SELECT * FROM get_avg_marks_by_year_in_interval_func(:startYear, :endYear)", nativeQuery = true)
    List<Object[]> findAverageMarksByYear(@Param("startYear") int startYear, @Param("endYear") int endYear);
    @Query(value = "SELECT * FROM calculate_performance_growth(:startYear, :endYear)", nativeQuery = true)
    List<Object[]> calculatePerformanceGrowth(@Param("startYear") int startYear, @Param("endYear") int endYear);
}


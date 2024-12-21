package com.example.base.services;

import com.example.base.entitys.Mark;
import com.example.base.repositories.MarkRepository;
import com.example.base.request.MarkRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MarkService {
    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private PeopleService peopleService;


    public Mark create(Mark mark) {
        return markRepository.save(mark);
    }
    public List<Object[]> calcPerfGrow(int startYear, int endYear) {
        return markRepository.calculatePerformanceGrowth(startYear, endYear);
    }
    public List<Object[]> getAverageMarksG(int startYear, int endYear) {
        return markRepository.findAverageMarksByGroup(startYear, endYear);
    }
    public List<Object[]> getAverageMarksS(int startYear, int endYear) {
        return markRepository.findAverageMarksBySubject(startYear, endYear);
    }
    public List<Object[]> getAverageMarksP(int startYear, int endYear) {
        return markRepository.findAverageMarksByStudent(startYear, endYear);
    }
    public List<Object[]> getAverageMarksT(int startYear, int endYear) {
        return markRepository.findAverageMarksByTeacher(startYear, endYear);
    }
    public List<Object[]> getAverageMarksY(int startYear, int endYear) {
        return markRepository.findAverageMarksByYear(startYear, endYear);
    }
    public boolean delete(Long id) {
        Mark tar = null;
        for(Mark mark : markRepository.findAll()) {
            if(mark.getId().equals(id)) {
                tar = mark;
            }
        }
        if(tar == null) return false;
        markRepository.delete(tar);
        return true;
    }
    public Mark getById(Long id){
        for(Mark m : markRepository.findAll()){
            if(Objects.equals(m.getId(), id)){
                return m;
            }
        }
        return null;
    }
    public Mark update(MarkRequest newMark) {
        Mark tar = null;
        for(Mark mark : markRepository.findAll()) {
            if(mark.getId().equals(newMark.getId())) {
                tar = mark;
                break;
            }
        }
        if(tar == null) {
            return null;
        }
        tar.setSubject(subjectService.getSubjectById(newMark.getSubject_id()));
        tar.setTeacher(peopleService.getById(newMark.getTeacher_id()));
        tar.setStudent(peopleService.getById(newMark.getStudent_id()));
        return markRepository.save(tar);

    }
    public List<Mark> getAll() {
        return markRepository.findAll();
    }

    public void deleteById(Integer id) {
        markRepository.deleteById(Long.valueOf(id));
    }
}

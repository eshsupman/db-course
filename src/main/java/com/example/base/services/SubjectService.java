package com.example.base.services;

import com.example.base.entitys.Subject;
import com.example.base.repositories.SubjectRepository;
import com.example.base.request.SubjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }
    public boolean update(Long id, String name) {
        Subject tar = getSubjectById(id);
        if(tar == null) {
            return false;
        }
        tar.setName(name);
        subjectRepository.save(tar);
        return true;
    }

    public boolean remove(Long id){
        Subject tar = null;
        for(Subject s : subjectRepository.findAll()){
            if(s.getId().equals(id)){
                tar = s;
            }
        }
        if(tar == null) return false;
        subjectRepository.deleteById(id);
        return true;
    }
    public Subject getSubjectById(Long id) {
       for(Subject subject : subjectRepository.findAll()) {
           if(subject.getId().equals(id)) {
               return subject;
           }
       }
        return null;

    }
}

package com.example.base.services;

import com.example.base.entitys.Group;
import com.example.base.entitys.Mark;
import com.example.base.entitys.Person;
import com.example.base.repositories.GroupRepository;
import com.example.base.repositories.MarkRepository;
import com.example.base.repositories.PeopleRepository;
import com.example.base.request.MarkRequest;
import com.example.base.request.PeopleRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleService {
    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private GroupService groupService;

    public Person save(Person person) {
        return peopleRepository.save(person);
    }

    public boolean update(PeopleRequest people) {
        Person tar = getById(people.getId());
        tar.setType(people.getType());
        tar.setFirstName(people.getFirst_name());
        tar.setLastName(people.getLast_name());
        tar.setFatherName(people.getFather_name());
        Group group = groupService.getById(people.getGroup_id());
        tar.setGroup(group);
        peopleRepository.save(tar);
        return true;
    }

    public boolean remove(Long id) {
        Person tar = null;
        for(Person p : peopleRepository.findAll()) {
            if(p.getId().equals(id)) {
                tar = p;
            }
        }
        if(tar == null) return false;
        peopleRepository.delete(tar);
        return true;
    }

    public List<Person> getAll(){
        return peopleRepository.findAll();
    }
    public Person getById(Long id){
        for(Person x : peopleRepository.findAll()){
            if(x.getId().equals(id)){
                return x;
            }
        }
        return null;
    }
    public List<Person> getGroupmates(String f_name, String l_name, String fath_name){
        Group g  = getPersonGroupByName(f_name,l_name,fath_name);
        List<Person> list = new ArrayList<Person>();
        for(Person x : peopleRepository.findAll()){
            if(x.getGroup()!= null && x.getGroup().equals(g) && !x.getFirstName().equals(fath_name)
            && !x.getLastName().equals(l_name)){
                list.add(x);
            }
        }
        return list;
    }
    public Person getByName(String f_name, String l_name, String fath_name){
        for(Person x : peopleRepository.findAll()){
            if(x.getFirstName().equals(f_name) && x.getLastName().equals(l_name) && x.getFatherName().equals(fath_name)){
                return x;
            }
        }
        return null;
    }

    public Group getPersonGroupByName(String f_name, String l_name, String fath_name){
        for(Person x : peopleRepository.findAll()){
            if(x.getFirstName().equals(f_name)
                    && x.getLastName().equals(l_name)
                    && x.getFatherName().equals(fath_name)){
                return x.getGroup();
            }
        }
        return null;
    }
    public Double calcAvg(String f_name, String l_name, String fath_name){
        Person tar = new Person();
        for(Person x : peopleRepository.findAll()){
            if(x.getFirstName().equals(f_name)
            && x.getLastName().equals(l_name)
            && x.getFatherName().equals(fath_name)) {
                tar = x;
                break;
            }
        }
        Double avg = 0.0;
        Integer count = 0;
        for(Mark x : markRepository.findAll()){
            if(x.getStudent().equals(tar)) {
                avg += x.getValue();
                count++;
            }
        }
        if(count == 0) return 0.0;
        return avg/count;
    }
    public Double getGroupsAbf(String f_name, String l_name, String fath_name){
        Person tar = getByName(f_name,l_name,fath_name);
        Double val = 0.0;
        Integer count = 0;
        if(tar.getGroup() == null) return 0.0;
        for(Mark x : markRepository.findAll()){
            if(x.getStudent().getGroup().equals(tar.getGroup())) {
                val += x.getValue();
                count++;
            }
        }

        return val/count;

    }
}

package com.example.base.services;

import com.example.base.entitys.Group;
import com.example.base.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public Group getById(Long id) {
        for(Group group : groupRepository.findAll()) {
            if(group.getId().equals(id)) {
                return group;
            }
        }
        return null;
    }

    public Group create(Group group) {
        return groupRepository.save(group);
    }
    public boolean remove(Long id) {
        Group tar = getById(id);
        if(tar == null) return false;
        groupRepository.delete(tar);
        return true;
    }
    public boolean update(Long id, String name) {
        Group tar = getById(id);
        if(tar == null) return false;
        tar.setName(name);
        groupRepository.save(tar);
        return true;
    }

    public List<Group> getGroups(){
        return groupRepository.findAll();
    }
    public Group getByName(String name){
        for(Group group : groupRepository.findAll()) {
            if(group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }
}

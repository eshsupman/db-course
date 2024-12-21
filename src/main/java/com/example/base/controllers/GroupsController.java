package com.example.base.controllers;

import com.example.base.entitys.Group;
import com.example.base.services.GroupService;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63343")
@RequestMapping("/api/groups")
public class GroupsController {
    @Autowired
    private GroupService groupService;



    @GetMapping()
    public List<Group> getGroup() {
        return  groupService.getGroups();
    }
}

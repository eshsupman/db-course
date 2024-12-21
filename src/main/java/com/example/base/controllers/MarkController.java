package com.example.base.controllers;

import com.example.base.config.Constanst;
import com.example.base.entitys.Mark;
import com.example.base.services.MarkService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63343")
@RequestMapping("/api/marks")
public class MarkController {

    @Autowired
    private MarkService markService;



    // CREATE
    @PostMapping
    public Mark create(@RequestBody Mark mark) {
        return markService.create(mark);
    }

    // READ (find by ID)

    // READ (find all)
    @GetMapping
    public List<Mark> findAll() {
        return markService.getAll();
    }

    // UPDATE


    // DELETE
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        markService.deleteById(id);
    }
}

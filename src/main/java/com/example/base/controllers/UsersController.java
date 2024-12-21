package com.example.base.controllers;

import com.example.base.entitys.Mark;
import com.example.base.entitys.Subject;
import com.example.base.request.*;
import com.example.base.Role;
import com.example.base.config.Constanst;
import com.example.base.entitys.Group;
import com.example.base.entitys.Person;
import com.example.base.entitys.User;
import com.example.base.services.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.engine.spi.SessionLazyDelegator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:63344")
@RequestMapping("/users")

public class UsersController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private MarkService markService;

    @Autowired
    private SubjectService subjectService;


    @Autowired
    private PeopleService peopleService;

    @PostMapping("/gro")
    public ResponseEntity<byte[]> growth(@RequestBody HashMap<String, Integer> rb){
       List<Object[]> list =  markService.calcPerfGrow(rb.get("start"), rb.get("end"));
       StringBuilder data = new StringBuilder();
       data.append("Year | AVG | Growth\n");
       data.append("-------------------\n");
       for(Object[] i : list){
           data.append(i[0].toString()).append(" | ").append(String.format("%.2f",Double.parseDouble(i[1].toString()))).append("  | ").append(String.format("%.2f",Double.parseDouble(i[2].toString()))).append("\n");
       }

       byte[] csvBytes = data.toString().getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/csv"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("file.csv").build());


        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/avgG")
    public ResponseEntity<List<AvgSub>> avgG(@RequestBody HashMap<String, Integer> rb ) {
        List<AvgSub> list = new ArrayList<>();
        for(Object[] resp : markService.getAverageMarksG(rb.get("start"), rb.get("end"))){
            AvgSub cur = new AvgSub();
            cur.setName((String) resp[0]);
            cur.setVal(Double.parseDouble(((BigDecimal) resp[1]).toString()));
            list.add(cur);
        }
        return new  ResponseEntity<>(list, HttpStatus.OK);

    }
    @PostMapping("/avgS")
    public ResponseEntity<List<AvgSub>> avgS(@RequestBody HashMap<String, Integer> rb) {
        List<AvgSub> list = new ArrayList<>();
        for(Object[] resp : markService.getAverageMarksS(rb.get("start"), rb.get("end"))){
            AvgSub cur = new AvgSub();
            cur.setName((String) resp[0]);
            cur.setVal(Double.parseDouble(((BigDecimal) resp[1]).toString()));
            list.add(cur);
        }
        return new  ResponseEntity<>(list, HttpStatus.OK);

    }
    @PostMapping("/avgP")
    public ResponseEntity<List<AvgSub>> avgP(@RequestBody HashMap<String, Integer> rb) {
        List<AvgSub> list = new ArrayList<>();
        for(Object[] resp : markService.getAverageMarksP(rb.get("start"), rb.get("end"))){
            AvgSub cur = new AvgSub();
            cur.setName((String) resp[0]);
            cur.setVal(Double.parseDouble(((BigDecimal) resp[1]).toString()));
            list.add(cur);
        }
        return new  ResponseEntity<>(list, HttpStatus.OK);

    }
    @PostMapping("/avgT")
    public ResponseEntity<List<AvgSub>> avgT(@RequestBody HashMap<String, Integer> rb) {
        List<AvgSub> list = new ArrayList<>();
        for(Object[] resp : markService.getAverageMarksT(rb.get("start"), rb.get("end"))){
            AvgSub cur = new AvgSub();
            cur.setName((String) resp[0]);
            cur.setVal(Double.parseDouble(((BigDecimal) resp[1]).toString()));
            list.add(cur);
        }
        return new  ResponseEntity<>(list, HttpStatus.OK);

    }
    @PostMapping("/avgY")
    public ResponseEntity<List<AvgSub>> avgY(@RequestBody HashMap<String, Integer> rb) {
        List<AvgSub> list = new ArrayList<>();
        for(Object[] resp : markService.getAverageMarksY(rb.get("start"), rb.get("end"))){
            AvgSub cur = new AvgSub();
            cur.setName((String) resp[0]);
            cur.setVal(Double.parseDouble(((BigDecimal) resp[1]).toString()));
            list.add(cur);
        }
        return new  ResponseEntity<>(list, HttpStatus.OK);

    }


    @PostMapping("/avgGroup")
    public ResponseEntity<Double> avgG(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Constanst.key)
                    .build().parseSignedClaims(token).getPayload();

            String f_name = claims.get("first_name").toString();
            String l_name = claims.get("last_name").toString();
            String fa_name = claims.get("father_name").toString();
            return new ResponseEntity<>(peopleService.getGroupsAbf(f_name, l_name, fa_name), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/avg-by-year/{year}")
    public ResponseEntity<List<Map<String, Object>>> getSubjectsAvgByYear(@PathVariable int year) {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = "SELECT s.name as subject_name, avg(m.value) as avg_mark " +
                "FROM marks m " +
                "JOIN people p ON m.student_id = p.id " +
                "JOIN groups g ON p.group_id = g.id " +
                "JOIN subject s ON m.subject_id = s.id " +
                "WHERE g.name = '1904/2_2022' " +
                "GROUP BY s.name " +
                "ORDER BY s.name";

        try {
            result = jdbcTemplate.query(sql, new Object[]{year}, (rs, rowNum) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("subject_name", rs.getString("subject_name"));
                map.put("avg_mark", rs.getDouble("avg_mark"));
                return map;
            });

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/sigup")
    public ResponseEntity<String> createUser(@RequestBody SignUpRequest user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setRole(Role.USER);
        userService.createUser(newUser);
        return new ResponseEntity<>(generateJWTToken(newUser), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogInRequest user) {
        String username = user.getUsername();
        String password = user.getPassword().trim();
        for (User x : userService.getAllUsers()) {
            if (x.getUsername().equals(username)) {
                if (BCrypt.checkpw(password, x.getPassword())) {
                    String token = generateJWTToken(x);
                    return new ResponseEntity<>(token, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/userdata")
    public ResponseEntity<String[]> getUserData(HttpServletRequest request) {
        String token;
        String authHeader = request.getHeader("Authorization");
        token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Constanst.key)
                    .build().parseSignedClaims(token).getPayload();
            String[] resp = new String[1];
            resp[0] = claims.get("username").toString();
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }


    }

    @PostMapping("/mates")
    public ResponseEntity<List<Person>> getMates(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Constanst.key)
                    .build().parseSignedClaims(token).getPayload();

            String f_name = claims.get("first_name").toString();
            String l_name = claims.get("last_name").toString();
            String fa_name = claims.get("father_name").toString();
            return new ResponseEntity<>(peopleService.getGroupmates(f_name, l_name, fa_name), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity validateUser(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Constanst.key)
                    .build().parseSignedClaims(token).getPayload();
            if (claims.get("role").equals("ADMIN")) {
                return new ResponseEntity(HttpStatus.OK);

            }
            return new ResponseEntity<>("invalid role", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allG")
    public ResponseEntity<List<Object[]>> getAllGroups() {
        List<Object[]> groups = new ArrayList<>();
        for (Group g : groupService.getGroups()) {
            HashMap<Long, String> group = new HashMap<>();

            groups.add(new Object[]{g.getId(), g.getName()});
        }
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/addG")
    public ResponseEntity<String> addGroup(@RequestBody GroupRequest group) {
        Group g = new Group();
        g.setName(group.getName());
        groupService.create(g);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/addS")
    public ResponseEntity<String> addGroup(@RequestBody SubjectRequest group) {
        Subject g = new Subject();
        g.setName(group.getName());
        subjectService.save(g);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/updS")
    public ResponseEntity<String> updGroup(@RequestBody HashMap<String,List<SubjectRequest>> subjects) {
        for(SubjectRequest subject : subjects.get("list")){
            if(subject.getId() == -1){
                Subject newS = new Subject();
                newS.setName(subject.getName());
                subjectService.save(newS);
            }else{
                subjectService.update(subject.getId(), subject.getName());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/addP")
    public ResponseEntity<String> addGroup(@RequestBody PeopleRequest people) {
        Person p = new Person();
        p.setFirstName(people.getFirst_name());
        p.setLastName(people.getLast_name());
        p.setFatherName(people.getFather_name());
        p.setType(people.getType());
        if(people.getGroup_id() == null) p.setGroup(null);
        else p.setGroup(groupService.getById(people.getId()));
        peopleService.save(p);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/remS")
    public ResponseEntity<String> removeSubject(@RequestBody HashMap<String,List<Long>> sId) {
        for(Long i : sId.get("list")){
            System.out.println(i);
            subjectService.remove(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remG")
    public ResponseEntity<String> removeGroup(@RequestBody HashMap<String,List<Long>> gId) {
        for(Long i : gId.get("list")){
            //System.out.println(i);
            groupService.remove(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/updG")
    public ResponseEntity<String> updateGroup(@RequestBody HashMap<String,List<GroupRequest>> groups) {
        for(GroupRequest group : groups.get("list")){
            // to do проверка маски
            if(group.getId() == -1){
                Group newG = new Group();
                newG.setName(group.getName());
                groupService.create(newG);
            }else{
                groupService.update(group.getId(), group.getName());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/updM")
    public ResponseEntity<String> updateMark(@RequestBody HashMap<String,List<MarkRequest>> marks) {
        ResponseEntity<String> resp = new ResponseEntity<>(HttpStatus.OK);
        for(MarkRequest mark : marks.get("list")) {
            Mark m = new Mark();
            if(mark.getId() != -1){
                m = markService.getById(mark.getId());
            }

            Person student = peopleService.getById(mark.getStudent_id());
            if (student == null) {
                return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
            }
            if (student.getType() == 'P') {
                return new ResponseEntity<>("Cannot assign teacher to student", HttpStatus.BAD_REQUEST);
            }
            m.setStudent(student);
            Person teacher = peopleService.getById(mark.getTeacher_id());
            if (teacher == null) {
                return new ResponseEntity<>("Teacher not found", HttpStatus.BAD_REQUEST);
            }

            if (teacher.getType() == 'S') {
                return new ResponseEntity<>("Cannot assign student to teacher", HttpStatus.BAD_REQUEST);
            }
            m.setTeacher(teacher);
            Subject subject = subjectService.getSubjectById(mark.getSubject_id());
            if (subject == null) {
                return new ResponseEntity<>("Subject not found", HttpStatus.BAD_REQUEST);
            }
            m.setSubject(subject);

            m.setDate(mark.getDate());

            if (mark.getValue() < 2 || mark.getValue() > 5) {
                return new ResponseEntity<>("Invalid mark value. It should be between 2 and 5", HttpStatus.BAD_REQUEST);
            }
            m.setValue(mark.getValue());
            markService.create(m);

        }
        return new ResponseEntity<>("Mark successfully updated", HttpStatus.OK);
    }
    @PostMapping("/remM")
    public ResponseEntity<String> removeMark(@RequestBody HashMap<String,List<Long>> mId) {
        for(Long i : mId.get("list")){
            markService.delete(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/remP")
    public ResponseEntity<String> removePeople(@RequestBody HashMap<String,List<Long>> pId) {
        for(Long i : pId.get("list")){
            peopleService.remove(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @PostMapping("/updP")
    public ResponseEntity<String> updatePeople(@RequestBody HashMap<String,List<PeopleRequest>> peoples) {
        for(PeopleRequest people : peoples.get("list")){

            if(people.getId() == -1){
                Person p = new Person();
                p.setFatherName(people.getFather_name());
                p.setFirstName(people.getFirst_name());
                p.setLastName(people.getLast_name());
                p.setType(people.getType());
                Group g = groupService.getById(people.getGroup_id());
                //to do
                p.setGroup(g);
                peopleService.save(p);
            }else{
                peopleService.update(people);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/allM")
    public ResponseEntity<List<Object[]>> getAllMarks() {
        List<Object[]> marks = new ArrayList<>();
        for (Mark m : markService.getAll()) {
            marks.add(new Object[]{m.getId(), m.getStudent().getId(), m.getSubject().getId(), m.getTeacher().getId(),m.getDate(), m.getValue()});
        }
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/allS")
    public ResponseEntity<List<Object[]>> getAllSubjects() {
        List<Object[]> subjects = new ArrayList<>();
        for (Subject m : subjectService.findAll()) {
            subjects.add(new Object[]{m.getId(), m.getName()});
        }
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/allP")
    public ResponseEntity<List<Object[]>> getAllPeople() {
        List<Object[]> persons = new ArrayList<>();
        for (Person m : peopleService.getAll()) {
            if (m.getGroup() != null)
                persons.add(new Object[]{m.getId(), m.getFirstName(), m.getLastName(), m.getFatherName(), m.getGroup().getId(), m.getType()});
            else
                persons.add(new Object[]{m.getId(), m.getFirstName(), m.getLastName(), m.getFatherName(), "Nogroup", m.getType()});
        }
        return ResponseEntity.ok(persons);
    }

    @PostMapping("/addM")
    public ResponseEntity<String> addGroup(@RequestBody MarkRequest group) {
        if (group == null) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        Mark m = new Mark();
        System.out.println(group.getStudent_id() + " " + group.getSubject_id() + " " + group.getTeacher_id());
        Person student = peopleService.getById(group.getStudent_id());
        if (student == null) {
            return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
        }
        if (student.getType() == 'P') {
            return new ResponseEntity<>("Cannot assign teacher to student", HttpStatus.BAD_REQUEST);
        }
        m.setStudent(student);
        Person teacher = peopleService.getById(group.getTeacher_id());
        if (teacher == null) {
            return new ResponseEntity<>("Teacher not found", HttpStatus.BAD_REQUEST);
        }

        if (teacher.getType() == 'S') {
            return new ResponseEntity<>("Cannot assign student to teacher", HttpStatus.BAD_REQUEST);
        }
        m.setTeacher(teacher);
        Subject subject = subjectService.getSubjectById(group.getSubject_id());
        if (subject == null) {
            return new ResponseEntity<>("Subject not found", HttpStatus.BAD_REQUEST);
        }
        m.setSubject(subject);

        // Проверка на валидность оценки
        if (group.getValue() < 2 || group.getValue() > 5) {
            return new ResponseEntity<>("Invalid mark value. It should be between 2 and 5", HttpStatus.BAD_REQUEST);
        }

        m.setValue(group.getValue());

        System.out.println(m.getStudent().getId() + " " + m.getSubject().getId() + " " + m.getTeacher().getId());

        markService.create(m);

        return new ResponseEntity<>("Mark successfully added", HttpStatus.OK);
    }




    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private String generateJWTToken(User user) {
        long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .signWith(Constanst.key)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 2 * 60 * 60 * 1000))
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .compact();

        return token;
    }

}

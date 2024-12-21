package com.example.base.request;

public class PeopleRequest {

    private Long id;
    private String first_name;
    private String last_name;
    private String father_name;
    private Long group_id;
    private Character type;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }
    public String getFather_name() {
        return father_name;
    }
    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }
    public Long getGroup_id() {
        return group_id;
    }
    public void setType(Character type) {
        this.type = type;
    }
    public Character getType() {
        return type;
    }
}

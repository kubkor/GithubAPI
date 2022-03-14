package com.hitit.sample.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Repository implements Comparable<Repository>{
    private long id;
    private String name;

    @JsonProperty("forks_count")
    private int forkQuantity;

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getForkQuantity() {
        return forkQuantity;
    }

    public void setForkQuantity(int forkQuantity) {
        this.forkQuantity = forkQuantity;
    }

    @Override
    public int compareTo(Repository o) {
        return this.getForkQuantity() - (o.getForkQuantity());
    }
}

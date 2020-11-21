package com.gkalapis.scorer.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "user", catalog = "scorer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String password;

    private int points;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, int points, String password) {
        this.name = name;
        this.points = points;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

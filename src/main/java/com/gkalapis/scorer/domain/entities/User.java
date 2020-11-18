package com.gkalapis.scorer.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", catalog = "scorer")
public class User {

    @Column (length = 250)
	@Id ()
    private String id;

    private String name;

    private String password;

    private int points;

    public User() {}

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String id, String name, int points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

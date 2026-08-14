package com.example.AuthLearn.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.IdGeneratorType;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name ="username")
    private String username;

    @Column(name ="password")
    private String password;




    @ManyToMany
    @JoinTable
            (name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                    inverseJoinColumns = @JoinColumn(name="role_id" , referencedColumnName = "id"))


    private List<Roles> roles = new ArrayList<>();


    public User(Long id, String name, String username, String password, List<Roles> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }}

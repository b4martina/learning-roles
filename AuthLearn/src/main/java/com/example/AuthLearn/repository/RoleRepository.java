package com.example.AuthLearn.repository;

import com.example.AuthLearn.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {


    Optional<Roles> findByRoleName(String roleName);
}

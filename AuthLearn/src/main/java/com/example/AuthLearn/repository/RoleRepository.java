package com.example.AuthLearn.repository;

import com.example.AuthLearn.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {
}

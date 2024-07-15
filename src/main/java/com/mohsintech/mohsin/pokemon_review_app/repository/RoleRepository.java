package com.mohsintech.mohsin.pokemon_review_app.repository;

import com.mohsintech.mohsin.pokemon_review_app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);


}

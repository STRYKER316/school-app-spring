package com.example.schoolapp.repository;

import com.example.schoolapp.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(exported = false)
@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Roles findByRoleName(String studentRole);
}

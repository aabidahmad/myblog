package com.myblog7.repository;

import com.myblog7.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}

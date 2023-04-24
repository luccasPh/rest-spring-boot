package com.lucas.restspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.restspringboot.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}

package com.example.hotelbookingassignment.repository;

import com.example.hotelbookingassignment.ds.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findRoleByRoleName(String roleName);
}

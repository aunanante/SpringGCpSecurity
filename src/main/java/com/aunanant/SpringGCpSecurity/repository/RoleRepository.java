package com.aunanant.SpringGCpSecurity.repository;

import com.aunanant.SpringGCpSecurity.models.ERole;
import com.aunanant.SpringGCpSecurity.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

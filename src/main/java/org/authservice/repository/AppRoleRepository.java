package org.authservice.repository;

import org.authservice.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByAppIdAndApiId(Long appId, Long apiId);
}
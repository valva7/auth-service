package org.authservice.repository;

import org.authservice.entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<Api, Long> {
    Api findByMethodAndPath(String method, String path);
}
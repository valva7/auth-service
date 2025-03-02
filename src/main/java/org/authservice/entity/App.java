package org.authservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "123", description = "auto increment pk")
    private Long id;

    @Schema(example = "vacation", description = "시스템 이름")
    @Column(unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name="app_id")
    private List<Api> apis;

    @OneToMany
    @JoinColumn(name="app_id")
    private List<AppRole> appRoles;
}
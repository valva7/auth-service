package org.authservice.repository;

import org.authservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Boolean existsByKakaoNickName(String kakaoNickName);

}

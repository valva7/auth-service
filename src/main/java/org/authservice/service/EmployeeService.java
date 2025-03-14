package org.authservice.service;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.authservice.entity.Employee;
import org.authservice.repository.EmployeeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> listEmployees(){
        return employeeRepository.findAll();
    }

    public Employee createEmployee(String firstName, String lastName, Long departmentId, String kakaoNickName) {
        if(employeeRepository.existsByKakaoNickName(kakaoNickName)) {
            throw new DuplicateKeyException("동일한 닉네임이 존재합니다");
        }

        Employee employee = Employee.builder()
            .firstName(firstName)
            .lastName(lastName)
            .departmentId(departmentId)
            .kakaoNickName(kakaoNickName)
            .build();

        employeeRepository.save(employee);
        return employee;
    }

    @Cacheable(cacheNames = "employee", key = "#id")
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("id를 찾을수 없습니다"));
    }

}
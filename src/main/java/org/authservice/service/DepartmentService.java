package org.authservice.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.authservice.entity.Department;
import org.authservice.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> listDepartments(){
        return departmentRepository.findAll();
    }
}
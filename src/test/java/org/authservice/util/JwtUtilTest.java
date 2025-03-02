package org.authservice.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.authservice.entity.Employee;
import org.authservice.entity.EmployeeRole;
import org.junit.jupiter.api.Test;

public class JwtUtilTest {

    @Test
    public void test(){
        String nickName = "nick name";

        Employee employee = Employee.builder()
            .kakaoNickName(nickName)
            .build();

        String token = JwtUtil.createUserToken(employee);

        assertEquals(nickName, JwtUtil.parseToken(token).get("nickname"));
    }

    @Test
    public void test_role(){
        EmployeeRole employeeEmployeeRole1 = EmployeeRole.builder()
            .id(1L)
            .name("role1")
            .build();
        EmployeeRole employeeEmployeeRole2 = EmployeeRole.builder()
            .id(2L)
            .name("role2")
            .build();
        List<EmployeeRole> employeeEmployeeRoles = Arrays.asList(employeeEmployeeRole1, employeeEmployeeRole2);
        Set<EmployeeRole> employeeEmployeeRoleSet = new HashSet<>(employeeEmployeeRoles);

        Employee employee = Employee.builder()
            .employeeRoles(employeeEmployeeRoleSet)
            .build();

        String token = JwtUtil.createUserToken(employee);
        List res = JwtUtil.parseToken(token).get("employeeRoles", List.class);
        assertEquals(employeeEmployeeRoleSet.size(), res.size());
        assertTrue(res.contains(employeeEmployeeRole1.getName()));
        assertTrue(res.contains(employeeEmployeeRole2.getName()));
    }

}

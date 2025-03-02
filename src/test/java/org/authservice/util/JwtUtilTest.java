package org.authservice.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.authservice.entity.Employee;
import org.authservice.entity.Role;
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
        Role employeeRole1 = Role.builder()
            .id(1L)
            .name("role1")
            .build();
        Role employeeRole2 = Role.builder()
            .id(2L)
            .name("role2")
            .build();
        List<Role> employeeRoles = Arrays.asList(employeeRole1, employeeRole2);
        Set<Role> employeeRoleSet = new HashSet<>(employeeRoles);

        Employee employee = Employee.builder()
            .roles(employeeRoleSet)
            .build();

        String token = JwtUtil.createUserToken(employee);
        List res = JwtUtil.parseToken(token).get("roles", List.class);
        assertEquals(employeeRoleSet.size(), res.size());
        assertTrue(res.contains(employeeRole1.getName()));
        assertTrue(res.contains(employeeRole2.getName()));
    }

}

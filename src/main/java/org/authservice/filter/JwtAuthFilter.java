package org.authservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.authservice.entity.Employee;
import org.authservice.repository.EmployeeRepository;
import org.authservice.service.KakaoService;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final KakaoService kakaoService;
    private final EmployeeRepository employeeRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")){
            String accessToken = authorizationHeader.substring(7);
            String nickName = kakaoService.getUserFromKakao(accessToken).getKakaoAccount().getProfile().getNickName();
            if(employeeRepository.existsByKakaoNickName(nickName)){
                Employee employee = employeeRepository.findByKakaoNickName(nickName);
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                if(Employee.isHR(employee)){
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }
                Authentication authentication = new TestingAuthenticationToken(employee.getFirstName(), "password", authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}

package org.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.authservice.config.CustomRateLimiter;
import org.authservice.dto.AppTokenRespDto;
import org.authservice.dto.ValidateTokenDto;
import org.authservice.entity.Api;
import org.authservice.entity.App;
import org.authservice.repository.ApiRepository;
import org.authservice.repository.AppRepository;
import org.authservice.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final AppRepository appRepository;
    private final ApiRepository apiRepository;
    private final CustomRateLimiter customRateLimiter;

    public AppTokenRespDto createAppToken(Long appId){
        App app = appRepository.getById(appId);
        String token = JwtUtil.createAppToken(app);
        return AppTokenRespDto.builder()
                .token(token)
                .build();
    }

    public ResponseEntity<String> validateToken(ValidateTokenDto dto) {
        Api api = apiRepository.findByMethodAndPath(dto.getMethod(), dto.getPath());
        ResponseEntity resp = JwtUtil.validateAppToken(dto, api);
        if(resp.getStatusCode().is2xxSuccessful()){
            Long appId = Long.valueOf(JwtUtil.parseSubject(dto.getToken()));
            if(!customRateLimiter.tryConsume(appId, api.getId())){
                log.error("TOO MANY REQUESTS");
                return new ResponseEntity<>("too many requests", HttpStatus.TOO_MANY_REQUESTS);
            }
        }
        return resp;
    }
}
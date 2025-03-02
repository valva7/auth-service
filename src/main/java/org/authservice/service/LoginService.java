package org.authservice.service;

import lombok.RequiredArgsConstructor;
import org.authservice.dto.KakaoUserInfoRespDto;
import org.authservice.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final KakaoService kakaoService;
    private final EmployeeRepository employeeRepository;

    public ResponseEntity login(String code) {
        String token = kakaoService.getAccessTokenFromKakao(code);
        return new ResponseEntity(token, HttpStatus.OK);
    }

    public ResponseEntity getKakaoUser(String token){
        KakaoUserInfoRespDto dto = kakaoService.getUserFromKakao(token);
        String nickName =  dto.getKakaoAccount().getProfile().getNickName();
        if(employeeRepository.existsByKakaoNickName(nickName)){
            return new ResponseEntity("환영합니다 " + nickName, HttpStatus.OK);
        }else {
            return new ResponseEntity("등록된 임직원이 아닙니다", HttpStatus.FORBIDDEN);
        }
    }
}
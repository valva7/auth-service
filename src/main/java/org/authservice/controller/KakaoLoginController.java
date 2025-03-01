package org.authservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.authservice.dto.KakaoUserInfoRespDto;
import org.authservice.service.KakaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao/callback")
    public ResponseEntity callback(@RequestParam("code")String code){
        String accessTokenFromKakao = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoRespDto userFromKakao = kakaoService.getUserFromKakao(accessTokenFromKakao);
        log.info("user nickname is " + userFromKakao.getKakaoAccount().getProfile().getNickName());
        return new ResponseEntity(HttpStatus.OK);
    }
}
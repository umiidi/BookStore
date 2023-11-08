package com.ingress.bookstore.controller;

import com.ingress.bookstore.models.base.BaseResponse;
import com.ingress.bookstore.models.dto.AccessTokenDto;
import com.ingress.bookstore.models.dto.LoginDto;
import com.ingress.bookstore.models.request.AuthorSignUpRequest;
import com.ingress.bookstore.models.request.StudentSignUpRequest;
import com.ingress.bookstore.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public BaseResponse<AccessTokenDto> authorize(@RequestBody LoginDto loginDto) {
        return BaseResponse.success(authService.signIn(loginDto));
    }

    @PostMapping("/sign-up/student")
    public BaseResponse<?> signUp(@RequestBody StudentSignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return BaseResponse.success();
    }

    @PostMapping("/sign-up/author")
    public BaseResponse<?> signUp(@RequestBody AuthorSignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return BaseResponse.success();
    }

}

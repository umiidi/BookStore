package com.ingress.bookstore.services.auth;

import com.ingress.bookstore.models.dto.AccessTokenDto;
import com.ingress.bookstore.models.dto.LoginDto;
import com.ingress.bookstore.models.request.AuthorSignUpRequest;
import com.ingress.bookstore.models.request.StudentSignUpRequest;

public interface AuthService {

    AccessTokenDto signIn(LoginDto loginDto);

    void signUp(AuthorSignUpRequest signUpRequest);

    void signUp(StudentSignUpRequest signUpRequest);

}

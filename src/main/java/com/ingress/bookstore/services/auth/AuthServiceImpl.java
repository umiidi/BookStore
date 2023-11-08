package com.ingress.bookstore.services.auth;

import com.ingress.bookstore.models.dto.AccessTokenDto;
import com.ingress.bookstore.models.dto.LoginDto;
import com.ingress.bookstore.models.request.AuthorSignUpRequest;
import com.ingress.bookstore.models.request.StudentSignUpRequest;
import com.ingress.bookstore.services.author.AuthorService;
import com.ingress.bookstore.services.student.StudentService;
import com.ingress.bookstore.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthorService authorService;
    private final StudentService studentService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AccessTokenDto signIn(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateJwtToken(authentication);
        return new AccessTokenDto(token);
    }

    @Override
    public void signUp(AuthorSignUpRequest signUpRequest) {
        authorService.addAuthor(signUpRequest);
    }

    @Override
    public void signUp(StudentSignUpRequest signUpRequest) {
        studentService.addStudent(signUpRequest);
    }

}

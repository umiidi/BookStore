package com.ingress.bookstore.services.author;

import com.ingress.bookstore.exception.types.NotFoundException;
import com.ingress.bookstore.models.domain.Author;
import com.ingress.bookstore.models.domain.Student;
import com.ingress.bookstore.models.domain.User;
import com.ingress.bookstore.models.enums.Authority;
import com.ingress.bookstore.models.request.AuthorSignUpRequest;
import com.ingress.bookstore.models.response.StudentResponse;
import com.ingress.bookstore.repository.AuthorRepo;
import com.ingress.bookstore.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final UserService userService;
    private final AuthorRepo authorRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addAuthor(AuthorSignUpRequest authorSignUpRequest) {
        int userId = userService.addUser(User.builder()
                .username(authorSignUpRequest.getUsername())
                .password(passwordEncoder.encode(authorSignUpRequest.getPassword()))
                .authority(Authority.AUTHOR)
                .build());
        authorRepo.save(Author.builder()
                .name(authorSignUpRequest.getName())
                .userId(userId)
                .age(authorSignUpRequest.getAge())
                .build());
    }

    @Override
    public Author getAuthorByUserId(int id) {
        return authorRepo.getByUserId(id).orElseThrow(
                () -> new NotFoundException("author not found")
        );
    }

    @Override
    public List<StudentResponse> getSubscriber(int id) {
        return authorRepo.getSubscriber(id).get().stream().map(
                        student -> StudentResponse.builder()
                                .name(student.getName())
                                .age(student.getAge())
                                .build())
                .collect(Collectors.toList());
    }

}

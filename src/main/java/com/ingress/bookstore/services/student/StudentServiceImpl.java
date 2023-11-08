package com.ingress.bookstore.services.student;

import com.ingress.bookstore.exception.types.NotFoundException;
import com.ingress.bookstore.models.domain.Book;
import com.ingress.bookstore.models.domain.Student;
import com.ingress.bookstore.models.domain.User;
import com.ingress.bookstore.models.enums.Authority;
import com.ingress.bookstore.models.request.StudentSignUpRequest;
import com.ingress.bookstore.models.response.BookResponse;
import com.ingress.bookstore.repository.StudentRepo;
import com.ingress.bookstore.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserService userService;
    private final StudentRepo studentRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addStudent(StudentSignUpRequest signUpRequest) {
        int userId = userService.addUser(User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .authority(Authority.STUDENT)
                .build());
        studentRepo.save(Student.builder()
                .name(signUpRequest.getName())
                .userId(userId)
                .age(signUpRequest.getAge())
                .build());
    }

    @Override
    public Student getStudentByUserId(int id) {
        return studentRepo.getByUserId(id).orElseThrow(
                () -> new NotFoundException("student not found")
        );
    }

    @Override
    public List<BookResponse> getReadBooks() {
        User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int studentId = getStudentByUserId(auth.getId()).getId();
        return studentRepo.getReadBooks(studentId).get()
                .stream().map(book ->
                        BookResponse.builder()
                                .name(book.getName())
                                .author(book.getAuthor().getName())
                                .build())
                .toList();
    }

    @Override
    public void subscribe(int id) {
        User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int studentId = getStudentByUserId(auth.getId()).getId();
        if (!isSubscribe(studentId, id)) {
            studentRepo.subscribe(studentId, id);
        } else {
            throw new IllegalArgumentException("student already subscribe");
        }
    }

    private boolean isSubscribe(int studentId, int authorId) {
        return studentRepo.isSubscribe(studentId, authorId);
    }

}

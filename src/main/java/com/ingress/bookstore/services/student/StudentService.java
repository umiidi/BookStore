package com.ingress.bookstore.services.student;

import com.ingress.bookstore.models.domain.Book;
import com.ingress.bookstore.models.domain.Student;
import com.ingress.bookstore.models.request.StudentSignUpRequest;
import com.ingress.bookstore.models.response.BookResponse;

import java.util.List;

public interface StudentService {

    void addStudent(StudentSignUpRequest signUpRequest);

    void subscribe(int id);

    Student getStudentByUserId(int id);

    List<BookResponse> getReadBooks();

}

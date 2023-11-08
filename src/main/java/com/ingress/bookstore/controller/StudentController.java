package com.ingress.bookstore.controller;

import com.ingress.bookstore.models.base.BaseResponse;
import com.ingress.bookstore.models.domain.Book;
import com.ingress.bookstore.models.response.BookResponse;
import com.ingress.bookstore.services.student.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("subscribe/{id}")
    public BaseResponse<Void> subscribe(@PathVariable int id) {
        studentService.subscribe(id);
        return BaseResponse.success();
    }

    @GetMapping("get-books")
    public BaseResponse<List<BookResponse>> getByStudentReadBooks() {
        return BaseResponse.success(studentService.getReadBooks());
    }

}

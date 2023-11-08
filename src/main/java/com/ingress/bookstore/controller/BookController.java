package com.ingress.bookstore.controller;

import com.ingress.bookstore.models.base.BaseResponse;
import com.ingress.bookstore.models.request.BookRequest;
import com.ingress.bookstore.models.response.BookResponse;
import com.ingress.bookstore.models.response.StudentResponse;
import com.ingress.bookstore.services.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BaseResponse<BookResponse> readBook(@PathVariable int id) {
        return BaseResponse.success(bookService.readBook(id));
    }

    @GetMapping("readers/{id}")
    public BaseResponse<List<StudentResponse>> allReaders(@PathVariable int id) {
        return BaseResponse.success(bookService.getAllReaders(id));
    }

    @PostMapping()
    public BaseResponse<Void> createBook(@RequestBody BookRequest bookRequest) {
        bookService.createBook(bookRequest);
        return BaseResponse.success();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return BaseResponse.success();
    }

}

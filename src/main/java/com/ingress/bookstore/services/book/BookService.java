package com.ingress.bookstore.services.book;

import com.ingress.bookstore.models.request.BookRequest;
import com.ingress.bookstore.models.response.BookResponse;
import com.ingress.bookstore.models.response.StudentResponse;

import java.util.List;

public interface BookService {

    BookResponse readBook(int id);

    List<StudentResponse> getAllReaders(int id);

    void createBook(BookRequest bookRequest);

    void deleteBook(int id);

}

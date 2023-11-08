package com.ingress.bookstore.services.author;

import com.ingress.bookstore.models.domain.Author;
import com.ingress.bookstore.models.request.AuthorSignUpRequest;
import com.ingress.bookstore.models.response.StudentResponse;

import java.util.List;

public interface AuthorService {

    void addAuthor(AuthorSignUpRequest authorSignUpRequest);

    Author getAuthorByUserId(int id);

    List<StudentResponse> getSubscriber(int id);

}

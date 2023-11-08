package com.ingress.bookstore.services.book;

import com.ingress.bookstore.exception.types.NotFoundException;
import com.ingress.bookstore.models.domain.Author;
import com.ingress.bookstore.models.domain.Book;
import com.ingress.bookstore.models.domain.Student;
import com.ingress.bookstore.models.domain.User;
import com.ingress.bookstore.models.enums.Authority;
import com.ingress.bookstore.models.request.BookRequest;
import com.ingress.bookstore.models.response.BookResponse;
import com.ingress.bookstore.models.response.StudentResponse;
import com.ingress.bookstore.repository.BookRepo;
import com.ingress.bookstore.services.author.AuthorService;
import com.ingress.bookstore.services.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final StudentService studentService;
    private final BookRepo bookRepo;

    private BookResponse getBook(int id) {
        Book b = bookRepo.getById(id).orElseThrow(
                () -> new NotFoundException(String.format("Book not found with id: %d", id))
        );
        return BookResponse.builder()
                .name(b.getName())
                .author(b.getAuthor().getName())
                .build();
    }

    @Override
    public BookResponse readBook(int id) {
        BookResponse b = getBook(id);
        User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth.getAuthority() == Authority.STUDENT) {
            Student s = studentService.getStudentByUserId(auth.getId());
            if (!bookRepo.isReadByStudent(s.getId(), id)) {
                bookRepo.read(id, s.getId());
            }
        }
        return b;
    }

    @Override
    public List<StudentResponse> getAllReaders(int id) {
        return bookRepo.getAllReaders(id).get().stream()
                .map(student -> StudentResponse.builder()
                        .name(student.getName())
                        .age(student.getAge())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void createBook(BookRequest bookRequest) {
        User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Author author = authorService.getAuthorByUserId(auth.getId());
        bookRepo.save(Book.builder()
                .name(bookRequest.getName())
                .author(author)
                .build());
        /*
            List<StudentResponse> subscriber = authorService.getSubscriber(author.getId());
            todo: send email to subscriber
         */
    }

    @Override
    public void deleteBook(int id) {
        bookRepo.delete(id);
    }
}

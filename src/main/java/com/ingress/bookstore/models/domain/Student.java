package com.ingress.bookstore.models.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    int id;
    int userId;
    int age;
    String name;
    List<Book> readBooks;

}

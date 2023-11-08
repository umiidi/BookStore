package com.ingress.bookstore.models.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    int id;
    String name;
    Author author;

}

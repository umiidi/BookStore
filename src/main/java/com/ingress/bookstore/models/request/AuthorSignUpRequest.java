package com.ingress.bookstore.models.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorSignUpRequest{

    String name;
    int age;
    String username;
    String password;

}

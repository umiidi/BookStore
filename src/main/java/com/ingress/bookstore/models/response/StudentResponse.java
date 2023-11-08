package com.ingress.bookstore.models.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponse {

    String name;
    int age;
}

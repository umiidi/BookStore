package com.ingress.bookstore.models.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

    String name;
    String author;

}

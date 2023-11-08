package com.ingress.bookstore.services.user;

import org.springframework.stereotype.Service;
import com.ingress.bookstore.models.domain.User;

@Service
public interface UserService {

    User getByUsername(String username);

    int addUser(User user);

}

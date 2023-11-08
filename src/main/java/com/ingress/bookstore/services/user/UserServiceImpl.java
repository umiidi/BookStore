package com.ingress.bookstore.services.user;

import com.ingress.bookstore.exception.types.NotAvailableException;
import com.ingress.bookstore.models.domain.User;
import com.ingress.bookstore.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public User getByUsername(String username) throws UsernameNotFoundException {
        return userRepo.getByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );
    }

    @Override
    public int addUser(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new NotAvailableException("username is already in use");
        }
        return userRepo.save(user);
    }

}

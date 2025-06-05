package ru.javawebinar.topjavagraduation.web.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjavagraduation.service.UserService;


@Service("authorizedUserService")
public class AuthorizedUserService implements UserDetailsService {

    UserService userService;

    public AuthorizedUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(userOptional.get());
    }
}

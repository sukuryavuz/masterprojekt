package com.fhcampuswien.masterprojekt.security.jwt;

import com.fhcampuswien.masterprojekt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.fhcampuswien.masterprojekt.entity.User user = userService.getMitarbeiterByUsername(username);
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

}

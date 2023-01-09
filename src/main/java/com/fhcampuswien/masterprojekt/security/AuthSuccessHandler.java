package com.fhcampuswien.masterprojekt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fhcampuswien.masterprojekt.entity.User;
import com.fhcampuswien.masterprojekt.security.jwt.JwtUserDetailsService;
import com.fhcampuswien.masterprojekt.service.UserService;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final int expTime;
  private final String secret;
  private final JwtUserDetailsService userDetailsService;
  private final UserService userService;

  @Autowired
  public AuthSuccessHandler(
      @Value("${jwt.expiration}") int expTime,
      @Value("${jwt.secret}") String secret,
      JwtUserDetailsService userDetailsService,
      UserService userService) {
    this.expTime = expTime;
    this.secret = secret;
    this.userDetailsService = userDetailsService;
    this.userService = userService;
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    UserDetails principal = (UserDetails) authentication.getPrincipal();
    User user = userService.getUserByUsername(principal.getUsername());

    String token =
        JWT.create()
            .withSubject(
                userDetailsService.loadUserByUsername(principal.getUsername()).getUsername())
            .withExpiresAt(
                Date.from(
                    Instant.ofEpochMilli(
                        ZonedDateTime.now(ZoneId.systemDefault()).toInstant().toEpochMilli()
                            + expTime)))
            .sign(Algorithm.HMAC256(secret));
    response.addHeader("Authorization", "Bearer " + token);
    response.addHeader("Content-Type", "application/json");
    response
        .getWriter()
        .print("{\"token\": \"" + token + "\"," + "\t\"id\": \"" + user.getId() + "\"\n," + "}");
  }
}

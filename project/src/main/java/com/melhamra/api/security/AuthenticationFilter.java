package com.melhamra.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melhamra.api.SpringApplicationContext;
import com.melhamra.api.dtos.UserDto;
import com.melhamra.api.requests.UserLoginRequest;
import com.melhamra.api.services.UserService;
import com.melhamra.api.services.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequest user = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User springUser = (User) authResult.getPrincipal();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto user = userService.getUserByEmail(springUser.getUsername());

        String jwt = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getUserId())
                .claim("name", user.getFirstName() + " " + user.getLastName())
                .claim("roles", springUser.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();

        response.addHeader(SecurityConstants.HEADER_STRING, jwt);
        response.addHeader("id", user.getUserId());
        response.getWriter()
                .write("{\"token\": \""+jwt+"\", \"id\": \""+user.getUserId()+"\"}");
    }
}

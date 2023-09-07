

package com.spring.securityPractice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.securityPractice.SpringApplicationContext;
import com.spring.securityPractice.constants.AppConstants;
import com.spring.securityPractice.model.UserDto;
import com.spring.securityPractice.service.UserService;
import com.spring.securityPractice.utils.JWTUtils;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.spring.securityPractice.model.UserLoginRequestModel;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserLoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword())
            );
        } catch (IOException e) {
            log.error("IOException occurred at attemptAuthentication method: {}", e.getMessage());
            throw new RuntimeException("Failed to read request data", e);
        } catch (AuthenticationException authException) {
            log.error("AuthenticationException occurred at attemptAuthentication method: {}", authException.getMessage());
            throw authException;
        } catch (Exception ex) {
            log.error("Exception occurred at attemptAuthentication method: {}", ex.getMessage());
            throw new RuntimeException("Authentication failed", ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        try {
            String user = ((User) authResult.getPrincipal()).getUsername();
            String accessToken = JWTUtils.generateToken(user);
            UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
            UserDto userDto = userService.getUser(user);

            // Create a JSON response
            String jsonResponse = "{\"userId\":\"" + userDto.getUserId() + "\",\"" + AppConstants.HEADER_STRING + "\":\"" + AppConstants.TOKEN_PREFIX + accessToken + "\"}";

            // Set the HTTP response status and content type
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");

            try {
                // Get the response writer and write the JSON response to the response body
                PrintWriter writer = response.getWriter();
                writer.write(jsonResponse);
                writer.flush();
            } catch (IOException e) {
                // Handle IOException
                log.error("IOException occurred while writing response: {}", e.getMessage());
                throw new RuntimeException("Failed to write response", e);
            }
        } catch (AuthenticationException authException) {
            log.error("AuthenticationException occurred at successfulAuthentication method: {}", authException.getMessage());
            throw authException;
        } catch (Exception ex) {
            log.error("Exception occurred at successfulAuthentication method: {}", ex.getMessage());
            throw new RuntimeException("Authentication succeeded, but response handling failed", ex);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        try {
            // Handle unsuccessful authentication here
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set the status code for unauthorized
            response.setContentType("application/json");

            String jsonResponse = "{\"error\":\"Authentication failed\",\"message\":\"" + failed.getMessage() + "\"}";

            try {
                // Get the response writer and write the JSON response to the response body
                PrintWriter writer = response.getWriter();
                writer.write(jsonResponse);
                writer.flush();
            } catch (IOException e) {
                // Handle IOException
                log.error("IOException occurred while writing response: {}", e.getMessage());
                throw new RuntimeException("Failed to write response", e);
            }
        } catch (AuthenticationException authException) {
            log.error("AuthenticationException occurred at unsuccessfulAuthentication method: {}", authException.getMessage());
            throw authException;
        } catch (Exception ex) {
            log.error("Exception occurred at unsuccessfulAuthentication method: {}", ex.getMessage());
            throw new RuntimeException("Authentication failed, but response handling failed", ex);
        }
    }
}

package com.blog.Controllers;

import com.blog.Exceptions.BasCredentialException;
import com.blog.Payloads.JWTAuthRequest;
import com.blog.Payloads.JWTAuthResponse;
import com.blog.Payloads.UserDto;
import com.blog.Security.JWTTockenHelper;
import com.blog.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JWTTockenHelper tokenhelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> createToken(
            @RequestBody JWTAuthRequest request
            ) throws Exception {
        this.authenticate(request.getUserName(), request.getPassword());

        String token = this.tokenhelper.generateToken(this.userDetailsService.loadUserByUsername(request.getUserName()));

        JWTAuthResponse response = new JWTAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response,OK);
    }

    private void authenticate(String userName, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e){
            throw new BasCredentialException("Invalid Credentials");
        }
    }

    // register new user

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(
            @RequestBody UserDto userDto
            ){
        return new ResponseEntity<>(this.userService.registerNewUser(userDto),CREATED);
    }
}

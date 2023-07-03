package com.blog.Security;

import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from DB
        return new CustomUserDetails(this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email :" + username, 0)));
    }
}

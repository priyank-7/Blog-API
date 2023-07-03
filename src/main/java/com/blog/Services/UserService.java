package com.blog.Services;

import com.blog.Payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto user);

    UserDto updateUser (UserDto user, Integer id);

    UserDto getUserById(Integer id);
    List<UserDto> getAllUsers();
    void deleteUser(Integer id);
}

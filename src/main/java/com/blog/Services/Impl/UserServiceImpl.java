package com.blog.Services.Impl;

import com.blog.Config.AppConstants;
import com.blog.Entities.Role;
import com.blog.Entities.User;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Payloads.UserDto;
import com.blog.Repositories.RoleRepository;
import com.blog.Repositories.UserRepository;
import com.blog.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        return userToUserDto(this.userRepository.save(userDtoToUser(userDto)));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        return userToUserDto(this.userRepository.save(userDtoToUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User","Id", id));
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        return userToUserDto(this.userRepository.save(updateUserDtoToUser(userDto,user, id)));
    }

    @Override
    public UserDto getUserById(Integer id) {
        return userToUserDto(this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User","Id", id)));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(this::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer id) {
        this.userRepository.delete(this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id", id)));
    }

    private User userDtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);
        user.setRoles(Set.of(this.roleRepository.findById(AppConstants.NORMAL_USER_ROLE_ID).get()));
        return user;
    }

    private User updateUserDtoToUser(UserDto userDto, User user, Integer id){
        user = this.modelMapper.map(userDto,User.class);
        user.setId(id);
        return user;
    }

    private UserDto userToUserDto(User user){
        return this.modelMapper.map(user, UserDto.class);
    }
}

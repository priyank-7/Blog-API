package com.blog.Controllers;

import com.blog.Payloads.ApiResponse;
import com.blog.Payloads.UserDto;
import com.blog.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST -Create User
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(this.userService.createUser(userDto));
    }

    // PUT - Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id){
        return ResponseEntity.ok(this.userService.updateUser(userDto,id));
    }

    // GET - Get User
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    // GET - Get All Users
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // DELETE - Delete User
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        this.userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse(true,"User Deleted Successfully"));
    }
}



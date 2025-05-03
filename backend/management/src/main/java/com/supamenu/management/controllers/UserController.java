package com.supamenu.management.controllers;

import com.supamenu.management.models.User;
import com.supamenu.management.payload.request.CreateUserDTO;
import com.supamenu.management.payload.request.UpdateUserDTO;
import com.supamenu.management.payload.response.ApiResponse;
import com.supamenu.management.service.UserService;
import com.supamenu.management.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/supa/users")
@RequiredArgsConstructor
public class UserController{
    private final UserService userService;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping(path = "/current-user")
    public ResponseEntity<ApiResponse> currentLoggedInUser(){
        return ResponseEntity.ok(ApiResponse.success("Currently logged in user fetched", userService.getLoggedInUser()));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateUserDTO dto){
        User updated = this.userService.update(this.userService.getLoggedInUser().getId(), dto);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", updated));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ){
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", this.userService.getAll(pageable)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "q") String q
    ){
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        return ResponseEntity.ok(ApiResponse.success("User fetched successfully", this.userService.search(pageable,q)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable(value = "id") UUID id){
        return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", this.userService.getById(id)));
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid CreateUserDTO dto){
        User user = new User();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMobile(dto.getMobile());
        user.setPassword(encodedPassword);

        User entity = this.userService.create(user);
        return ResponseEntity.ok(ApiResponse.success("User created successfully", entity));
    }
}

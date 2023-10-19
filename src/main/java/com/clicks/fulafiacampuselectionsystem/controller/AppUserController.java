package com.clicks.fulafiacampuselectionsystem.controller;

import com.clicks.fulafiacampuselectionsystem.dto.UserDto;
import com.clicks.fulafiacampuselectionsystem.service.AppUserService;
import com.clicks.fulafiacampuselectionsystem.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class AppUserController {

    private final AppUserService userService;

    @PostMapping("")
    public ResponseEntity<CustomResponse> register(@RequestBody UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getUser(@PathVariable Long id) {
        UserDto user = userService.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, user));
    }

    @GetMapping("/{page}")
    public ResponseEntity<CustomResponse> getUsers(
            @PathVariable Integer page,
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "election", required = false) String election) {
        List<UserDto> users = userService.getUsers(role, election, page);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, users));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> update(
            @RequestBody UserDto userDto,
            @PathVariable Long id) {
        userService.update(id, userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

}



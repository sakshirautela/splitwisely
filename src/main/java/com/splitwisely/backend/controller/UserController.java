package com.splitwisely.backend.controller;

import com.splitwisely.backend.dto.UserRequestDTO;
import com.splitwisely.backend.dto.UserResponseDTO;
import com.splitwisely.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> editUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO userDetails
    ) {
        return ResponseEntity.ok(userService.editUser(id, userDetails));
    }
}

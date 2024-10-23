/**
 * @author Starling Diaz on 10/20/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/20/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.controller;

import com.starlingdiaz.humanresourcesmanagement.dto.UserDTO;
import com.starlingdiaz.humanresourcesmanagement.exception.TokenExpiredException;
import com.starlingdiaz.humanresourcesmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto) throws Exception{
        UserDTO userResponse = userService.registerUser(userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("User updated successfully.");
    }

    @GetMapping("/activate-account")
    @Operation(summary = "Activate account", description = "Activate user account")
    @ApiResponse(responseCode = "200", description = "Account activated successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> activateAccount(@RequestParam String token) throws TokenExpiredException, MessagingException {
        userService.activateAccount(token);
        return ResponseEntity.ok("Account activated successfully");
    }
}

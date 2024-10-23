/**
 * @author Starling Diaz on 10/21/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/21/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.controller;

import com.starlingdiaz.humanresourcesmanagement.dto.AdminDTO;
import com.starlingdiaz.humanresourcesmanagement.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/register-admin")
    public ResponseEntity<String> registerUser(@RequestBody AdminDTO adminDTO) {
        adminService.registerAdmin(adminDTO);
        return ResponseEntity.ok("Admin registered successfully.");
    }

    @PutMapping("/update-admin/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        adminService.updateAdmin(id, adminDTO);
        return ResponseEntity.ok("Admin updated successfully.");
    }
}

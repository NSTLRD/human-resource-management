/**
 * @author Starling Diaz on 10/22/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/22/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.controller;

import com.starlingdiaz.humanresourcesmanagement.dto.EmployeeDTO;
import com.starlingdiaz.humanresourcesmanagement.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @PostMapping("/register-employee")
    public ResponseEntity<String> registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.ok("Employee registered successfully.");
    }

    @PutMapping("/update-employee/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok("Employee updated successfully.");
    }

    @DeleteMapping("/delete-employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }

    @GetMapping("/get-all-employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}

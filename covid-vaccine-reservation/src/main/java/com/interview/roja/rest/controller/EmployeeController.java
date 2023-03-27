package com.interview.roja.rest.controller;

import com.interview.roja.rest.dto.EmployeeDTO;
import com.interview.roja.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = ("/employee"))
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping({"/list-employee", "/list-employee/{employeeId}"})
    public ResponseEntity<List<EmployeeDTO>> listEmployee(
            @PathVariable(required = false) Integer employeeId) {
        return ResponseEntity.ok(employeeService.listEmployee(employeeId));
    }
}

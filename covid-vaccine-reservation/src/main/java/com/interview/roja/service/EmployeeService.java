package com.interview.roja.service;

import com.interview.roja.rest.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDTO> listEmployee(Integer employeeId);
}

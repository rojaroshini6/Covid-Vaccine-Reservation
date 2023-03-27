package com.interview.roja.service.impl;

import com.interview.roja.data.model.Employee;
import com.interview.roja.data.repository.EmployeeRepository;
import com.interview.roja.rest.dto.EmployeeDTO;
import com.interview.roja.service.EmployeeService;
import com.interview.roja.util.EmployeeRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> listEmployee(Integer employeeId) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        if (employeeId != null) {
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if (employee.isPresent()) {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setId(employee.get().getId());
                employeeDTO.setName(employee.get().getName());
                employeeDTO.setDutyScheduleTimeFrom(employee.get().getDutyScheduleTimeFrom());
                employeeDTO.setDutyScheduleTimeTo(employee.get().getDutyScheduleTimeTo());
                employeeDTO.setRole(EmployeeRole.getEmployeeRoleMap().get(employee.get().getRole()));
                employeeDTO.setCentreName(employee.get().getVaccinationCentre().getCentreName());
                employeeDTOs.add(employeeDTO);
            }
        } else {
            List<Employee> employees = employeeRepository.findAll();
            if (!CollectionUtils.isEmpty(employees)) {
                for (Employee employee : employees) {
                    EmployeeDTO employeeDTO = new EmployeeDTO();
                    employeeDTO.setId(employee.getId());
                    employeeDTO.setName(employee.getName());
                    employeeDTO.setDutyScheduleTimeFrom(employee.getDutyScheduleTimeFrom());
                    employeeDTO.setDutyScheduleTimeTo(employee.getDutyScheduleTimeTo());
                    employeeDTO.setRole(EmployeeRole.getEmployeeRoleMap().get(employee.getRole()));
                    employeeDTO.setCentreName(employee.getVaccinationCentre().getCentreName());
                    employeeDTOs.add(employeeDTO);
                }
            }
        }
        return employeeDTOs;
    }
}

package com.interview.roja.data.repository;

import com.interview.roja.data.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> findAll();

    @Query("from Employee where vaccination_centre_id = :vaccinationCentreId and employee_role = 2")
    List<Employee> findAllNurseForCenterId(
            @Param(value = "vaccinationCentreId") Integer vaccinationCentreId);
}

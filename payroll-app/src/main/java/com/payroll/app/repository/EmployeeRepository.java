package com.payroll.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payroll.app.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

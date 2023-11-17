package com.payroll.app.exception;

public class EmployeeNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 5032519432470899799L;

	public EmployeeNotFoundException(Long id) {
		super("Cound not find an employee with id: " + id);
	}
}

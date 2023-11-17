package com.payroll.app.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payroll.app.exception.EmployeeNotFoundException;
import com.payroll.app.helper.EmployeeModelAssembler;
import com.payroll.app.model.Employee;
import com.payroll.app.repository.EmployeeRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class EmployeeController {

	private final EmployeeRepository employeeRepository;
	private final EmployeeModelAssembler assembler;

	public EmployeeController(EmployeeRepository employeeRepository, EmployeeModelAssembler assembler) {
		this.employeeRepository = employeeRepository;
		this.assembler = assembler;
	}

	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> all() {
		List<EntityModel<Employee>> employees = employeeRepository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}
	
	@GetMapping("/employees/{id}")
	public EntityModel<Employee> one(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return assembler.toModel(employee);
	}

	@PostMapping("/employees")
	Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		// find employee with given id, if found update it or else add it
		return employeeRepository.findById(id).map(e -> {
			e.setName(newEmployee.getName());
			e.setRole(newEmployee.getRole());
			return employeeRepository.save(e);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return employeeRepository.save(newEmployee);
		});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		employeeRepository.deleteById(id);
	}

	@GetMapping("/setcookie")
	public String setCookie(HttpServletResponse response) throws UnsupportedEncodingException {
		// test cookie
		Cookie cookie = new Cookie("TEST_COOKIE", "test_value");
		cookie.setHttpOnly(false);
		cookie.setMaxAge(1*60*60);
		response.addCookie(cookie);
 
		/** 
		 * 
		 * Example of cookie with spaces or other possible special characters
		 * 	according to HTTP State Management Mechanism (RFC 6265), special characters including spaces 
		 * 	are not allowed in cookie values, so it needs to be URLEncoded using @URLEncoder#encode if required,
		 * 	otherwise it will throw exception during parsing of the value.
		 * 
		 * 	when retrieving cookie value, it can be decoded using @URLEncoder#decode
		 * 
		 */
		cookie = new Cookie("TEST_COOKIE_WITH_SPACE", URLEncoder.encode("This is a value with spaces","UTF-8"));
		cookie.setHttpOnly(true);
		cookie.setMaxAge(1*60*60);
		cookie.setSecure(true);
		response.addCookie(cookie);
		
		return "Cookies are set";
	}
}

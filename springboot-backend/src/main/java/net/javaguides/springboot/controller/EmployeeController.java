package net.javaguides.springboot.controller;


import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

//  @Autowired is used for dependency injection

    @Autowired
    private EmployeeRepository employeeRepository;


//    build create employee REST API
//    @RequestBody (convert JSON into JAVA object

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

//   build get employee REST API

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }


//    build get employee by id  REST API
//    ResponseEntity ( very helpful class we use this class to create the response of rest api )
//    @PathVariable  ( The @PathVariable annotation is used to extract the value from the URI. It is most suitable for the RESTful web service where the URL contains some value.)

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
        return  ResponseEntity.ok(employee);
    }

// build update employee REST API

    @PutMapping("{id}")
    public ResponseEntity<Employee>  updateEmployee(@PathVariable long id , @RequestBody Employee employeeDetails){
        Employee updateEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmailId(employeeDetails.getEmailId());

        employeeRepository.save(updateEmployee);
        return  ResponseEntity.ok(updateEmployee);
    }

// build delete employee REST API

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.naushad.asyncprogramming.controller;

import com.naushad.asyncprogramming.model.Employee;
import com.naushad.asyncprogramming.model.EmployeeProjectDetails;
import com.naushad.asyncprogramming.model.Response;
import com.naushad.asyncprogramming.services.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller class for employee related API
 * @author Naushad Alam
 */

@RestController
@RequestMapping("/async")
public class EmployeeController {
    @Autowired
    private EmpService empService;

    @GetMapping("/id")
    public Response<Employee> getEmployeeById(@PathVariable int id){
        Response<Employee> response = new Response<>();
        Employee employee = empService.getEmployeeById(id);
        if(employee != null){
            response.setData(employee);
            response.setSuccessMessage("Fetched employee successfully");
        }else{
            response.setErrorMessage("Not found any data for given employee Id");
        }
        return response;
    }

    @GetMapping("/all")
    public Response<List<Employee>> getAllEmployees(){
        Response<List<Employee>> response = new Response<>();
        List<Employee> employeeList = empService.getAllEmployee();
        if(employeeList != null && employeeList.size() >0){
            response.setData(employeeList);
            response.setSuccessMessage("Got all active record successfully");
        }else{
            response.setErrorMessage("Not found any active records");
        }
        return response;
    }

    @GetMapping("/emp/projects/empId")
    public Response<EmployeeProjectDetails> getAllProjectsOfEmp(@PathVariable int empId){
        Response<EmployeeProjectDetails> response = new Response<>();
        EmployeeProjectDetails employeeProjectDetails = empService.getAllEmployeeProjectsById(empId);
        if(employeeProjectDetails != null){
            response.setData(employeeProjectDetails);
            response.setSuccessMessage("Got all projects of " + employeeProjectDetails.getEmpName());
        }else{
            response.setErrorMessage("Not found any projects for employee id - " + empId);
        }
        return response;
    }


}

package com.naushad.asyncprogramming.services;

import com.naushad.asyncprogramming.Repositroy.EmployeeRepo;
import com.naushad.asyncprogramming.Repositroy.ProjectRepo;
import com.naushad.asyncprogramming.model.EmpProjectMapping;
import com.naushad.asyncprogramming.model.Employee;
import com.naushad.asyncprogramming.model.EmployeeProjectDetails;
import com.naushad.asyncprogramming.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmpService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepo.class);

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ProjectRepo projectRepo;

    public Employee getEmployeeById(Integer empId){
        CompletableFuture<Employee> completableFuture =  employeeRepo.getEmployeeById(empId);
        Employee employee = null;
        try{
            employee = completableFuture.get();
        }catch (Exception exception){
            log.error("Exception occurred while fetching employee details for empId - " + empId);
        }
        return employee;
    }

    public List<Employee> getAllEmployee(){
        CompletableFuture<List<Employee>> empListFuture =  employeeRepo.getAllEmployee();
        List<Employee> employeeList = new ArrayList<>();
        try{
            employeeList = empListFuture.get();
        }catch (Exception ex){
            log.error("Exception occurred while fetching employee list from future object");
        }
        return employeeList;
    }

    public EmployeeProjectDetails getAllEmployeeProjectsById(Integer empId){
        List<EmpProjectMapping> empProjectMappings =  employeeRepo.getAllProjectOfEmp(empId);
        List<CompletableFuture<Project>> projectList = new ArrayList<>();
        empProjectMappings.forEach(empProjectMapping -> {
            CompletableFuture<Project> project = projectRepo.getProjectById(empProjectMapping.getProjectId());
            projectList.add(project);
        });
        log.info("Waiting for all projects to be fetched from DB for empId -" + empId);
        CompletableFuture.allOf(projectList.toArray(new CompletableFuture[empProjectMappings.size()])).join();

        Employee employee = getEmployeeById(empId);
        List<Project> projects = new ArrayList<>();
        for(CompletableFuture<Project> projectCompletableFuture : projectList){
            try {
                Project project =  projectCompletableFuture.get();
                if(project != null)
                    projects.add(project);
            } catch (Exception e) {
                log.error("Exception occurred while getting projectList from future object");
            }
        }
        return mapEmpProjectDetails(employee,projects);
    }

    public EmployeeProjectDetails mapEmpProjectDetails(Employee employee, List<Project> projectList){
        EmployeeProjectDetails employeeProjectDetails = new EmployeeProjectDetails();
        if(employee != null){
            employeeProjectDetails.setEmpId(employee.getEmpId());
            employeeProjectDetails.setEmpName(employee.getFirstName() + " " + employee.getLastName());
            employeeProjectDetails.setEmail(employee.getEmail());
            employeeProjectDetails.setPhoneNo(employee.getPhoneNo());
            employeeProjectDetails.setDestination(employee.getDestination());
            employeeProjectDetails.setLocation(employee.getLocation());
        }
       if(projectList != null){
           employeeProjectDetails.setProjects(projectList);
       }

       return employeeProjectDetails;
    }
}

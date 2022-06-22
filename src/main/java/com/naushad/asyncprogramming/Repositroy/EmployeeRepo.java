package com.naushad.asyncprogramming.Repositroy;

import com.naushad.asyncprogramming.exception.CustomException;
import com.naushad.asyncprogramming.model.EmpProjectMapping;
import com.naushad.asyncprogramming.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class EmployeeRepo {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepo.class);

    @Autowired
    private NamedParameterJdbcTemplate dbRepository;

    public static final String GET_EMPLOYEE_DETAIL_BY_ID = "SELECT * FROM Employee WHERE empId=:empId";
    public static final String GET_ALL_EMPLOYEE = "SELECT * FROM Employee";
    public static final String GET_ALL_PROJECTS_BY_EMPID = "SELECT * FROM EmpProjectMapping Where empId=:empId";

    @Async
    public CompletableFuture<Employee> getEmployeeById(Integer empId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("empId", empId);
        Employee employee;
        try {
            employee = dbRepository.queryForObject(GET_EMPLOYEE_DETAIL_BY_ID,
                    parameterSource, BeanPropertyRowMapper.newInstance(Employee.class));
        } catch (EmptyResultDataAccessException e) {
            // if specified employee id not exists
            throw new CustomException("No record exist for employee id - " + empId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(employee);
    }

    @Async
    public CompletableFuture<List<Employee>> getAllEmployee(){
        List<Employee> employeeList = new ArrayList<>();
        try{
            employeeList = dbRepository.query(GET_ALL_EMPLOYEE,
                    new MapSqlParameterSource(),BeanPropertyRowMapper.newInstance(Employee.class));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(employeeList);
    }

    public List<EmpProjectMapping> getAllProjectOfEmp(Integer empId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("empId", empId);
        List<EmpProjectMapping> empProjects = new ArrayList<>();
        try{
            empProjects = dbRepository.query(GET_ALL_PROJECTS_BY_EMPID,
                    parameterSource,BeanPropertyRowMapper.newInstance(EmpProjectMapping.class));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return empProjects;
    }
}

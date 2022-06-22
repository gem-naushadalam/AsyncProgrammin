package com.naushad.asyncprogramming.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeProjectDetails {
    private Integer empId;
    private String empName;
    private String email;
    private String phoneNo;
    private String destination;
    private String location;
    List<Project> projects = new ArrayList<>();
}

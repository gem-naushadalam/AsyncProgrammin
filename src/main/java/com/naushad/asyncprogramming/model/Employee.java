package com.naushad.asyncprogramming.model;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    private Integer empId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String destination;
    private String location;
    private Boolean isActive;
    private Timestamp lastModifiedOn;
    private Integer lastModifiedBy;
}

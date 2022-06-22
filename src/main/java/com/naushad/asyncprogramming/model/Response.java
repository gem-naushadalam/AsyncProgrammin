package com.naushad.asyncprogramming.model;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Response<T> {
    T data;
    String successMessage;
    String errorMessage;
}

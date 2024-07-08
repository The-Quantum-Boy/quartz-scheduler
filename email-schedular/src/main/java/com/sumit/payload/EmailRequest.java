package com.sumit.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
@Setter
@Getter
public class EmailRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String subjects;


    @NotEmpty
    private String body;

    @NotNull
    private LocalDateTime dateTime;


    @NotNull
    private ZoneId timeZone;



}
package com.project.tutornet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest
{
    private String userName;
    private String emailAddress;
    private String mobileNumber;
}

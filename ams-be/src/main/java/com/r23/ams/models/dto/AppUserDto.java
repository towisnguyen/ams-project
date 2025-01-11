package com.r23.ams.models.dto;

import com.r23.ams.models.entities.Role;
import lombok.Data;
import java.io.Serializable;

@Data
public class AppUserDto {
    private long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String birthDate;
    private String gender;
    private String address;
    private String role;
    private String avatar;
    private String status;

}

package com.example.usersservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSaveDTO {

    @NotBlank(message = "Ime je obavezno")
    @Size(max = 50)
    @Pattern(regexp = "^[A-Z].*",message="Ime mora poceti velikim slovom")
    private String firstName;
    @NotBlank(message = "Prezime je obavezno")
    @Size(max = 50)
    @Pattern(regexp = "^[A-Z].*",message="Prezime mora poceti velikim slovom")
    private String lastName;
    @NotBlank(message = "Email je obavezan")
    @Email
    @Size(max = 50)
    private String email;
    @NotBlank(message = "Broj telefona je obavezan")
    @Size(max=18)
    private String phone;

}
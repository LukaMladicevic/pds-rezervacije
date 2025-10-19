package com.example.usersservice.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEmailChangeDTO {
    @Id
    private Integer id;
    @Email(message = "Polje mora biti email")
    @NotBlank(message="Email je obavezan")
    private String email;
}

package com.hotel_booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message="Email must be filled!")
    private String email;

    @NotBlank(message="Password must be filled!")
    private String password;
}

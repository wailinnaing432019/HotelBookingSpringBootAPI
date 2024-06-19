package com.hotel_booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel_booking.entity.Booking;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    private String email;
    private String name;
    private String phoneNumber;
    private String role;
    private List<BookingDTO> bookingList=new ArrayList<>();


}

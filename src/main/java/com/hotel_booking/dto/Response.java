package com.hotel_booking.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;

    private UserDTO user;
    private BookingDTO booking;
    private RoomDTO room;
    private List<BookingDTO> bookingList;
    private List<RoomDTO> roomList;
    private List<UserDTO> userList;
}

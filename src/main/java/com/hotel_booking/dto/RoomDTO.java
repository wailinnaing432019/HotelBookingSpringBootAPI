package com.hotel_booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel_booking.entity.Booking;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

    private Long id;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;
    private String roomType;
    private List<BookingDTO> bookingList;

}

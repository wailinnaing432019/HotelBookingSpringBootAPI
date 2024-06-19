package com.hotel_booking.service.interfac;

import com.hotel_booking.dto.Response;
import com.hotel_booking.entity.Booking;
import org.springframework.stereotype.Service;

@Service
public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response getALLBookings();
    Response cancelBooking(Long bookingId);
}

package com.hotel_booking.service.interfac;

import com.hotel_booking.dto.LoginRequest;
import com.hotel_booking.dto.Response;
import com.hotel_booking.entity.User;

public interface IUserService {

    Response register(User loginRequest);

    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserBookingHistory(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);
}

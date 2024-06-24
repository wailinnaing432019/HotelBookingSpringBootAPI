package com.hotel_booking.controller;

import com.hotel_booking.dto.Response;
import com.hotel_booking.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){

        Response response= userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-id/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId){

        Response response= userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Response> deleteUserById(@PathVariable("userId") String userId){
        Response response= userService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/myInfo")
    public ResponseEntity<Response> getLoggedInUserInfo(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        Response response= userService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-user-bookings/{userId}")
    public ResponseEntity<Response> getUserBookingHistory(@PathVariable("userId") String userId){

        Response response= userService.getUserBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}


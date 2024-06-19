package com.hotel_booking.controller;


import com.hotel_booking.dto.LoginRequest;
import com.hotel_booking.dto.Response;
import com.hotel_booking.entity.User;
import com.hotel_booking.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){
        Response response=userService.register(user);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response=userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<Response> logout(@RequestBody LoginRequest loginRequest){
//        SecurityContextHolder.clearContext();
//        Response response=new Response();
//        response.setMessage("Logout Success");
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }
}

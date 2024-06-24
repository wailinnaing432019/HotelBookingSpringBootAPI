package com.hotel_booking.controller;


import com.hotel_booking.dto.LoginRequest;
import com.hotel_booking.dto.Response;
import com.hotel_booking.entity.User;
import com.hotel_booking.service.interfac.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid User user){
        Response response=userService.register(user);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginRequest loginRequest){
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


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleFieldsErrors(MethodArgumentNotValidException exp){
//        var errors=new HashMap<String,String>();
//        exp.getBindingResult().getAllErrors()
//                .forEach(error -> {
//                    var fieldName=((FieldError) error).getField();
//                    var errorMsg=error.getDefaultMessage();
//                    errors.put(fieldName,errorMsg);
//                });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
}

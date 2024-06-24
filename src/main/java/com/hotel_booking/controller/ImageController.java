package com.hotel_booking.controller;


import com.hotel_booking.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController {
    @Autowired
    private StorageService storageService;

    @GetMapping("uploads/{image}")
    public ResponseEntity<?> downloadImage(@PathVariable String image) throws IOException {
        byte[] imageData=storageService.downloadImageFromFileSystem(image);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

}

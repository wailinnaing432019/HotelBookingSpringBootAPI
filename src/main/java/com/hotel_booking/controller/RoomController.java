package com.hotel_booking.controller;

import com.hotel_booking.dto.Response;
import com.hotel_booking.entity.Room;
import com.hotel_booking.service.interfac.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private IRoomService roomService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewRoom(
            @RequestParam(value = "photo",required = false)MultipartFile photo,
            @RequestParam(value = "roomType",required = false)String roomType,
            @RequestParam(value = "roomPrice",required = false) BigDecimal roomPrice,
            @RequestParam(value = "roomDescription",required = false)String roomDescription
            ) {

        if(photo==null || photo.isEmpty()|| roomType==null || roomType.isBlank()|| roomPrice==null){
            Response response=new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(photo, roomType, roomPrice)");
            ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response=roomService.addNewRoom(photo,roomType,roomPrice,roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms(){

        Response response= roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getAllRoomTypes(){
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable Long roomId){

        Response response= roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-rooms")
    public ResponseEntity<Response> getAllAvailableRooms(){

        Response response= roomService.getAllAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response> getAvailableRoomByDateAndType(
            @RequestParam(value = "checkInDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(value = "checkOutDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(value = "roomType",required = false)String roomType
    ) {

        if(checkInDate==null || checkOutDate==null || roomType==null || roomType.isBlank()){
            Response response=new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields(checkInDate,checkOutDate, roomType )");
           return ResponseEntity.status(response.getStatusCode()).body(response);
        }
        Response response=roomService.getAvailableRoomsByDateAndType(checkInDate ,checkOutDate,roomType );
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity updateRoom(
            @PathVariable("roomId") Long roomId,
            @RequestParam(value = "photo",required = false)MultipartFile photo,
             @RequestParam(value = "rooomType",required = false)String roomType,
             @RequestParam(value = "roomPrice",required = false) BigDecimal roomPrice,
             @RequestParam(value = "roomDescription",required = false)String roomDescription
    ) throws IOException {

        Response response=roomService.updateRoom(roomId,roomType,roomDescription,roomPrice,photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable("roomId") Long roomId){
        Response response=roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }



}

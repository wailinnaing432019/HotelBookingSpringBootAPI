package com.hotel_booking.service.impl;

import com.hotel_booking.dto.Response;
import com.hotel_booking.dto.RoomDTO;
import com.hotel_booking.entity.Room;
import com.hotel_booking.exception.OurException;
import com.hotel_booking.repo.BookingRepository;
import com.hotel_booking.repo.RoomRepository;
import com.hotel_booking.service.StorageService;
import com.hotel_booking.service.interfac.IRoomService;
import com.hotel_booking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private StorageService storageService;
    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {

       Response response = new Response();
       try{

            Room savedImageToSystem=storageService.saveImageToFileSystem(photo);
            Room room=new Room();
            room.setRoomPhotoUrl(savedImageToSystem.getRoomPhotoUrl());
            room.setRoomImg(savedImageToSystem.getRoomImg());
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            room.setRoomType(roomType);

            Room savedRoom=roomRepository.save(room);
           RoomDTO roomDTO= Utils.mapRoomEntityToRoomDTO(savedRoom);

           response.setStatusCode(200);
           response.setMessage("Room created successfully");
           response.setRoom(roomDTO);
       } catch (Exception e){
           response.setStatusCode(500);
           response.setMessage("Error creating new room" + e.getMessage());
       }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {

        Response response = new Response();
        try{
            List<Room> roomList=roomRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<RoomDTO> roomDTOList=Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("Room created successfully");
            response.setRoomList(roomDTOList);
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Getting all rooms" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(long roomId) {
        Response response = new Response();
        try{

            roomRepository.findById(roomId).orElseThrow(()->new OurException("Could not find room"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Room Delete successfully");
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Deleting room" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId,String description, String roomType, BigDecimal roomPrice, MultipartFile photo) throws IOException {

        Response response=new Response();


        try{
            Room room=roomRepository.findById(roomId).orElseThrow(()->new OurException("Room not found"));
            if(photo !=null || !photo.isEmpty()) {
                Room saveRoomImage= storageService.saveImageToFileSystem(photo);
                room.setRoomImg(saveRoomImage.getRoomImg());
                room.setRoomPhotoUrl(saveRoomImage.getRoomPhotoUrl());
            }
            if(roomType!=null) room.setRoomType(roomType);
            if(description!=null) room.setRoomDescription(description);
            if(roomPrice!=null) room.setRoomPrice(roomPrice);

            Room updatedRoom=roomRepository.save(room);
            RoomDTO roomDTO=Utils.mapRoomEntityToRoomDTO(updatedRoom);
            response.setStatusCode(200);
            response.setMessage("Room Update successfully");
            response.setRoom(roomDTO);

        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error updating room" + e.getMessage());
        }
        return response;
    }



    @Override
    public Response getRoomById(long roomId) {

        Response response=new Response();

        try{
            Room room=roomRepository.findById(roomId).orElseThrow(()->new OurException("Room not found"));
             RoomDTO roomDTO=Utils.mapRoomEntityToRoomDTO(room);
            response.setStatusCode(200);
            response.setMessage("successfully");
            response.setRoom(roomDTO);
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting room" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {

        Response response=new Response();

        try{
            List<Room> availableRooms=roomRepository.getAvailableRoomsByDateAndTypes(checkInDate,checkOutDate,roomType);
            List<RoomDTO> roomDTOList=Utils.mapRoomListEntityToRoomListDTO(availableRooms);
            response.setStatusCode(200);
            response.setMessage(" successfully");
            response.setRoomList(roomDTOList);
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting available  room" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response=new Response();

        try{
            List<Room> roomList=roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList=Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("Room Update successfully");
            response.setRoomList(roomDTOList);
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error  getting available  room" + e.getMessage());
        }
        return response;
    }
}

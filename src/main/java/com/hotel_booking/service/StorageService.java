package com.hotel_booking.service;

import com.hotel_booking.entity.Room;
import com.hotel_booking.exception.OurException;
import com.hotel_booking.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class StorageService {

    private final String FOLDER_PATH="C:/Users/user/Desktop/spring/HotelBookingApi/HotelBooking/uploads/images/";

    @Autowired
    private RoomRepository roomRepository;
    public Room saveImageToFileSystem(MultipartFile file) throws IOException {
        String filename=System.currentTimeMillis()+"_Wai_Gyi"+".png";
        String filePath=FOLDER_PATH+filename;

        file.transferTo(new File(filePath));
        Room room=new Room();
        room.setRoomPhotoUrl(filePath);
        room.setRoomImg(filename);


//        responseStudent.setFilePath("http://localhost:8080/uploads/"+responseStudent.getProfileImage());
        return room;
    }

    public byte[] downloadImageFromFileSystem(String image) throws IOException {
//        roomRepository.findByRoomImg(image).orElseThrow(()->new OurException("Image not found"));
        Optional<Room> room=roomRepository.findByRoomImg(image);
        if(room==null || !room.isPresent()){
            throw new OurException("Image not found");
        }
        String filePath=room.get().getRoomPhotoUrl();
        byte[] images= Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}

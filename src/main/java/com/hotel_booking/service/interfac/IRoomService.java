package com.hotel_booking.service.interfac;

import com.hotel_booking.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice,String description);

    List<String> getAllRoomTypes();

    Response getAllRooms();
    Response deleteRoom(long roomId);
    Response updateRoom(Long roomId,String roomType,String description,BigDecimal roomPrice,MultipartFile photo) throws IOException;



    Response getRoomById(long roomId);
    Response getAvailableRoomsByDateAndType(LocalDate checkInDate,LocalDate checkOutDate,String roomType);

    Response getAllAvailableRooms();
}

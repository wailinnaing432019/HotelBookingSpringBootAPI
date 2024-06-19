package com.hotel_booking.repo;

import com.hotel_booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:roomType% AND r.id NOT IN (SELECT bk.room.id FROM Booking bk WHERE"+
            "(bk.checkInDate<=:checkOutDate) AND (bk.checkOutDate>=:checkInDate) )")
    List<Room> getAvailableRoomsByDateAndTypes(LocalDate checkInDate, LocalDate checkOutDate,String roomType);

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b)")
    List<Room> getAllAvailableRooms();


    Optional<Room> findByRoomImg(String image);
}

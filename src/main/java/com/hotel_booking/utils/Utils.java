package com.hotel_booking.utils;

import com.hotel_booking.dto.BookingDTO;
import com.hotel_booking.dto.RoomDTO;
import com.hotel_booking.dto.UserDTO;
import com.hotel_booking.entity.Booking;
import com.hotel_booking.entity.Room;
import com.hotel_booking.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final String ALPHANUMERIC_STRING="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateConfirmationCode(int length){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<length;i++){
            int randomIndex=secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar=ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Room room){
       RoomDTO roomDTO=new RoomDTO();

       roomDTO.setId(room.getId());
       roomDTO.setRoomType(room.getRoomType());
       roomDTO.setRoomPrice(room.getRoomPrice());
       roomDTO.setRoomDescription(room.getRoomDescription());
        // transform to real work for image url for now
       roomDTO.setRoomPhotoUrl("http://localhost:8080/uploads/"+room.getRoomImg());

       return roomDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room){
        RoomDTO roomDTO=new RoomDTO();

        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        // transform to real work for image url for now
        roomDTO.setRoomPhotoUrl("http://localhost:8080/uploads/"+room.getRoomImg());

        if(room.getBookingList()!=null){
            roomDTO.setBookingList(room.getBookingList().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }
        return roomDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        return bookingDTO;
    }

    public static UserDTO mapUserEntityToUserDTOPlusUserBookingAndRoom(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());


        if(!user.getBookingList().isEmpty()){
            userDTO.setBookingList(user.getBookingList().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedRoom(booking, false)).collect(Collectors.toList()));
        }

        return userDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedRoom(Booking booking, boolean mapUser) {

        BookingDTO bookingDTO=new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        if(mapUser){
            bookingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }

        if(booking.getRoom() != null){
            RoomDTO roomDTO=new RoomDTO();

            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());
//            roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
            roomDTO.setRoomPhotoUrl("http://localhost:8080/uploads/"+booking.getRoom().getRoomImg());

            bookingDTO.setRoom(roomDTO);
        }
        return bookingDTO;
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }
    public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList){
        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }
    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList){
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }

}

package com.hotel_booking.service.impl;

import com.hotel_booking.dto.BookingDTO;
import com.hotel_booking.dto.Response;
import com.hotel_booking.entity.Booking;
import com.hotel_booking.entity.Room;
import com.hotel_booking.entity.User;
import com.hotel_booking.exception.OurException;
import com.hotel_booking.repo.BookingRepository;
import com.hotel_booking.repo.RoomRepository;
import com.hotel_booking.repo.UserRepository;
import com.hotel_booking.service.interfac.IBookingService;
import com.hotel_booking.service.interfac.IRoomService;
import com.hotel_booking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private IRoomService iRoomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response=new Response();

        try{

            if(bookingRequest.getCheckInDate().isBefore(bookingRequest.getCheckInDate())){
                throw new IllegalArgumentException("Check in date must be before Check out date");
            }

            Room room=roomRepository.findById(roomId).orElseThrow(()->new OurException("Room not found"));
            User user=userRepository.findById(userId).orElseThrow(()->new OurException("User not found"));

            List<Booking> existingBookings = room.getBookingList();

            if(!roomIsAvailable(bookingRequest,existingBookings)){
                throw new OurException("Room is not available for selected date and range");
            }

            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode= Utils.generateConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);

            bookingRepository.save(bookingRequest);
            response.setBookingConfirmationCode(bookingConfirmationCode);

            response.setStatusCode(200);
            response.setMessage("Success creating booking");
        }catch(OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error creating booking request"+e.getMessage());
        }
       return response;
    }



    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {

        Response response=new Response();

        try{

            Booking booking=bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()->new OurException("Could not find booking"));
            BookingDTO bookingDTO=Utils.mapBookingEntityToBookingDTOPlusBookedRoom(booking,true);
            response.setStatusCode(200);
            response.setMessage("Success finding booking");
            response.setBooking(bookingDTO);
        }catch(OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting booking by confirmation code"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getALLBookings() {

        Response response=new Response();

        try{

            List<Booking> bookings=bookingRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<BookingDTO> bookingsDTOs=Utils.mapBookingListEntityToBookingListDTO(bookings);
            response.setStatusCode(200);
            response.setMessage("Success Getting all booking");
            response.setBookingList(bookingsDTOs);
        }catch(OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Getting all booking request"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {

        Response response=new Response();

        try{

            bookingRepository.findById(bookingId).orElseThrow(()->new OurException("Booking not found"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Success Canceled booking");
        }catch(OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Cancelling booking request"+e.getMessage());
        }
        return response;
    }


    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch( existingBooking->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate()))
                                && (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().isEqual(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().isEqual(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().isAfter(bookingRequest.getCheckInDate()))

                );
    }
}

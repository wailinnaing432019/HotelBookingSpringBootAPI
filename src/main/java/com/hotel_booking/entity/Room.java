package com.hotel_booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingConfirmationCode; // when booking , the confirmationCode is to be added
    @NotBlank(message = "Please enter roomType")
    private String roomType; 
    private BigDecimal roomPrice;
    @NotBlank(message = "Please enter room photo")
    private String roomPhotoUrl; // to call the image from anywhere

    private String roomImg; // to call the image with name
    private String roomDescription;
    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookingList=new ArrayList<>();

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", roomPhotoUrl='" + roomPhotoUrl + '\'' +
                ", roomDescription='" + roomDescription + '\'' +
                '}';
    }
}

package com.mohyehia.multipledatabaseconnection.booking.controller;

import com.mohyehia.multipledatabaseconnection.booking.entity.Booking;
import com.mohyehia.multipledatabaseconnection.booking.service.framework.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{userId}/bookings")
    public ResponseEntity<List<Booking>> retrieveUserBookings(@PathVariable("userId") long userId){
        System.out.println("UserId =>" + userId);
        List<Booking> bookingList = bookingService.findByUserId(userId);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @PostMapping("/{userId}/bookings")
    public ResponseEntity<Booking> addBookingForUser(@PathVariable("userId") long userId, @RequestBody Booking booking){
        System.out.println("UserId =>" + userId);
        booking.setUserId(userId);
        booking = bookingService.save(booking);
        if(booking == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
}

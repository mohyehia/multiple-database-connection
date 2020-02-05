package com.mohyehia.multipledatabaseconnection.booking.service.framework;

import com.mohyehia.multipledatabaseconnection.booking.entity.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> findByUserId(long userId);
    Booking save(Booking booking);
}

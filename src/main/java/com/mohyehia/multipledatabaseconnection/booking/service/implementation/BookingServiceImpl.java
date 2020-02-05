package com.mohyehia.multipledatabaseconnection.booking.service.implementation;

import com.mohyehia.multipledatabaseconnection.booking.dao.BookingDAO;
import com.mohyehia.multipledatabaseconnection.booking.entity.Booking;
import com.mohyehia.multipledatabaseconnection.booking.service.framework.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingDAO bookingDAO;

    @Autowired
    public BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Override
    public List<Booking> findByUserId(long userId) {
        return bookingDAO.findByUserId(userId);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingDAO.save(booking);
    }
}

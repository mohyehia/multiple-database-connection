package com.mohyehia.multipledatabaseconnection.booking.dao;

import com.mohyehia.multipledatabaseconnection.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingDAO extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(long userId);
}

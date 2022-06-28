package com.wipro.booking.service;

import com.wipro.booking.model.Booking;
import com.wipro.booking.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {

    public List<Booking> getBookingsAt(Restaurant restaurant, LocalDate localDate) {
        return restaurant.getBookings().stream()
                .filter(booking -> booking.getStartTime().toLocalDate().isEqual(localDate))
                .collect(Collectors.toList());
    }

    public String getJsonBookingsAt(Restaurant restaurant, LocalDate localDate) {
        String content = this.getBookingsAt(restaurant, localDate).stream()
                .map(Booking::toString)
                .collect(Collectors.joining(", "));
        return "[ " + content + " ]";
    }

    public void book(Restaurant restaurant, Booking booking) {
        restaurant.addBookings(booking);
    }
}

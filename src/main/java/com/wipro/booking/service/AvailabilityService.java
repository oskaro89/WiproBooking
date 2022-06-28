package com.wipro.booking.service;

import com.wipro.booking.model.Booking;
import com.wipro.booking.model.OpeningHours;
import com.wipro.booking.model.Restaurant;

import java.time.LocalDateTime;

public class AvailabilityService {

    public boolean isBookingPossible(Restaurant restaurant, Booking booking) {
        boolean restaurantOpen = isRestaurantOpenFor2HoursFromDateTime(restaurant, booking.getStartTime());
        return restaurantOpen && isTableAvailable(restaurant, booking);
    }

    public boolean isRestaurantOpenFor2HoursFromDateTime(Restaurant restaurant, LocalDateTime dateTime) {
        OpeningHours openingHours = restaurant.getCalendar().get(dateTime.toLocalDate());
        return isDateTimeBetweenOpeningHoursFor2Hours(dateTime, openingHours);
    }

    private boolean isDateTimeBetweenOpeningHoursFor2Hours(LocalDateTime dateTime, OpeningHours openingHours) {
        return openingHours.getOpening().isBefore(dateTime.toLocalTime())
                && openingHours.getClosing().minusHours(2).isAfter(dateTime.toLocalTime());
    }

    private boolean isTableAvailable(Restaurant restaurant, Booking booking) {
        long availableTablesCount = provideRestaurantTablesCountOfSize(restaurant, booking.getTableSize());
        long bookedTablesCount = provideBookedTablesCountOfSameTimeAndSize(restaurant, booking);
        return availableTablesCount > bookedTablesCount;
    }

    private long provideBookedTablesCountOfSameTimeAndSize(Restaurant restaurant, Booking booking) {
        return restaurant.getBookings().stream()
                .filter(b -> b.getTableSize().equals(booking.getTableSize()))
                .filter(b -> !b.getStartTime().isBefore(booking.getStartTime().minusHours(2)))
                .filter(b -> !b.getStartTime().isAfter(booking.getStartTime().plusHours(2)))
                .count();
    }

    private long provideRestaurantTablesCountOfSize(Restaurant restaurant, int size) {
        return restaurant.getTables().stream()
                .filter(table -> table.getCapacity() == size)
                .count();
    }


}

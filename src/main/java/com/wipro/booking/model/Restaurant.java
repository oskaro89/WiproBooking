package com.wipro.booking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class Restaurant {

    private final Map<LocalDate, OpeningHours> calendar;
    private final List<Table> tables;
    private final List<Booking> bookings = new ArrayList<>();

    public void addBookings(Booking... bookings) {
        this.bookings.addAll(Arrays.stream(bookings).collect(Collectors.toList()));
    }

}

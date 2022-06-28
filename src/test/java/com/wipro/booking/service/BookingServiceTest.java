package com.wipro.booking.service;

import com.wipro.booking.model.Booking;
import com.wipro.booking.model.Restaurant;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class BookingServiceTest {

    @Test
    public void givenSingleBooking_whenConverted_shouldReturnCorrectJsonArray() {
        Booking booking = new Booking(LocalDateTime.now().withHour(14), 10, "Oskar");
        Restaurant restaurant = new Restaurant(null, null);
        restaurant.addBookings(booking);

        BookingService bookingService = new BookingService();
        String jsonBookingsAt = bookingService.getJsonBookingsAt(restaurant, LocalDate.now());

        assertEquals("[ " + booking + " ]", jsonBookingsAt);
    }

    @Test
    public void givenSimpleJsonBooking_whenParsed_shouldReturnProperObject() {
        String bookingInput = "    {\n" +
                "        \"tableSize\": 2,\n" +
                "        \"startTime\": \"30.06.2022, 20:00\",\n" +
                "        \"name\": \"Customer\"\n" +
                "    }";

        Booking result = Booking.parse(bookingInput);

        assertEquals(
                new Booking(LocalDateTime.of(2022, 6, 30, 20, 0), 2, "Customer"),
                result);
    }
}

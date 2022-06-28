package com.wipro.booking;

import com.wipro.booking.controller.BookingController;
import com.wipro.booking.model.Booking;
import com.wipro.booking.model.OpeningHours;
import com.wipro.booking.model.Restaurant;
import com.wipro.booking.model.Table;
import com.wipro.booking.service.AvailabilityService;
import com.wipro.booking.service.BookingService;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class WiproBooking {
    public static void main(String[] args) {
        BookingController bookingController = initializeControllerWithData();

        MuServer server = MuServerBuilder.httpServer()
                .addHandler(RestHandlerBuilder.restHandler(bookingController))
                .start();
        System.out.println("Started server at " + server.uri());

    }

    private static BookingController initializeControllerWithData() {
        Map<LocalDate, OpeningHours> calendar = Map.of(
                LocalDate.now(), new OpeningHours(LocalTime.of(12, 0), LocalTime.of(20, 0)),
                LocalDate.now().plusDays(1), new OpeningHours(LocalTime.of(12, 0), LocalTime.of(20, 0)),
                LocalDate.now().plusDays(2), new OpeningHours(LocalTime.of(12, 0), LocalTime.of(20, 0)),
                LocalDate.now().plusDays(3), new OpeningHours(LocalTime.of(12, 0), LocalTime.of(20, 0)),
                LocalDate.now().plusDays(4), new OpeningHours(LocalTime.of(12, 0), LocalTime.of(20, 0)),
                LocalDate.now().plusDays(5), new OpeningHours(LocalTime.of(8, 0), LocalTime.of(22, 0)),
                LocalDate.now().plusDays(6), new OpeningHours(LocalTime.of(8, 0), LocalTime.of(22, 0)));
        List<Table> tables = List.of(
                new Table(2),
                new Table(2),
                new Table(2),
                new Table(2),
                new Table(4),
                new Table(4),
                new Table(4),
                new Table(6),
                new Table(6),
                new Table(10),
                new Table(12));
        Booking booking0 = new Booking(LocalDateTime.now().plusDays(2).withHour(14), 2, "Oskar");
        Booking booking1 = new Booking(LocalDateTime.now().plusDays(2).withHour(14), 2, "Filip");

        Restaurant restaurant = new Restaurant(calendar, tables);
        restaurant.addBookings(booking0, booking1);
        return new BookingController(restaurant, new AvailabilityService(), new BookingService());
    }
}

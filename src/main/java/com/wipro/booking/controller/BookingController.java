package com.wipro.booking.controller;

import com.wipro.booking.model.Booking;
import com.wipro.booking.model.Restaurant;
import com.wipro.booking.service.AvailabilityService;
import com.wipro.booking.service.BookingService;
import io.muserver.rest.ApiResponse;
import lombok.AllArgsConstructor;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

@Path("/booking")
@AllArgsConstructor
public class BookingController {

    Restaurant restaurant;
    AvailabilityService availabilityService;
    BookingService bookingService;

    @GET
    @Path("/{year}/{month}/{day}")
    @Produces("application/json")
    @ApiResponse(code = "200", message = "Success")
    public String getBookings(@PathParam("year") int year, @PathParam("month") int month, @PathParam("day") int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return bookingService.getJsonBookingsAt(restaurant, localDate);
    }

    // TODO add more errors (no such table, restaurant closed)
    // TODO add HATEOAS (decline reservation, change hour, change size, etc)
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @ApiResponse(code = "200", message = "Success")
    @ApiResponse(code = "409", message = "Conflict")
    public String createBooking(String bookingJson) {
        Booking booking = Booking.parse(bookingJson);
        if (!availabilityService.isBookingPossible(restaurant, booking)) {
            throw new ClientErrorException(Response.Status.CONFLICT);
        }
        bookingService.book(restaurant, booking);
        return booking.toString();
    }
}

package com.wipro.booking.service;

import com.wipro.booking.model.Booking;
import com.wipro.booking.model.OpeningHours;
import com.wipro.booking.model.Restaurant;
import com.wipro.booking.model.Table;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AvailabilityServiceTest {

    public static final int TABLE_SIZE = 10;
    public static final int OPENING_HOUR = 8;
    public static final int CLOSING_HOUR = 20;
    public static final String CUSTOMER_NAME = "Oskar";
    
    private Restaurant restaurant;

    @Before
    public void initialize() {
        List<Table> tables = List.of(new Table(TABLE_SIZE));
        restaurant = new Restaurant(
                Map.of(LocalDate.now(), new OpeningHours(
                        LocalTime.of(OPENING_HOUR, 0),
                        LocalTime.of(CLOSING_HOUR, 0))),
                tables);
    }

    @Test
    public void givenTimeBeforeOpening_whenChecked_shouldReturnFalse() {
        LocalDateTime given = LocalDateTime.now().withHour(OPENING_HOUR - 4);
        AvailabilityService service = new AvailabilityService();

        boolean result = service.isRestaurantOpenFor2HoursFromDateTime(restaurant, given);

        assertFalse(result);
    }

    @Test
    public void givenTimeOpenedTime_whenChecked_shouldReturnTrue() {
        LocalDateTime given = LocalDateTime.now().withHour(OPENING_HOUR + 4);
        AvailabilityService service = new AvailabilityService();

        boolean result = service.isRestaurantOpenFor2HoursFromDateTime(restaurant, given);

        assertTrue(result);
    }

    @Test
    public void givenTimeAfterClosing_whenChecked_shouldReturnFalse() {
        LocalDateTime given = LocalDateTime.now().withHour(CLOSING_HOUR + 2);
        AvailabilityService service = new AvailabilityService();

        boolean result = service.isRestaurantOpenFor2HoursFromDateTime(restaurant, given);

        assertFalse(result);
    }

    @Test
    public void givenTimeLessThan2HoursBeforeClosing_whenChecked_shouldReturnFalse() {
        LocalDateTime given = LocalDateTime.now().withHour(CLOSING_HOUR - 1);
        AvailabilityService service = new AvailabilityService();

        boolean result = service.isRestaurantOpenFor2HoursFromDateTime(restaurant, given);

        assertFalse(result);
    }

    @Test
    public void givenCorrectBooking_whenChecked_shouldReturnTrue() {
        Booking booking = new Booking(LocalDateTime.now().withHour(OPENING_HOUR + 2), TABLE_SIZE, CUSTOMER_NAME);

        AvailabilityService service = new AvailabilityService();
        boolean result = service.isBookingPossible(restaurant, booking);

        assertTrue(result);
    }

    @Test
    public void givenNotExistingTableSize_whenChecked_shouldReturnFalse() {
        Booking booking = new Booking(LocalDateTime.now().withHour(OPENING_HOUR + 2), TABLE_SIZE + 10, CUSTOMER_NAME);

        AvailabilityService service = new AvailabilityService();
        boolean result = service.isBookingPossible(restaurant, booking);

        assertFalse(result);
    }

    @Test
    public void givenCorrectTableSizeAfterClosingBooking_whenChecked_shouldReturnFalse() {
        Booking booking = new Booking(LocalDateTime.now().withHour(CLOSING_HOUR + 2), TABLE_SIZE, CUSTOMER_NAME);

        AvailabilityService service = new AvailabilityService();
        boolean result = service.isBookingPossible(restaurant, booking);

        assertFalse(result);
    }

    @Test
    public void givenCorrectTableSizeLessThan2HoursBeforeClosingBooking_whenChecked_shouldReturnFalse() {
        Booking booking = new Booking(LocalDateTime.now().withHour(CLOSING_HOUR - 1), TABLE_SIZE, CUSTOMER_NAME);

        AvailabilityService service = new AvailabilityService();
        boolean result = service.isBookingPossible(restaurant, booking);

        assertFalse(result);
    }

    @Test
    public void givenCorrectTableSizeBeforeOpeningBooking_whenChecked_shouldReturnFalse() {
        Booking booking = new Booking(LocalDateTime.now().withHour(OPENING_HOUR - 4), TABLE_SIZE, CUSTOMER_NAME);

        AvailabilityService service = new AvailabilityService();
        boolean result = service.isBookingPossible(restaurant, booking);

        assertFalse(result);
    }

    @Test
    public void givenCorrectTableSizeThatIsBookedBooking_whenChecked_shouldReturnFalse() {
        restaurant.getBookings().add(new Booking(LocalDateTime.now().withHour(OPENING_HOUR + 2), TABLE_SIZE, CUSTOMER_NAME));
        Booking booking = new Booking(LocalDateTime.now().withHour(OPENING_HOUR + 2), TABLE_SIZE, CUSTOMER_NAME);

        AvailabilityService service = new AvailabilityService();
        boolean result = service.isBookingPossible(restaurant, booking);

        assertFalse(result);
    }
}

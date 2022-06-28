package com.wipro.booking.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Booking {

    private final LocalDateTime startTime;
    private final Integer tableSize;
    private final String name;

    @Override
    public String toString() {
        return new JSONObject()
                .put("startTime", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(startTime))
                .put("tableSize", tableSize)
                .put("name", name)
                .toString(4);
    }

    public static Booking parse(String bookingJson) {
        var bookingJsonObject = new JSONObject(bookingJson);
        Integer tableSize = bookingJsonObject.getInt("tableSize");
        String name = bookingJsonObject.getString("name");
        String startTimeString = bookingJsonObject.getString("startTime");
        LocalDateTime startTime = LocalDateTime.parse(startTimeString, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        return new Booking(startTime, tableSize, name);
    }
}

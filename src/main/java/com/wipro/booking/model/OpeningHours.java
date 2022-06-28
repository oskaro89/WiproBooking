package com.wipro.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class OpeningHours {

    private final LocalTime opening;
    private final LocalTime closing;
}

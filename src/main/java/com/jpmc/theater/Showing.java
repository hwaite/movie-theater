package com.jpmc.theater;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Stream;

public record Showing(Movie movie, LocalTime startTime) {
    public static final String TIME_FORMAT = "H:mm";
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public Showing {Stream.of(movie, startTime).forEach(Objects::requireNonNull);}

    /** @param startTime Start time to be parsed using format {@linkplain #TIME_FORMAT}. */
    public Showing(Movie movie, String startTime) {
        this(movie, LocalTime.parse(startTime, FORMAT));
    }
}

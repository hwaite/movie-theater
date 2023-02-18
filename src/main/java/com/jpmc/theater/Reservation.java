package com.jpmc.theater;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

public record Reservation(
    LocalDate date, Customer customer, Showing showing, int audienceCount, BigDecimal price
) {
    public Reservation {
        Stream.of(date, customer, showing, price).forEach(Objects::requireNonNull);
    }
}

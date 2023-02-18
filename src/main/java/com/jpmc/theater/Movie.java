package com.jpmc.theater;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;

public record Movie(String title, Duration runningTime, BigDecimal price, boolean special) {
    public Movie {Stream.of(title, runningTime, price).forEach(Objects::requireNonNull);}

    public Movie(String title, Duration runningTime, double price) {
        this(title, runningTime, price, false);
    }

    public Movie(String title, Duration runningTime, double price, boolean special) {
        this(title, runningTime, BigDecimal.valueOf(price), special);
    }
}

package com.jpmc.theater.discount;

import com.jpmc.theater.Movie;
import com.jpmc.theater.Schedule;
import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** @see ListIndexDiscountStrategy */
class ListIndexDiscountStrategyTest {
    private final Movie movie = new Movie("title", Duration.ofHours(1), 10);
    private final Schedule schedule = new Schedule(
        Stream.of(
            new Showing(movie, "9:00"),
            new Showing(movie, "10:00")
        )
    );
    private final DiscountStrategy strategy =
     new ListIndexDiscountStrategy(1, new BigDecimal("2.00"));

    @Test
    void testMiss() {
        Assertions.assertEquals(
            BigDecimal.ZERO,
            strategy.getDiscount(LocalDate.of(2023, 2, 17), schedule, 0)
        );
    }

    @Test
    void testHit() {
        MatcherAssert.assertThat(
            BigDecimal.valueOf(2),
            Matchers.comparesEqualTo(
                strategy.getDiscount(LocalDate.of(2023, 2, 3), schedule, 1)
            )
        );
    }
}

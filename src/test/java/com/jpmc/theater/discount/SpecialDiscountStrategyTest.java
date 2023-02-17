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

/** @see SpecialDiscountStrategy */
class SpecialDiscountStrategyTest {
    private final Schedule schedule = new Schedule(
        Stream.of(
            new Showing(
                new Movie("title1", Duration.ofHours(1), 10), "9:00"
            ),
            new Showing(
                new Movie("title2", Duration.ofHours(1), 10, true), "10:00"
            )
        )
    );
    private final DiscountStrategy strategy = new SpecialDiscountStrategy(0.25);

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
            BigDecimal.valueOf(2.5),
            Matchers.comparesEqualTo(
                strategy.getDiscount(LocalDate.of(2023, 2, 17), schedule, 1)
            )
        );
    }
}

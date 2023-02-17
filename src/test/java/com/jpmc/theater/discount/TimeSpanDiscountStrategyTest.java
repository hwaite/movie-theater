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

/** @see TimeSpanDiscountStrategy */
class TimeSpanDiscountStrategyTest {
    private final Movie movie = new Movie("title1", Duration.ofHours(1), 10);
    private final Schedule schedule = new Schedule(
        Stream.of(
            new Showing(movie, "8:00"),
            new Showing(movie, "9:00"),
            new Showing(movie, "10:00"),
            new Showing(movie, "11:00"),
            new Showing(movie, "12:00")
        )
    );
    private final DiscountStrategy strategy =
     new TimeSpanDiscountStrategy("9:00", "11:00", 0.2);

    @Test
    void test() {
        boolean[] hit = new boolean[] {false, true, true, true, false};
        for (int i = 0; i < hit.length; i++) {
            MatcherAssert.assertThat(
                hit[i] ? BigDecimal.valueOf(2) : BigDecimal.ZERO,
                Matchers.comparesEqualTo(
                    strategy.getDiscount(LocalDate.of(2023, 2, 17), schedule, i)
                )
            );
        }

        Assertions.assertEquals(
            BigDecimal.ZERO,
            strategy.getDiscount(LocalDate.of(2023, 2, 17), schedule, 0)
        );
    }
}
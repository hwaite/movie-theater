package com.jpmc.theater.discount;

import com.jpmc.theater.Movie;
import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/** @see TimeSpanDiscountStrategy */
class TimeSpanDiscountStrategyTest {
    private final Movie movie = new Movie("title", Duration.ofHours(1), 10);
    private final List<? extends Showing> showings =
     IntStream.range(8, 13).mapToObj(i -> new Showing(movie, LocalTime.of(i, 0))).toList();
    private final DiscountStrategy strategy =
     new TimeSpanDiscountStrategy("9:00", "11:00", 0.2);

    @Test
    void test() {
        final boolean[] hit = new boolean[] {false, true, true, true, false};
        for (int i = 0; i < hit.length; i++) {
            MatcherAssert.assertThat(
                hit[i] ? BigDecimal.valueOf(2) : BigDecimal.ZERO,
                Matchers.comparesEqualTo(
                    strategy.getDiscount(LocalDate.of(2023, 2, 17), showings, i)
                )
            );
        }
    }
}

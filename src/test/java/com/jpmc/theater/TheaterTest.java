package com.jpmc.theater;

import com.jpmc.theater.discount.DiscountStrategy;
import com.jpmc.theater.schedule.JsonScheduleFormat;
import com.jpmc.theater.schedule.PrettyScheduleFormat;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** @see Theater */
class TheaterTest {
    private final Theater theater = new Theater();

    @Test
    void testTotalFee() {
        final Reservation reservation = theater.reserve(
            new Customer("John Doe"), 2, 4, LocalDate.of(2023, 2, 17)
        );
        Assertions.assertEquals(BigDecimal.valueOf(37.48),  reservation.price());
    }

    /**
     * Verify that greatest discount is selected. We instantiate and shuffle 100 discounts
     * and randomize list. Then make a reservation and ensure that the largest discount
     * is used (as inferred from reservation price).
     */
    @Test
    void testMaxDiscount() {
        final List<DiscountStrategy> discountStrategies = IntStream.range(0, 100)
            .mapToObj(i -> (DiscountStrategy) (date, schedule, idx) -> BigDecimal.valueOf(i))
            .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(discountStrategies);

        MatcherAssert.assertThat(
            BigDecimal.valueOf(25),
            Matchers.comparesEqualTo(
                (
                    new Theater(
                        Stream.of(
                            new Showing(
                                new Movie("title", Duration.ofMinutes(1), 100), "12:00"
                            )
                        ),
                        discountStrategies
                    )
                ).reserve(new Customer("cust"), 1, 25).price()
            )
        );
    }

    /** Discount exceeds ticket price. */
    @Test
    void testNegativePrice() {
        MatcherAssert.assertThat(
            BigDecimal.valueOf(-35),
            Matchers.comparesEqualTo(
                (
                    new Theater(
                        Stream.of(
                            new Showing(
                                new Movie("title", Duration.ofMinutes(1), 5), "12:00"
                            )
                        ),
                        List.of((date, schedule, idx) -> BigDecimal.TEN)
                    )
                ).reserve(new Customer("cust"), 1, 7).price()
            )
        );
    }

    /**
     * No assertions but we can verify that printing pretty schedule doesn't yield exception.
     * @see PrettyScheduleFormat
     */
    @Test
    void testPrettyPrint() {theater.printSchedule(new PrettyScheduleFormat());}

    /**
     * No assertions but we can verify that printing JSON schedule doesn't yield exception.
     * @see JsonScheduleFormat
     */
    @Test
    void testJsonPrint() {theater.printSchedule(new JsonScheduleFormat());}
}

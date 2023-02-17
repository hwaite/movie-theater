package com.jpmc.theater;

import com.jpmc.theater.schedule.PrettyScheduleFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TheaterTest {
    private static final Theater THEATER = new Theater();

    @Test
    void testTotalFee() {
        final Reservation reservation = THEATER.reserve(
            new Customer("John Doe"), 2, 4, LocalDate.of(2023, 2, 17)
        );
        assertEquals(BigDecimal.valueOf(37.48),  reservation.price());
    }

    @Test
    void printMovieSchedule() {THEATER.printSchedule(new PrettyScheduleFormat());}
}

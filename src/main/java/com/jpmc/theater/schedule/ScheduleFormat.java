package com.jpmc.theater.schedule;

import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface ScheduleFormat {
    void print(
        LocalDate date,
        List<? extends Showing> showings,
        Function<Integer, BigDecimal> priceFunction
    );
}

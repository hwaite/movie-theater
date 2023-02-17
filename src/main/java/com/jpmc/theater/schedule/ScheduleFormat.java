package com.jpmc.theater.schedule;

import com.jpmc.theater.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

@FunctionalInterface
public interface ScheduleFormat {
    void print(
        LocalDate date, Schedule schedule, Function<Integer, BigDecimal> priceFunction
    );
}

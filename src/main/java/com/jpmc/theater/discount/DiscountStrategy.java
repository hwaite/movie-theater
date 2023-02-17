package com.jpmc.theater.discount;

import com.jpmc.theater.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;

@FunctionalInterface
public interface DiscountStrategy {
    /** @param idx 0-indexed location of movie in schedule. */
    BigDecimal getDiscount(LocalDate date, Schedule schedule, int idx);
}

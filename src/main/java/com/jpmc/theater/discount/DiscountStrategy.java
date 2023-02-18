package com.jpmc.theater.discount;

import com.jpmc.theater.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Determines how much to discount movie ticket. */
@FunctionalInterface
public interface DiscountStrategy {
    /** @param idx 0-indexed location of movie in schedule. */
    BigDecimal getDiscount(LocalDate date, Schedule schedule, int idx);
}

package com.jpmc.theater.discount;

import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Determines how much to discount movie ticket. */
@FunctionalInterface
public interface DiscountStrategy {
    /**
     * @param showings Sorted list of showings.
     * @param idx 0-indexed location of movie in schedule.
     */
    BigDecimal getDiscount(LocalDate date, List<? extends Showing> showings, int idx);
}

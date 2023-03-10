package com.jpmc.theater.discount;

import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Discount fixed amount ({@linkplain #getDiscount()}) on a particular day of the month
 * ({@linkplain #getDayOfMonth()}).
 */
@Value
@RequiredArgsConstructor
public class DayOfMonthDiscountStrategy implements DiscountStrategy {
	/** 1-indexed day of month (1 - 31). */
	int dayOfMonth;

	/** Fixed value discount. */
	@NonNull
	BigDecimal discount;

	public DayOfMonthDiscountStrategy(int dayOfMonth, double discount) {
		this(dayOfMonth, BigDecimal.valueOf(discount));
	}

	@Override
	public BigDecimal getDiscount(
		LocalDate date, List<? extends Showing> showings, int idx
	) {
		return date.getDayOfMonth() == dayOfMonth ? discount : BigDecimal.ZERO;
	}
}

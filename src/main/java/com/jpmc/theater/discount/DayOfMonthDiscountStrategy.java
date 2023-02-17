package com.jpmc.theater.discount;

import com.jpmc.theater.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

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
	public BigDecimal getDiscount(LocalDate date, Schedule schedule, int idx) {
		return date.getDayOfMonth() == dayOfMonth ? discount : BigDecimal.ZERO;
	}
}

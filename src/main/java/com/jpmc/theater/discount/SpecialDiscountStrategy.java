package com.jpmc.theater.discount;

import com.jpmc.theater.Movie;
import com.jpmc.theater.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class SpecialDiscountStrategy implements DiscountStrategy {
	/** Percentage discount. */
	@NonNull
	BigDecimal discount;

	public SpecialDiscountStrategy(double discount) {this(BigDecimal.valueOf(discount));}

	@Override
	public BigDecimal getDiscount(LocalDate date, Schedule schedule, int idx) {
		return Optional.of(schedule.getShowings().get(idx).movie())
			.filter(Movie::special)
			.map(Movie::price)
			.map(discount::multiply)
			.orElse(BigDecimal.ZERO);
	}
}

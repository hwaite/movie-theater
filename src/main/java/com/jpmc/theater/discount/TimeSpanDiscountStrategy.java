package com.jpmc.theater.discount;

import com.jpmc.theater.Schedule;
import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class TimeSpanDiscountStrategy implements DiscountStrategy {
	@NonNull
	LocalTime start;

	@NonNull
	LocalTime end;

	/** Percentage discount. */
	@NonNull
	BigDecimal discount;

	public TimeSpanDiscountStrategy(String start, String end, double discount) {
		this(
			LocalTime.parse(start, Showing.FORMAT),
			LocalTime.parse(end, Showing.FORMAT),
			BigDecimal.valueOf(discount)
		);
	}

	@Override
	public BigDecimal getDiscount(LocalDate date, Schedule schedule, int idx) {
		final Showing showing = schedule.getShowings().get(idx);
		final LocalTime startTime = showing.startTime();
		return
		 startTime.isBefore(start) || startTime.isAfter(end) ?
		 BigDecimal.ZERO :
		 showing.movie().price().multiply(discount);
	}
}

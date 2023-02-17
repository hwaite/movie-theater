package com.jpmc.theater.discount;

import com.jpmc.theater.Schedule;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ListIndexDiscountStrategy implements DiscountStrategy {
	/** Zero-indexed showing offset within schedule list. */
	int idx;

	/** Fixed dollar discount. */
	@NonNull
	BigDecimal discount;

	public ListIndexDiscountStrategy(int idx, double discount) {
		this(idx, BigDecimal.valueOf(discount));
	}

	@Override
	public BigDecimal getDiscount(LocalDate date, Schedule schedule, int index) {
		return index == this.idx ? discount : BigDecimal.ZERO;
	}
}

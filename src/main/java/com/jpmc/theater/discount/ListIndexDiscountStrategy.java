package com.jpmc.theater.discount;

import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Apply fixed discount {@linkplain #getDiscount()} to nth movie of the day
 * (where n = {@linkplain #getIdx()}).
 */
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
	public BigDecimal getDiscount(
		LocalDate date, List<? extends Showing> showings, int index
	) {
		return index == this.idx ? discount : BigDecimal.ZERO;
	}
}

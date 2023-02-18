package com.jpmc.theater.discount;

import com.jpmc.theater.Movie;
import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Apply percentage discount ({@linkplain #getDiscount()}) if movie is special
 * ({@linkplain Movie#special()} is true).
 */
@Value
@RequiredArgsConstructor
public class SpecialDiscountStrategy implements DiscountStrategy {
	/** Percentage discount. */
	@NonNull
	BigDecimal discount;

	public SpecialDiscountStrategy(double discount) {this(BigDecimal.valueOf(discount));}

	@Override
	public BigDecimal getDiscount(
		LocalDate date, List<? extends Showing> showings, int idx
	) {
		return Optional.of(showings.get(idx).movie())
			.filter(Movie::special)
			.map(Movie::price)
			.map(discount::multiply)
			.orElse(BigDecimal.ZERO);
	}
}

package com.jpmc.theater;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Schedule {
	/** Showings, sorted by {@linkplain Showing#startTime()}. */
	@NonNull
	List<? extends Showing> showings;

	public Schedule(Iterable<? extends Showing> showings) {
		this(StreamSupport.stream(showings.spliterator(), false));
	}

	public Schedule(Stream<? extends Showing> showings) {
		this.showings = showings.sorted(Comparator.comparing(Showing::startTime)).toList();
	}
}

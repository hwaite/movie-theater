package com.jpmc.theater;

import com.jpmc.theater.discount.DayOfMonthDiscountStrategy;
import com.jpmc.theater.discount.DiscountStrategy;
import com.jpmc.theater.discount.ListIndexDiscountStrategy;
import com.jpmc.theater.discount.SpecialDiscountStrategy;
import com.jpmc.theater.discount.TimeSpanDiscountStrategy;
import com.jpmc.theater.schedule.JsonScheduleFormat;
import com.jpmc.theater.schedule.PrettyScheduleFormat;
import com.jpmc.theater.schedule.ScheduleFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Theater {
    /** Sample movie used in main method and unit tests. */
    static final Movie SPIDER_MAN =
     new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, true);
    /** Sample movie used in main method and unit tests. */
    static final Movie TURNING_RED =
     new Movie("Turning Red", Duration.ofMinutes(85), 11, false);
    /** Sample movie used in main method and unit tests. */
    static final Movie THE_BATMAN =
     new Movie("The Batman", Duration.ofMinutes(95), 9, false);

    /** Sample showings used in main method and unit tests. */
    public static final Iterable<Showing> DEFAULT_SHOWINGS = List.of(
        new Showing(TURNING_RED, "9:00"),
        new Showing(SPIDER_MAN, "11:00"),
        new Showing(THE_BATMAN, "12:50"),
        new Showing(TURNING_RED, "14:30"),
        new Showing(SPIDER_MAN, "16:00"),
        new Showing(THE_BATMAN, "17:50"),
        new Showing(TURNING_RED, "19:30"),
        new Showing(SPIDER_MAN, "21:10"),
        new Showing(THE_BATMAN, "23:00")
    );

    private static final Iterable<DiscountStrategy> DEFAULT_DISCOUNT_STRATEGIES = List.of(
        // $1 on 7th of the month
        new DayOfMonthDiscountStrategy(7, 1),

        // $3 for first movie of the day
        new ListIndexDiscountStrategy(0, 3),

        // $2 for second movie of the day
        new ListIndexDiscountStrategy(1, 2),

        // 20% for special movie
        new SpecialDiscountStrategy(0.2),

        // 25% 11:00-16:00
        new TimeSpanDiscountStrategy("11:00", "16:00", 0.25)
    );


    @NonNull
    private final Schedule schedule;

    @NonNull
    private final Iterable<? extends DiscountStrategy> discountStrategies;

    public Theater() {this(DEFAULT_SHOWINGS);}

    public Theater(Iterable<? extends Showing> showings) {
        this(showings, DEFAULT_DISCOUNT_STRATEGIES);
    }

    public Theater(
        Iterable<? extends Showing> showings,
        Iterable<? extends DiscountStrategy> discountStrategies
    ) {
        this(
            new Schedule(StreamSupport.stream(showings.spliterator(), false)),
            discountStrategies
        );
    }

    public Reservation reserve(Customer customer, int sequence, int quantity) {
        return reserve(customer, sequence, quantity, LocalDate.now());
    }

    /** @param sequence 1-indexed sequence value. */
    public Reservation reserve(
        Customer customer, int sequence, int quantity, LocalDate date
    ) {
        final int idx = sequence - 1;
        return new Reservation(
            date,
            customer,
            schedule.getShowings().get(idx),
            quantity,
            getPrice(date, idx).multiply(BigDecimal.valueOf(quantity))
        );
    }

    public void printSchedule(ScheduleFormat format) {
        final LocalDate today = LocalDate.now();
        format.print(today, schedule, idx -> getPrice(today, idx));
    }

    /** @param idx 0-indexed show offset. */
    private BigDecimal getPrice(LocalDate date, int idx) {
        return schedule.getShowings().get(idx).movie().price().subtract(
            getDiscount(date, idx)
        );
    }

    /** @param idx 0-indexed show selection. */
    private BigDecimal getDiscount(LocalDate date, int idx) {
        return StreamSupport.stream(discountStrategies.spliterator(), false)
            .map(strat -> strat.getDiscount(date, schedule, idx))
            .max(Comparator.naturalOrder())
            .map(discount -> discount.setScale(2, RoundingMode.CEILING))
            .orElse(BigDecimal.ZERO);
    }

    public static void main(String[] args) {
        final Theater theater = new Theater();
        Stream.of(new PrettyScheduleFormat(), new JsonScheduleFormat())
            .forEach(theater::printSchedule);
    }
}

package com.jpmc.theater.schedule;

import com.jpmc.theater.Schedule;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.function.Function;

public class PrettyScheduleFormat implements ScheduleFormat {
    @Override
    public void print(
        LocalDate date, Schedule schedule, Function<Integer, BigDecimal> priceFunction
    ) {
        System.out.println(date);
        System.out.println("===================================================");

        // keep track of movie index
        final int[] idxRef = new int[1];

        schedule.getShowings().forEach(
            showing -> {
                System.out.printf(
                    "%d: %s %s %s $%s%n",
                    idxRef[0] + 1,
                    showing.startTime(),
                    showing.movie().title(),
                    humanReadableFormat(showing.movie().runningTime()),
                    priceFunction.apply(idxRef[0])
                );
                idxRef[0]++;
            }
        );
        System.out.println("===================================================");
    }

    private static String humanReadableFormat(Duration duration) {
        final long hour = duration.toHours();
        final long remainingMin = duration.toMinutesPart();

        return String.format(
            "(%s hour%s %s minute%s)",
            hour,
            handlePlural(hour),
            remainingMin,
            handlePlural(remainingMin)
        );
    }

    /** (s) postfix should be added to handle plural correctly. */
    private static String handlePlural(long value) {return value == 1 ? "" : "s";}
}

package com.jpmc.theater.schedule;

import com.google.gson.GsonBuilder;
import com.jpmc.theater.Movie;
import com.jpmc.theater.Schedule;
import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class JsonScheduleFormat implements ScheduleFormat {
    @Override
    public void print(
        LocalDate date, Schedule schedule, Function<Integer, BigDecimal> priceFunction
    ) {
        System.out.println(
            (new GsonBuilder()).setPrettyPrinting().create().toJson(
                IntStream.range(0, schedule.getShowings().size())
                    .<Map<String,Object>>mapToObj(
                        idx -> {
                            final Showing showing = schedule.getShowings().get(idx);
                            final Movie movie = showing.movie();
                            return Map.of(
                                "start", showing.startTime().toString(),
                                "title", movie.title(),
                                "duration", movie.runningTime().toString(),
                                "price", priceFunction.apply(idx)
                            );
                        }
                    )
                    .toList()
            )
        );
    }
}

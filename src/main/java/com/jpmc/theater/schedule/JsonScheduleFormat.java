package com.jpmc.theater.schedule;

import com.google.gson.GsonBuilder;
import com.jpmc.theater.Movie;
import com.jpmc.theater.Showing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class JsonScheduleFormat implements ScheduleFormat {
    @Override
    public void print(
        LocalDate date,
        List<? extends Showing> showings,
        Function<Integer, BigDecimal> priceFunction
    ) {
        System.out.println(
            (new GsonBuilder()).setPrettyPrinting().create().toJson(
                IntStream.range(0, showings.size())
                    .<Map<String,Object>>mapToObj(
                        idx -> {
                            final Showing showing = showings.get(idx);
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

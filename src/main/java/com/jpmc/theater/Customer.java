package com.jpmc.theater;

import java.util.Objects;

public record Customer(String name) {
    public Customer {Objects.requireNonNull(name);}
}

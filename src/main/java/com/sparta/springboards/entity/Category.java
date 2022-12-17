package com.sparta.springboards.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Category {
    Spring("Spring"),
    Java("Java"),
    TIL("TIL");


    private final String category;

    public static Category valueOfCategory(String category){
        return Arrays.stream(values())
                .filter(value -> value.category.equals(category))
                .findAny()
                .orElse(null);
    }
}

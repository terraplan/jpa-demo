package com.terraplan.demo.jpa;

import lombok.Data;

@Data
public class DayEntryDto {
    private String id;
    private String name;

    public DayEntryDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

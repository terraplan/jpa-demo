package com.terraplan.demo.jpa;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayEntry {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayEntry dayEntry = (DayEntry) o;

        return Objects.equals(id, dayEntry.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

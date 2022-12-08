package com.terraplan.demo.versions;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    private UUID studentId;

    private String name;

    // EAGER is the default here and would be ok. Just trying out LAZY ...
    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    private Long schoolId;

    private Instant validFrom;
    private Instant validUntil;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

package com.terraplan.demo.versions;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "schools")
public class School {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}

package com.terraplan.demo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DayEntryRepository extends JpaRepository<DayEntry, Long> {
}

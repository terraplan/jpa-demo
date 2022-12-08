package com.terraplan.demo.versions;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long>  {

    @EntityGraph(attributePaths = {"students"})
    Optional<Tour> findOneWithStudentsById(Long id);
}

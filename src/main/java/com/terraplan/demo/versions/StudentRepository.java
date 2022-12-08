package com.terraplan.demo.versions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, Long>  {

    List<Student> findByStudentIdAndValidFromBeforeAndValidUntilAfter(UUID studentId, Instant validFrom, Instant validUntil);

    List<Student> findBySchoolIdAndValidFromBeforeAndValidUntilAfter(Long studentId, Instant validFrom, Instant validUntil);
}

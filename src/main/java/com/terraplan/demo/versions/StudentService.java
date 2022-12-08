package com.terraplan.demo.versions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class StudentService {

    public static final Instant MIN_VALID_FROM = Instant.EPOCH;
    public static final Instant MAX_VALID_UNTIL = Instant.parse("3000-01-01T00:00:00Z");
    private final StudentRepository studentRepository;

    public Student createStudent(Student student) {
        student.setStudentId(UUID.randomUUID());
        student.setValidFrom(MIN_VALID_FROM);
        student.setValidUntil(MAX_VALID_UNTIL);
        return studentRepository.save(student);
    }

    // This only works into the "future"
    public Student updateStudent(Student student, Instant validFrom) {
        List<Student> lastStudents = studentRepository.findByStudentIdAndValidFromBeforeAndValidUntilAfter(student.getStudentId(), validFrom, validFrom);
        // must be one!
        if (lastStudents.size() != 1) {
            throw new RuntimeException("More or less than one entry found for instant " + validFrom);
        }

        Student lastStudent = lastStudents.get(0);
        lastStudent.setValidUntil(validFrom);
        studentRepository.save(lastStudent);

        // make sure that a new entry is created and not an existing one updated!
        student.setId(null);

        student.setValidFrom(validFrom);
        student.setValidUntil(MAX_VALID_UNTIL);

        return studentRepository.save(student);
    }

    public Student loadStudent(UUID uuid, Instant validAt) {
        List<Student> lastStudents = studentRepository.findByStudentIdAndValidFromBeforeAndValidUntilAfter(uuid, validAt, validAt);
        // must be one!
        if (lastStudents.size() != 1) {
            throw new RuntimeException("More or less than one entry found for instant " + validAt);
        }

        return lastStudents.get(0);
    }

    public List<Student> getStudentsOfSchool(Long schoolId, Instant validAt) {
        return studentRepository.findBySchoolIdAndValidFromBeforeAndValidUntilAfter(schoolId, validAt, validAt);
    }
}

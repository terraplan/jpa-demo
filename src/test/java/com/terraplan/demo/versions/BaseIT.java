package com.terraplan.demo.versions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseIT {

    @Container
    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDB::getUsername);
        registry.add("spring.datasource.password", postgresDB::getPassword);

    }

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TourService tourService;

    @Test
    void testStudent() {

        School school = new School();
        school.setName("Schulname");
        School persistedSchool = schoolRepository.save(school);

        Address address1 = new Address();
        address1.setName("Place One");
        address1 = addressRepository.save(address1);

        Address address2 = new Address();
        address2.setName("Place Two");
        address2 = addressRepository.save(address2);

        Student testStudent = new Student();
        testStudent.setName("Niklas");
        testStudent.setSchoolId(persistedSchool.getId());
        testStudent.setAddress(address1);

        Student createdStudent = studentService.createStudent(testStudent);
        Student loadedStudent = studentService.loadStudent(createdStudent.getStudentId(), Instant.now().plusSeconds(3600));

        Tour tour = tourService.createNewTour();
        tour.addStudent(loadedStudent);
        tourService.updateStudents(tour);

        Student updatedStudent = new Student();
        updatedStudent.setStudentId(loadedStudent.getStudentId());
        updatedStudent.setName("neuer Name");
        updatedStudent.setSchoolId(persistedSchool.getId());
        updatedStudent.setAddress(address2);
        studentService.updateStudent(updatedStudent, Instant.now());

        assertThat(studentRepository.findAll(), hasSize(2));

        Student loadedStudent1 = studentService.loadStudent(createdStudent.getStudentId(), Instant.now().plusSeconds(3600));
        loadedStudent1.getName();
        Student loadedStudent2 = studentService.loadStudent(createdStudent.getStudentId(), Instant.now().minusSeconds(3600));
        loadedStudent2.getName();

        List<Student> studentsOfSchool = studentService.getStudentsOfSchool(persistedSchool.getId(), Instant.now());

        assertThat(studentsOfSchool, hasSize(1));

        loadedStudent = studentService.loadStudent(createdStudent.getStudentId(), Instant.now());
        loadedStudent.setSchoolId(null);
        studentService.updateStudent(loadedStudent, Instant.now().plusSeconds(60));

        assertThat(studentRepository.findAll(), hasSize(3));

        studentsOfSchool = studentService.getStudentsOfSchool(persistedSchool.getId(), Instant.now().plusSeconds(300));

        assertThat(studentsOfSchool, hasSize(0));

        tour = tourService.loadTour(tour.getId());
        assertThat(tour.getStudents(), hasSize(1));

        Student testStudent2 = new Student();
        testStudent2.setName("Silas");
        testStudent2.setSchoolId(persistedSchool.getId());
        testStudent2.setAddress(address2);
        Student createdStudent2 = studentService.createStudent(testStudent2);
        tour.addStudent(createdStudent2);
        tourService.updateStudents(tour);

        tour = tourService.loadTour(tour.getId());
        assertThat(tour.getStudents(), hasSize(2));
        assertThat(tour.getStudents().get(0).getName(), is("Niklas"));
    }
}

package rest.univer.service;

import rest.univer.domain.Student;

import java.util.Optional;

public interface StudentService {
    Student saveStudent(Student student);

    void deleteStudentById(Long id);

    Optional<Student> findStudentById(Long id);

    Iterable<Student> findAllStudents();
}

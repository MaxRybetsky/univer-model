package rest.univer.service;

import rest.univer.domain.Student;
import rest.univer.exceptions.NoSuchPersonException;

public interface StudentService {
    Student saveStudent(Student student);

    void deleteStudentById(Long id) throws NoSuchPersonException;

    Student findStudentById(Long id) throws NoSuchPersonException;

    Iterable<Student> findAllStudents();
}

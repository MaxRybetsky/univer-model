package rest.univer.service;

import rest.univer.domain.Teacher;

import java.util.Optional;

public interface TeacherService {
    Teacher saveTeacher(Teacher teacher);

    void deleteTeacherById(Long id);

    Optional<Teacher> findTeacherById(Long id);

    Iterable<Teacher> findAllTeachers();
}

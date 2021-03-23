package rest.univer.service;

import rest.univer.domain.Teacher;
import rest.univer.exceptions.NoSuchPersonException;

public interface TeacherService {
    Teacher saveTeacher(Teacher teacher);

    void deleteTeacherById(Long id) throws NoSuchPersonException;

    Teacher findTeacherById(Long id) throws NoSuchPersonException;

    Iterable<Teacher> findAllTeachers();
}

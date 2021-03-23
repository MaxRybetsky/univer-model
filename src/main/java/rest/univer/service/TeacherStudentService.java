package rest.univer.service;

import rest.univer.domain.Student;
import rest.univer.domain.Teacher;

public interface TeacherStudentService {
    boolean addStudentToTeacher(Long studentId, Long teacherId);

    boolean deleteStudentFromTeacher(Long studentId, Long teacherId);

    Iterable<Student> getStudentsFromTeacher(Long teacherId);

    Iterable<Teacher> getTeachersFromStudent(Long studentId);
}

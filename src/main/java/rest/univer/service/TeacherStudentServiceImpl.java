package rest.univer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.univer.domain.Student;
import rest.univer.domain.Teacher;
import rest.univer.domain.TeacherStudent;
import rest.univer.repository.TeacherStudentRepository;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherStudentServiceImpl implements TeacherStudentService {
    private final StudentService studentService;
    private final TeacherService teacherService;
    @Autowired
    private TeacherStudentRepository repo;

    @Autowired
    public TeacherStudentServiceImpl(StudentService studentService,
                                     TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public boolean addStudentToTeacher(Long studentId, Long teacherId) {
        return false;
    }

    @Override
    public boolean deleteStudentFromTeacher(Long studentId, Long teacherId) {
        Student student = studentService.findStudentById(studentId);
        Teacher teacher = teacherService.findTeacherById(teacherId);
        TeacherStudent teacherStudent = new TeacherStudent(teacher, student);
        if (!teacher.getStudents().contains(teacherStudent)) {
            return false;
        }

        repo.save(teacherStudent);
        return true;
    }

    @Override
    public Iterable<Student> getStudentsFromTeacher(Long teacherId) {
        Teacher teacher = teacherService.findTeacherById(teacherId);
        return teacher.getStudents().stream()
                .map(TeacherStudent::getStudent)
                .sorted(Comparator.comparingLong(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Teacher> getTeachersFromStudent(Long studentId) {
        return null;
    }
}

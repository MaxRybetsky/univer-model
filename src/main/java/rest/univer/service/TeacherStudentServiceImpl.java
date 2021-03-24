package rest.univer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.univer.domain.Student;
import rest.univer.domain.Teacher;
import rest.univer.domain.TeacherStudent;
import rest.univer.repository.TeacherStudentRepository;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class TeacherStudentServiceImpl implements TeacherStudentService {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final TeacherStudentRepository teacherStudentRepository;

    @Autowired
    public TeacherStudentServiceImpl(StudentService studentService,
                                     TeacherService teacherService,
                                     TeacherStudentRepository teacherStudentRepository) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.teacherStudentRepository = teacherStudentRepository;
    }

    @Override
    @Transactional
    public boolean addStudentToTeacher(Long studentId, Long teacherId) {
        return false;
    }

    @Override
    @Transactional
    public boolean deleteStudentFromTeacher(Long studentId, Long teacherId) {
        Student student = studentService.findStudentById(studentId);
        Teacher teacher = teacherService.findTeacherById(teacherId);
        TeacherStudent teacherStudent = new TeacherStudent(teacher, student);
        if (!teacher.getStudents().contains(teacherStudent)) {
            return false;
        }
        teacherStudentRepository.save(teacherStudent);
        return true;
    }

    @Override
    @Transactional
    public Iterable<Student> getStudentsFromTeacher(Long teacherId) {
        Teacher teacher = teacherService.findTeacherById(teacherId);
        return teacher.getStudents().stream()
                .map(TeacherStudent::getStudent)
                .sorted(Comparator.comparingLong(Student::getId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Iterable<Teacher> getTeachersFromStudent(Long studentId) {
        return null;
    }
}

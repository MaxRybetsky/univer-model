package rest.univer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import rest.univer.domain.Student;
import rest.univer.domain.Teacher;
import rest.univer.domain.TeacherStudent;
import rest.univer.repository.TeacherStudentRepository;
import rest.univer.util.CompareHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@RunWith(SpringRunner.class)
public class TeacherStudentServiceImplTest {
    @Mock
    private TeacherStudentRepository teacherStudentRepository;

    @Mock
    private TeacherServiceImpl teacherService;

    @Mock
    private StudentServiceImpl studentService;

    @InjectMocks
    private TeacherStudentServiceImpl teacherStudentService;

    private Student student;
    private Teacher teacher;
    private TeacherStudent teacherStudent;

    @Before
    public void createEntities() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("Max");
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Ivan");
        teacherStudent = new TeacherStudent(teacher, student);
    }

    @Test
    public void whenSuccessfullyAddStudentToTeacher() {
        when(teacherService.findTeacherById(ArgumentMatchers.anyLong()))
                .thenReturn(teacher);
        when(studentService.findStudentById(ArgumentMatchers.anyLong()))
                .thenReturn(student);
        boolean result = teacherStudentService.addStudentToTeacher(student.getId(), teacher.getId());
        assertThat(result).isTrue();
        verify(teacherStudentRepository).save(teacherStudent);
    }

    @Test
    public void whenCanNotAddStudentToTeacherBecauseHeWasAddedEarlier() {
        teacher.getStudents().add(teacherStudent);
        when(teacherService.findTeacherById(ArgumentMatchers.anyLong()))
                .thenReturn(teacher);
        when(studentService.findStudentById(ArgumentMatchers.anyLong()))
                .thenReturn(student);
        boolean result = teacherStudentService.addStudentToTeacher(student.getId(), teacher.getId());
        assertThat(result).isFalse();
    }

    @Test
    public void whenSuccessfullyDeleteStudentFromTeacher() {
        teacher.getStudents().add(teacherStudent);
        when(teacherService.findTeacherById(ArgumentMatchers.anyLong()))
                .thenReturn(teacher);
        when(studentService.findStudentById(ArgumentMatchers.anyLong()))
                .thenReturn(student);
        boolean result = teacherStudentService.deleteStudentFromTeacher(student.getId(), teacher.getId());
        assertThat(result).isTrue();
        verify(teacherStudentRepository).deleteByTeacherIdAndStudentId(teacher.getId(), student.getId());
    }

    @Test
    public void whenCanNotDeleteStudentFromTeacherBecauseHeWasDeletedEarlier() {
        when(teacherService.findTeacherById(ArgumentMatchers.anyLong()))
                .thenReturn(teacher);
        when(studentService.findStudentById(ArgumentMatchers.anyLong()))
                .thenReturn(student);
        boolean result = teacherStudentService.deleteStudentFromTeacher(student.getId(), teacher.getId());
        assertThat(result).isFalse();
    }

    @Test
    public void whenGetAllStudentsFromTeacher() {
        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Jenkins");
        TeacherStudent teacherStudent2 = new TeacherStudent(teacher, student2);
        Student student3 = new Student();
        student3.setId(3L);
        student3.setFirstName("Steve");
        TeacherStudent teacherStudent3 = new TeacherStudent(teacher, student3);
        teacher.getStudents().add(teacherStudent);
        teacher.getStudents().add(teacherStudent2);
        teacher.getStudents().add(teacherStudent3);
        when(teacherService.findTeacherById(ArgumentMatchers.anyLong()))
                .thenReturn(teacher);
        when(studentService.findStudentById(ArgumentMatchers.anyLong()))
                .thenReturn(student);
        Iterable<Student> students = teacherStudentService.getStudentsFromTeacher(teacher.getId());
        boolean result = CompareHelper.compareStudentsIterableWithSet(students, teacher.getStudents());
        assertThat(result).isTrue();
    }

    @Test
    public void whenGetAllTeachersFromStudent() {
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Jenkins");
        TeacherStudent teacherStudent2 = new TeacherStudent(teacher2, student);
        Teacher teacher3 = new Teacher();
        teacher3.setId(3L);
        teacher3.setFirstName("Steve");
        TeacherStudent teacherStudent3 = new TeacherStudent(teacher3, student);
        student.getTeachers().add(teacherStudent);
        student.getTeachers().add(teacherStudent2);
        student.getTeachers().add(teacherStudent3);
        when(teacherService.findTeacherById(ArgumentMatchers.anyLong()))
                .thenReturn(teacher);
        when(studentService.findStudentById(ArgumentMatchers.anyLong()))
                .thenReturn(student);
        Iterable<Teacher> teachers = teacherStudentService.getTeachersFromStudent(student.getId());
        boolean result = CompareHelper.compareTeachersIterableWithSet(teachers, student.getTeachers());
        assertThat(result).isTrue();
    }
}
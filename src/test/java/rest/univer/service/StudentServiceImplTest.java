package rest.univer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import rest.univer.domain.Student;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@RunWith(SpringRunner.class)
public class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void whenSuccessfullyAddStudent() {
        Student student = new Student();
        student.setFirstName("Max");
        when(studentRepository.save(ArgumentMatchers.any(Student.class)))
                .thenReturn(student);
        Student savedStudent = studentService.saveStudent(student);
        assertThat(savedStudent.getFirstName())
                .isSameAs(student.getFirstName());
        verify(studentRepository).save(student);
    }

    @Test
    public void whenSuccessfullyUpdateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Max");
        when(studentRepository.save(ArgumentMatchers.any(Student.class)))
                .thenReturn(student);
        when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(student));
        Student updatedStudent = studentService.saveStudent(student);
        assertThat(updatedStudent.getId())
                .isSameAs(student.getId());
        assertThat(updatedStudent.getFirstName())
                .isSameAs(student.getFirstName());
        verify(studentRepository).findById(student.getId());
        verify(studentRepository).save(student);
    }

    @Test(expected = NoSuchPersonException.class)
    public void whenNonSuccessfullyUpdateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Max");
        when(studentRepository.save(ArgumentMatchers.any(Student.class)))
                .thenReturn(student);
        when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());
        Student updatedStudent = studentService.saveStudent(student);
    }

    @Test
    public void whenSuccessfullyDeleteStudent() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(student));
        studentService.deleteStudentById(student.getId());
        verify(studentRepository).findById(student.getId());
        verify(studentRepository).deleteById(student.getId());
    }

    @Test(expected = NoSuchPersonException.class)
    public void whenNonSuccessfullyDeleteStudent() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());
        studentService.deleteStudentById(student.getId());
    }

    @Test
    public void whenSuccessfullyFindStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Max");
        when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(student));
        Student foundStudent = studentService.findStudentById(student.getId());
        assertThat(foundStudent.getId())
                .isSameAs(student.getId());
        assertThat(foundStudent.getFirstName())
                .isSameAs(student.getFirstName());
        verify(studentRepository).findById(student.getId());
    }

    @Test(expected = NoSuchPersonException.class)
    public void whenNonSuccessfullyFindStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Max");
        when(studentRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());
        Student foundStudent = studentService.findStudentById(student.getId());
    }

    @Test
    public void whenSuccessfullyFindAllStudents() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("Max");
        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Nikolay");
        Student student3 = new Student();
        student3.setId(3L);
        student3.setFirstName("Olga");
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        when(studentRepository.findAll())
                .thenReturn(studentList);
        List<Student> foundStudents = (List<Student>)studentService.findAllStudents();
        // Check 1st Student
        assertThat(foundStudents.get(0).getId())
                .isSameAs(student1.getId());
        assertThat(foundStudents.get(0).getFirstName())
                .isSameAs(student1.getFirstName());
        // Check 2nd Student
        assertThat(foundStudents.get(1).getId())
                .isSameAs(student2.getId());
        assertThat(foundStudents.get(1).getFirstName())
                .isSameAs(student2.getFirstName());
        // Check 3rd Student
        assertThat(foundStudents.get(2).getId())
                .isSameAs(student3.getId());
        assertThat(foundStudents.get(2).getFirstName())
                .isSameAs(student3.getFirstName());
        verify(studentRepository).findAll();
    }
}
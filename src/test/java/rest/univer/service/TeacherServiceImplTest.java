package rest.univer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import rest.univer.domain.Teacher;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@RunWith(SpringRunner.class)
public class TeacherServiceImplTest {
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    public void whenSuccessfullyAddTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Maxim");
        when(teacherRepository.save(ArgumentMatchers.any(Teacher.class)))
                .thenReturn(teacher);
        Teacher savedTeacher = teacherService.saveTeacher(teacher);
        assertThat(savedTeacher.getFirstName())
                .isSameAs(teacher.getFirstName());
        verify(teacherRepository).save(teacher);
    }

    @Test
    public void whenSuccessfullyUpdateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Maxim");
        when(teacherRepository.save(ArgumentMatchers.any(Teacher.class)))
                .thenReturn(teacher);
        when(teacherRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(teacher));
        Teacher updatedTeacher = teacherService.saveTeacher(teacher);
        assertThat(updatedTeacher.getId())
                .isSameAs(teacher.getId());
        assertThat(updatedTeacher.getFirstName())
                .isSameAs(teacher.getFirstName());
        verify(teacherRepository).findById(teacher.getId());
        verify(teacherRepository).save(teacher);
    }

    @Test(expected = NoSuchPersonException.class)
    public void whenNonSuccessfullyUpdateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Maxim");
        when(teacherRepository.save(ArgumentMatchers.any(Teacher.class)))
                .thenReturn(teacher);
        when(teacherRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());
        Teacher updatedTeacher = teacherService.saveTeacher(teacher);
    }

    @Test
    public void whenSuccessfullyDeleteTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(teacher));
        teacherService.deleteTeacherById(teacher.getId());
        verify(teacherRepository).findById(teacher.getId());
        verify(teacherRepository).deleteById(teacher.getId());
    }

    @Test(expected = NoSuchPersonException.class)
    public void whenNonSuccessfullyDeleteTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());
        teacherService.deleteTeacherById(teacher.getId());
    }

    @Test
    public void whenSuccessfullyFindTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Max");
        when(teacherRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(teacher));
        Teacher foundTeacher = teacherService.findTeacherById(teacher.getId());
        assertThat(foundTeacher.getId())
                .isSameAs(teacher.getId());
        assertThat(foundTeacher.getFirstName())
                .isSameAs(teacher.getFirstName());
        verify(teacherRepository).findById(teacher.getId());
    }

    @Test(expected = NoSuchPersonException.class)
    public void whenNonSuccessfullyFindTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Max");
        when(teacherRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());
        Teacher foundTeacher = teacherService.findTeacherById(teacher.getId());
    }

    @Test
    public void whenSuccessfullyFindAllTeachers() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("Max");
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Nikolay");
        Teacher teacher3 = new Teacher();
        teacher3.setId(3L);
        teacher3.setFirstName("Olga");
        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher1);
        teacherList.add(teacher2);
        teacherList.add(teacher3);
        when(teacherRepository.findAll())
                .thenReturn(teacherList);
        List<Teacher> foundTeachers = (List<Teacher>) teacherService.findAllTeachers();
        // Check 1st Teacher
        assertThat(foundTeachers.get(0).getId())
                .isSameAs(teacher1.getId());
        assertThat(foundTeachers.get(0).getFirstName())
                .isSameAs(teacher1.getFirstName());
        // Check 2nd Teacher
        assertThat(foundTeachers.get(1).getId())
                .isSameAs(teacher2.getId());
        assertThat(foundTeachers.get(1).getFirstName())
                .isSameAs(teacher2.getFirstName());
        // Check 3rd Teacher
        assertThat(foundTeachers.get(2).getId())
                .isSameAs(teacher3.getId());
        assertThat(foundTeachers.get(2).getFirstName())
                .isSameAs(teacher3.getFirstName());
        verify(teacherRepository).findAll();
    }
}
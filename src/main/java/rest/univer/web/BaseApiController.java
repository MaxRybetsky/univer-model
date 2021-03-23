package rest.univer.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import rest.univer.domain.Student;
import rest.univer.domain.Teacher;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.exceptions.PersonIncorrectData;
import rest.univer.service.StudentService;
import rest.univer.service.TeacherService;

@Getter
@Setter
public abstract class BaseApiController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    public BaseApiController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @ExceptionHandler
    public ResponseEntity<PersonIncorrectData> handleIncorrectId(
            NoSuchPersonException exception
    ) {
        PersonIncorrectData data = new PersonIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PersonIncorrectData> handleIncorrectInput(
            MethodArgumentTypeMismatchException exception
    ) {
        PersonIncorrectData data = new PersonIncorrectData(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    protected Student getStudentByIdOrThrowAnException(Long id) {
        Student student = studentService.findStudentById(id).orElse(null);
        if (student == null) {
            throw new NoSuchPersonException(
                    String.format("There is no student with id=%d", id)
            );
        }
        return student;
    }

    protected Teacher getTeacherByIdOrThrowAnException(Long id) {
        Teacher teacher = teacherService.findTeacherById(id).orElse(null);
        if (teacher == null) {
            throw new NoSuchPersonException(
                    String.format("There is no teacher with id=%d", id)
            );
        }
        return teacher;
    }
}

package rest.univer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import rest.univer.domain.Student;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.exceptions.PersonIncorrectData;
import rest.univer.service.StudentService;

@RestController
@RequestMapping("/${application.api.path}/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Iterable<Student> getAllStudents() {
        return studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return getStudentByIdOrThrowAnException(id);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return student;
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return student;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        getStudentByIdOrThrowAnException(id);
        studentService.deleteStudentById(id);
        return "Student with " + id + " was deleted!";
    }

    private Student getStudentByIdOrThrowAnException(Long id) {
        Student student = studentService.findStudentById(id).orElse(null);
        if (student == null) {
            throw new NoSuchPersonException(
                    String.format("There is no student with id=%d", id)
            );
        }
        return student;
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
}

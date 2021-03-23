package rest.univer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.univer.domain.Student;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.service.StudentService;
import rest.univer.service.TeacherService;

@RestController
@RequestMapping("/${application.api.path}/students")
public class StudentController extends BaseApiController {

    @Autowired
    public StudentController(StudentService studentService, TeacherService teacherService) {
        super(studentService, teacherService);
    }

    @GetMapping
    public Iterable<Student> getAllStudents() {
        return getStudentService().findAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return getStudentByIdOrThrowAnException(id);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return getStudentService().saveStudent(student);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return getStudentService().saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        getStudentByIdOrThrowAnException(id);
        getStudentService().deleteStudentById(id);
        return "Student with " + id + " was deleted!";
    }
}

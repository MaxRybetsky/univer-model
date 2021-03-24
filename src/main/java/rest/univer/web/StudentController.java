package rest.univer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.univer.domain.Student;
import rest.univer.domain.Teacher;
import rest.univer.service.StudentService;
import rest.univer.service.TeacherStudentService;

@RestController
@RequestMapping("/${application.api.path}/students")
public class StudentController extends BaseApiController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService,
                             TeacherStudentService teacherStudentService) {
        super(teacherStudentService);
        this.studentService = studentService;
    }

    @GetMapping
    public Iterable<Student> getAllStudents() {
        return studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "Student with " + id + " was deleted!";
    }

    @GetMapping("/{studentId}/teachers")
    public Iterable<Teacher> getStudents(@PathVariable Long studentId) {
        return getTeacherStudentService().getTeachersFromStudent(studentId);
    }
}

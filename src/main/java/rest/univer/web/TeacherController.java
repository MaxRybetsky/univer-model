package rest.univer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.univer.domain.Student;
import rest.univer.domain.Teacher;
import rest.univer.domain.TeacherStudent;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.service.StudentService;
import rest.univer.service.TeacherService;
import rest.univer.service.TeacherStudentService;

import java.util.Set;

@RestController
@RequestMapping("/${application.api.path}/teachers")
public class TeacherController extends BaseApiController {
    private final TeacherService teacherService;
    @Autowired
    private TeacherStudentService teacherStudentService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public Iterable<Teacher> getAllTeachers() {
        return teacherService.findAllTeachers();
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Long id) {
        return teacherService.findTeacherById(id);
    }

    @PostMapping
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @PutMapping
    public Teacher updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return "Teacher with " + id + " was deleted!";
    }

    @PostMapping("/{teacherId}/students/{studentId}")
    public String addStudentToTeacher(
            @PathVariable(name = "teacherId") Long teacherId,
            @PathVariable(name = "studentId") Long studentId
    ) {
        boolean success = teacherStudentService.addStudentToTeacher(studentId, teacherId);
        String resultMessage;
        if (!success) {
            resultMessage = "Student with id = %d has been already added to teacher with id = %d." +
                    " You can remove this student from or try to add another student to this teacher.";
        } else {
            resultMessage = "Student with id = %d successfully added to teacher with id = %d.";
        }
        return String.format(
                resultMessage, studentId, teacherId
        );
    }

    @GetMapping("/{teacherId}/students/")
    public Iterable<Student> getStudents(@PathVariable Long teacherId) {
        return teacherStudentService.getStudentsFromTeacher(teacherId);
    }
}

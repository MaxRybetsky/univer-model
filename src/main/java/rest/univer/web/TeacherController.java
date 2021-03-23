package rest.univer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.univer.domain.Teacher;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.service.TeacherService;

@RestController
@RequestMapping("/${application.api.path}/teachers")
public class TeacherController extends BaseApiController {
    private final TeacherService teacherService;

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
        return getTeacherByIdOrThrowAnException(id);
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
        getTeacherByIdOrThrowAnException(id);
        teacherService.deleteTeacherById(id);
        return "Teacher with " + id + " was deleted!";
    }

    private Teacher getTeacherByIdOrThrowAnException(Long id) {
        Teacher teacher = teacherService.findTeacherById(id).orElse(null);
        if (teacher == null) {
            throw new NoSuchPersonException(
                    String.format("There is no teacher with id=%d", id)
            );
        }
        return teacher;
    }


}

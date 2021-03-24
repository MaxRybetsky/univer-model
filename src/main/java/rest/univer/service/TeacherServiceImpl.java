package rest.univer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.univer.domain.Teacher;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.repository.TeacherRepository;

import javax.transaction.Transactional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional
    public Teacher saveTeacher(Teacher teacher) {
        Long teacherId = teacher.getId();
        if(teacherId != null) {
            getTeacherByIdOrThrowAnException(teacherId);
        }
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public void deleteTeacherById(Long id) throws NoSuchPersonException {
        getTeacherByIdOrThrowAnException(id);
        teacherRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Teacher findTeacherById(Long id) throws NoSuchPersonException {
        return getTeacherByIdOrThrowAnException(id);
    }

    @Override
    @Transactional
    public Iterable<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    private Teacher getTeacherByIdOrThrowAnException(Long id) throws NoSuchPersonException {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) {
            throw new NoSuchPersonException(
                    String.format("There is no teacher with id=%d", id)
            );
        }
        return teacher;
    }
}

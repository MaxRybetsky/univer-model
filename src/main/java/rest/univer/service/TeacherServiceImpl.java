package rest.univer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.univer.domain.Teacher;
import rest.univer.repository.TeacherRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Teacher> findTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    @Transactional
    public Iterable<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

}

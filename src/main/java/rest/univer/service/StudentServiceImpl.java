package rest.univer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rest.univer.domain.Student;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.exceptions.PersonIncorrectData;
import rest.univer.repository.StudentRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional
    public Iterable<Student> findAllStudents() {
        return studentRepository.findAll();
    }
}

package rest.univer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.univer.domain.Student;
import rest.univer.exceptions.NoSuchPersonException;
import rest.univer.repository.StudentRepository;

import javax.transaction.Transactional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        Long studentId = student.getId();
        if(studentId != null) {
            getStudentByIdOrThrowAnException(studentId);
        }
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(Long id) throws NoSuchPersonException {
        getStudentByIdOrThrowAnException(id);
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Student findStudentById(Long id) throws NoSuchPersonException {
        return getStudentByIdOrThrowAnException(id);
    }

    @Override
    @Transactional
    public Iterable<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    private Student getStudentByIdOrThrowAnException(Long id) throws NoSuchPersonException {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            throw new NoSuchPersonException(
                    String.format("There is no student with id=%d", id)
            );
        }
        return student;
    }
}

package rest.univer.repository;

import org.springframework.data.repository.CrudRepository;
import rest.univer.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
}

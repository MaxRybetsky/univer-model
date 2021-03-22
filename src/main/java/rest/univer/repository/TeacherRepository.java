package rest.univer.repository;

import org.springframework.data.repository.CrudRepository;
import rest.univer.domain.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}

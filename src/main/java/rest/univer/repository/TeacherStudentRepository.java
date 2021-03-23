package rest.univer.repository;

import org.springframework.data.repository.CrudRepository;
import rest.univer.domain.TeacherStudent;

public interface TeacherStudentRepository extends CrudRepository<TeacherStudent, Long> {
}

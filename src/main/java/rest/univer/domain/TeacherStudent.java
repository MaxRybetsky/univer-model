package rest.univer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TeacherStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

    public TeacherStudent(Teacher teacher, Student student) {
        this.teacher = teacher;
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherStudent that = (TeacherStudent) o;
        return Objects.equals(student.getId(), that.student.getId())
                && Objects.equals(teacher.getId(), that.teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(student.getId(), teacher.getId());
    }
}
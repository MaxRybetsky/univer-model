package rest.univer.util;

import rest.univer.domain.Student;
import rest.univer.domain.Teacher;
import rest.univer.domain.TeacherStudent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CompareHelper {
    public static boolean haveSameElements(final Iterable<?> iterable1, final Iterable<?> iterable2) {
        if (iterable1 == iterable2) {
            return true;
        }
        if (iterable1 == null || iterable2 == null) {
            return false;
        }
        Map<Object, Count> counts = new HashMap<>();
        for (Object item : iterable1) {
            if (!counts.containsKey(item)) {
                counts.put(item, new Count());
            }
            counts.get(item).count += 1;
        }
        for (Object item : iterable2) {
            if (!counts.containsKey(item)) {
                return false;
            }
            counts.get(item).count -= 1;
        }
        for (Map.Entry<Object, Count> entry : counts.entrySet()) {
            if (entry.getValue().count != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareStudentsIterableWithSet(Iterable<Student> students,
                                                         Set<TeacherStudent> studentsSet) {
        Iterable<Student> studentsIt = studentsSet.stream()
                .map(TeacherStudent::getStudent)
                .collect(Collectors.toSet());
        return haveSameElements(students, studentsIt);
    }

    public static boolean compareTeachersIterableWithSet(Iterable<Teacher> teachers,
                                                         Set<TeacherStudent> teacherSet) {
        Iterable<Teacher> teacherIterable = teacherSet.stream()
                .map(TeacherStudent::getTeacher)
                .collect(Collectors.toSet());
        return haveSameElements(teachers, teacherIterable);
    }

    private static class Count {
        public int count = 0;
    }
}

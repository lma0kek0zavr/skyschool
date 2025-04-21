package ru.hogwarts.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {   
    
    @Query("select s from Student s where s.age between :minRange and :maxRange")
    List<Student> findByAgeBetween(int minRange, int maxRange);

    @Query("select s.name from Student s")
    List<String> getStudentAndDisplayOnlyNames();

    @Query("select s from Student s where s.name like lower(concat('%', :character, '%'))")
    List<Student> findByNameContains(char character);

    @Query("select s from Student s where s.age < :age")
    List<Student> findStudentsWhoseAgeIsLessThanGiven(int age);

    @Query("select s from Student s order by s.age desc")
    List<Student> getStudentsSortedByAge();

    @Query("select f from Student s join s.studentFaculties f where s.id = :id")
    Faculty getStudentFaculty(Long id);
}

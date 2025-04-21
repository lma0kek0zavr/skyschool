SELECT * FROM student WHERE student.age BETWEEN :ageFrom AND :ageTo;

SELECT student.name FROM student;

SELECT * FROM student WHERE student.name LIKE lower(concat('%', :character, '%'));

SELECT * FROM student WHERE student.age < :age;

SELECT * FROM student ORDER BY student.age DESC;
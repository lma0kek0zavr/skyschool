SELECT * FROM student WHERE student.age BETWEEN :ageFrom AND :ageTo;

SELECT student.name FROM student;

SELECT * FROM student WHERE student.name LIKE lower(concat('%', :character, '%'));

SELECT * FROM student WHERE student.age < :age;

SELECT * FROM student ORDER BY student.age DESC;

SELECT count(s) FROM student s;

SELECT avg(s.age) FROM student s;

SELECT * FROM student ORDER BY student.id DESC LIMIT 5;

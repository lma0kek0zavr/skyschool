SELECT student.name, student.age, faculty_name FROM student LEFT JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age FROM student INNER JOIN avatar ON avatar_id = avatar.id;
-- Active: 1744740270092@@127.0.0.1@5432@hogwarts
-- liquibase formatted sql

-- changeset test:1
CREATE INDEX student_name_index ON student (name);

-- changeset test:2
CREATE INDEX faculty_name_color_index ON faculty (name, color);

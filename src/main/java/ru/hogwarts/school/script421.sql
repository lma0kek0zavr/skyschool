ALTER Table student ADD constraint age_cconstraint CHECK (age >= 16);

ALTER TABLE student ADD constraint name_constraint UNIQUE (name);

ALTER TABLE faculty ADD constraint name_color_constraint UNIQUE (name, color);

ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;
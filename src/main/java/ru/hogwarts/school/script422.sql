CREATE TABLE Human (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    age INT,
    car_license BOOLEAN
)

CREATE TABLE Car (
    id INT PRIMARY KEY,
    brand VARCHAR(255),
    model VARCHAR(255),
    price INT
)

CREATE TABLE HumanCar ( 
    human_id INT REFERENCES Human(id),
    car_id INT REFERENCES Car(id),
    PRIMARY KEY (human_id, car_id)
)
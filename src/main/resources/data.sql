DROP TABLE IF EXISTS employees;
 
CREATE TABLE employees (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  emp_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  age INT NOT NULL,
  sex VARCHAR(250) DEFAULT NULL
);

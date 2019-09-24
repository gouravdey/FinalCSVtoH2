package com.example.latestCSVtoH2.dao;

import com.example.latestCSVtoH2.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Employee> findAll() {
        return jdbcTemplate.query("select * from employees",
                new BeanPropertyRowMapper(Employee.class));
    }
}

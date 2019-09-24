package com.example.latestCSVtoH2.batch;

import com.example.latestCSVtoH2.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    DataSource dataSource;

    @Bean
    public FlatFileItemReader<Employee> reader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("EmployeeItemReader")
                .resource(new ClassPathResource("employee.csv"))
                .delimited()
                .names(new String[]{"empId", "name", "age", "sex"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {{
                    setTargetType(Employee.class);
                }})
                .linesToSkip(1)
                .build();
    }

//    @Bean
//    public ItemWriter<Employee> writer() {
//        return items -> {
//            for (Employee item : items) {
//                System.out.println(item.toString());
//            }
//        };
//    }

    @Bean
    public JdbcBatchItemWriter<Employee> writer() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
            itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO EMPLOYEES (EMP_ID, NAME, AGE, SEX) VALUES (:empId, :name, :age, :sex)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }


    @Bean
    public Step step() {
        return stepBuilderFactory.get("Step")
                .<Employee, Employee>chunk(5)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("Job")
                .start(step())
                .build();
    }
}

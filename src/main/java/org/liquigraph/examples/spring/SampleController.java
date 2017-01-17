package org.liquigraph.examples.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

@SpringBootApplication
@RestController
public class SampleController {

    private final DataSource dataSource;

    public SampleController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/")
    String home() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            if (!statement.execute("MATCH (n:Sentence) RETURN n.text AS result")) {
                throw new RuntimeException("Could not execute query");
            }
            return extract("result", statement.getResultSet());
        }
    }

    private String extract(String columnLabel, ResultSet results) throws SQLException {
        try (ResultSet resultSet = results) {
            resultSet.next();
            return resultSet.getString(columnLabel);
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}

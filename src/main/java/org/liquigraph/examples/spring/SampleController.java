package org.liquigraph.examples.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
@EnableAutoConfiguration
@Import(MigrationConfiguration.class)
public class SampleController {

    private final DataSource dataSource;

    @Autowired
    public SampleController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping("/")
    @ResponseBody
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
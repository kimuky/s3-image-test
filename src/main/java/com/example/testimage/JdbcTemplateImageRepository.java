package com.example.testimage;

import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateImageRepository implements ImageRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateImageRepository(DataSource dataSource) {
         this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void batchInsert() {
//        jdbcTemplate.batchUpdate()
    }
}

package com.springinaction.knights;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 使用jdbcTemplate重写的getEmployeeById()方法仅仅关注于获取员工数据的核心逻辑，而不需要迎合JDBC API的需求。
 * <p>
 * <p>
 * Created by keen.zhao on 2016/9/27.
 */
public class JdbcTemplateDemo {

    public static void main(String[] args) {
        Employee employee = getEmployeeById(2);
        if (employee != null) System.out.println(employee.toString());
    }

    public static Employee getEmployeeById(long id) {

        String url = "jdbc:mysql://localhost:3306/employees?" +
                "user=root&password=19790930" +
                "&useUnicode=true&characterEncoding=UTF8"; //连接串

        DataSource datasource = new DriverManagerDataSource(url);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

        return jdbcTemplate.queryForObject(
                "SELECT id,firstname,lastname,salary from employee where id=?"
                , new RowMapper<Employee>() {
                    @Override
                    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Employee employee = new Employee();
                        employee.setId(rs.getLong("id"));
                        employee.setFirstName(rs.getString("firstname"));
                        employee.setLastName(rs.getString("lastname"));
                        employee.setSalary(rs.getBigDecimal("salary"));
                        return employee;
                    }
                }
                , id);

    }

}

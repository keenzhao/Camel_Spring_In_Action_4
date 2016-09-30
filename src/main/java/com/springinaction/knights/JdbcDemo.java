package com.springinaction.knights;

import java.sql.*;

/**
 *
 * 正如下面看到的，这段JDBC代码查询数据库获得员工姓名和薪水。少量查询员工的代码淹没在一堆JDBC的样板式代码中。
 * 首先你要创建一个数据库连接，然后创建一个语句对象，最后你才能查询。为了平息JDBC可能出现的怒火，你必须捕捉
 * SQLException,这是一个检查型异常，即使它抛出后你也做不了太多事情，最后，毕竟该说的也说了，该做的也做了，你不得不
 * 清理战场，关闭数据库连接、语句和结果集。同样你为了平息JDBC可能出现的怒火，你依然要捕捉SQLException。
 *
 * 下面的代码和你实现其他JDBC操作时所写的代码几乎是相同的。只少量的代码与查询员工逻辑有关系，其他都是JDBC的
 * 样板代码。
 *
 * JDBC不是产生样板代码的唯一场景。在许多编程场景中往往都会导致类似的样板式代码，
 * JMS、JNDI和使用REST服务通常也涉及大量的重复代码。
 * Spring旨在通过模板封装来消除样板式代码，Spring的jdbcTemplate使得执行数据库操作时，避免传统的JDBC样板代码
 * 称为可能。
 *
 * Created by keen.zhao on 2016/9/27.
 */
public class JdbcDemo {

    public static void main(String[] args) {

        Employee employee = getEmployeeById(1);
        if(employee!=null) System.out.println(employee.toString());

    }

    //样板代码的例子演示
    public static Employee getEmployeeById(long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建employee数据库
        String url = "jdbc:mysql://localhost:3306/employees?" +
                "user=root&password=19790930" +
                "&useUnicode=true&characterEncoding=UTF8"; //连接串

        try {
             Class.forName("com.mysql.jdbc.Driver"); //加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try {
            conn = DriverManager.getConnection(url); //获取连接
            stmt = conn.prepareStatement("SELECT id,firstname,lastname,salary from employee where id=?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();

            Employee employee = null;

            if (rs.next()) {
                employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setFirstName(rs.getString("firstname"));
                employee.setLastName(rs.getString("lastname"));
                employee.setSalary(rs.getBigDecimal("salary"));
            }
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }

}


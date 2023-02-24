package com.epam.rd.autocode;


import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SetMapperFactory {

    public SetMapper<Set<Employee>> employeesSetMapper() {
        return resultSet -> {
            try {
                Set<Employee> employeeSet = new HashSet<>();
                while (resultSet.next()) {
                    employeeSet.add(getEmployee(resultSet));
                }
                return employeeSet;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        };
    }
    private Employee getEmployee(ResultSet resultSet) {
        try {
            BigInteger id = new BigInteger(resultSet.getString("ID"));
            FullName fullName = new FullName(resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("MIDDLENAME"));
            Position position = Position.valueOf(resultSet.getString("POSITION"));
            LocalDate hired = resultSet.getDate("HIREDATE").toLocalDate();
            BigDecimal salary = resultSet.getBigDecimal("SALARY");
            Employee manager = getEmployeeById(resultSet, resultSet.getInt("MANAGER"));
            return new Employee(id, fullName, position, hired, salary, manager);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private Employee getEmployeeById(ResultSet resultSet, int id) {
        try {
            Employee employee = null;
            int rowToReturn = resultSet.getRow();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                if (resultSet.getInt("ID") == id) {
                    employee = getEmployee(resultSet);
                    break;
                }
            }
            resultSet.absolute(rowToReturn);
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

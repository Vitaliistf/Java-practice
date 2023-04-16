package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.domain.Department;
import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {
    private static final String ID = "ID";
    private static final String FIRST_NAME = "FIRSTNAME";
    private static final String LAST_NAME = "LASTNAME";
    private static final String MIDDLE_NAME = "MIDDLENAME";
    private static final String HIRE_DATE = "HIREDATE";
    private static final String POSITION = "POSITION";
    private static final String SALARY = "SALARY";
    private static final String MANAGER_ID = "MANAGER";
    private static final String DEPARTMENT_ID = "DEPARTMENT";

    private static final String GET_BY_ID = "SELECT * FROM EMPLOYEE WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM EMPLOYEE";
    private static final String INSERT = "INSERT INTO EMPLOYEE VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM EMPLOYEE WHERE ID = ? AND FIRSTNAME = ? AND LASTNAME = ? AND MIDDLENAME = ? AND " +
                "POSITION = ? AND MANAGER = ? AND HIREDATE = ? AND SALARY = ? AND DEPARTMENT = ?";

    private static final String GET_EMPLOYEES_BY_DEPARTMENT = "SELECT * FROM EMPLOYEE WHERE DEPARTMENT = ?";
    private static final String GET_EMPLOYEES_BY_MANAGER = "SELECT * FROM EMPLOYEE WHERE MANAGER = ?";

    Connection connection;

    EmployeeDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Employee> getById(BigInteger id) {
        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setString(1, id.toString());
            statement.execute();
            ResultSet set = statement.getResultSet();

            if(set.next()) {
                return Optional.of(rowMapper(set));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }
    @Override
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
            statement.execute();
            ResultSet set = statement.getResultSet();
            while (set.next()) {
                list.add(rowMapper(set));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return list;
    }

    @Override
    public Employee save(Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT)){
            objectToStatement(statement, employee);
            statement.execute();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return employee;
    }

    @Override
    public void delete(Employee employee) {
        try(PreparedStatement statement = connection.prepareStatement(DELETE)) {
            objectToStatement(statement, employee);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Employee> getByDepartment(Department department) {
        List<Employee> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(GET_EMPLOYEES_BY_DEPARTMENT)) {
            statement.setString(1, department.getId().toString());
            statement.execute();
            ResultSet set = statement.getResultSet();
            while (set.next()) {
                list.add(rowMapper(set));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return list;
    }

    @Override
    public List<Employee> getByManager(Employee employee) {
        List<Employee> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(GET_EMPLOYEES_BY_MANAGER)) {
            statement.setString(1, employee.getId().toString());
            statement.execute();
            ResultSet set = statement.getResultSet();
            while (set.next()) {
                list.add(rowMapper(set));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return list;
    }

    void objectToStatement(PreparedStatement statement, Employee employee) {
        try {
            statement.setString(1, employee.getId().toString());
            statement.setString(2, employee.getFullName().getFirstName());
            statement.setString(3, employee.getFullName().getLastName());
            statement.setString(4, employee.getFullName().getMiddleName());
            statement.setString(5, employee.getPosition().toString());
            statement.setString(6, employee.getManagerId().toString());
            statement.setString(7, employee.getHired().toString());
            statement.setString(8, employee.getSalary().toString());
            statement.setString(9, employee.getDepartmentId().toString());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    Employee rowMapper(ResultSet set) {
        try {
            BigInteger id = BigInteger.valueOf(set.getInt(ID));
            FullName fullName = new FullName(
                    set.getString(FIRST_NAME),
                    set.getString(LAST_NAME),
                    set.getString(MIDDLE_NAME)
            );
            Position position = Position.valueOf(set.getString(POSITION));
            BigInteger managerId = BigInteger.valueOf(set.getInt(MANAGER_ID));
            LocalDate hired = set.getDate(HIRE_DATE).toLocalDate();
            BigDecimal salary = BigDecimal.valueOf(set.getDouble(SALARY));
            BigInteger departmentId = BigInteger.valueOf(set.getInt(DEPARTMENT_ID));

            return new Employee(id, fullName, position, hired, salary, managerId, departmentId);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.domain.Department;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DepartmentDaoImpl implements DepartmentDao {
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String LOCATION = "LOCATION";

    private static final String GET_BY_ID = "SELECT * FROM DEPARTMENT WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM DEPARTMENT";
    private static final String INSERT = "INSERT INTO DEPARTMENT VALUES (?,?,?)";
    private static final String DELETE = "DELETE FROM DEPARTMENT WHERE ID = ? AND NAME = ? AND LOCATION = ?";
    private static final String UPDATE = "UPDATE DEPARTMENT SET NAME = ?, LOCATION = ? WHERE ID = ?";

    Connection connection;

    DepartmentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Department> getById(BigInteger id) {
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
    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
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
    public Department save(Department department) {
        try {
            PreparedStatement statement;
            if(getById(department.getId()).isPresent()) {
                statement = connection.prepareStatement(UPDATE);
                objectToUpdateStatement(statement, department);
            } else {
                statement = connection.prepareStatement(INSERT);
                objectToStatement(statement, department);
            }
            statement.execute();

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return department;
    }

    @Override
    public void delete(Department department) {
        try(PreparedStatement statement = connection.prepareStatement(DELETE)) {
            objectToStatement(statement, department);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    void objectToStatement(PreparedStatement statement, Department department) {
        try {
            statement.setString(1, department.getId().toString());
            statement.setString(2, department.getName());
            statement.setString(3, department.getLocation());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    void objectToUpdateStatement(PreparedStatement statement, Department department) {
        try {
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());
            statement.setString(3, department.getId().toString());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    Department rowMapper(ResultSet set) {
        try {
            BigInteger id = BigInteger.valueOf(set.getInt(ID));
            String name = set.getString(NAME);
            String location = set.getString(LOCATION);
            return new Department(id, name, location);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

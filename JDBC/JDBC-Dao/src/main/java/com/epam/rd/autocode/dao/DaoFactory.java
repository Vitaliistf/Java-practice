package com.epam.rd.autocode.dao;

import com.epam.rd.autocode.ConnectionSource;
import java.sql.SQLException;

public class DaoFactory {
    public EmployeeDao employeeDAO() {
        try {
            return new EmployeeDaoImpl(ConnectionSource.instance().createConnection());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public DepartmentDao departmentDAO() {
        try {
            return new DepartmentDaoImpl(ConnectionSource.instance().createConnection());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

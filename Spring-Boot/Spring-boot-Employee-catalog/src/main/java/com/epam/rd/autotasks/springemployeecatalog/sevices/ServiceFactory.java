package com.epam.rd.autotasks.springemployeecatalog.sevices;

import com.epam.rd.autotasks.springemployeecatalog.DBConnection.Connection;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceFactory {

    private final static Statement statement;

    static {
        try {
            statement = Connection.getInstance().createConnection().createStatement();
        } catch (Exception exception) {
            throw new UnsupportedOperationException();
        }
    }

    private static ResultSet wrapQueryWithTry(String query) {
        try {
            return statement.executeQuery(query);
        } catch (Exception exception) {
            throw new UnsupportedOperationException();
        }
    }

    public static EmployeeService employeeService() {
        return new EmployeeService() {
            private final String DEFAULT_SELECTION = "SELECT * FROM EMPLOYEE LEFT JOIN DEPARTMENT ON EMPLOYEE.DEPARTMENT = DEPARTMENT.ID ";

            private List<Employee> getAllEmployees(String query) {
                return new ListMapperFactory().employeesListMapper(false).mapList(
                        wrapQueryWithTry(query)
                );
            }

            private List<Employee> getAllEmployeesWithChain(String query) {
                return new ListMapperFactory().employeesListMapper(true).mapList(
                        wrapQueryWithTry(query)
                );
            }

            private List<Employee> getEmployeesByDepartment(String query, String department) {
                if (department.matches("\\d+")) {
                    return getAllEmployees(query)
                            .stream()
                            .filter(employee -> employee.getDepartment() != null &&
                                    employee.getDepartment().getId().equals(Long.parseLong(department)))
                            .collect(Collectors.toList());
                } else {
                    return getAllEmployees(query)
                            .stream()
                            .filter(employee -> employee.getDepartment() != null &&
                                    employee.getDepartment().getName().equals(department))
                            .collect(Collectors.toList());
                }
            }

            private List<Employee> getEmployeesByManager(String query, Long managerId) {
                return getAllEmployees(query)
                        .stream()
                        .filter(employee -> employee.getManager() != null && employee.getManager().getId().equals(managerId))
                        .collect(Collectors.toList());
            }

            private List<Employee> employeePaging(List<Employee> employees, Paging paging) {
                List<Employee> employeesPage = new ArrayList<>();
                for (int index = paging.page * paging.itemPerPage;
                     index < employees.size() && index < (paging.page + 1) * paging.itemPerPage;
                     ++index) {
                    employeesPage.add(employees.get(index));
                }
                return employeesPage;
            }

            @Override
            public List<Employee> getAll() {
                return getAllEmployees(DEFAULT_SELECTION + "ORDER BY LASTNAME");
            }

            @Override
            public List<Employee> getAllSortBy(Paging paging, String order) {
                return employeePaging(getAllEmployees(DEFAULT_SELECTION + "ORDER BY " + order.toUpperCase()), paging);
            }

            @Override
            public Employee getById(Long employeeId) {
                return getAllEmployees(DEFAULT_SELECTION).stream()
                        .filter(item -> item.getId().equals(employeeId))
                        .findAny()
                        .orElse(null);
            }

            @Override
            public Employee getByIdWithFullChain(Long employeeId) {
                return getAllEmployeesWithChain(DEFAULT_SELECTION).stream()
                        .filter(item -> item.getId().equals(employeeId))
                        .findAny()
                        .orElse(null);
            }

            @Override
            public List<Employee> getByManager(Long managerId, Paging paging, String order) {
                return employeePaging(getEmployeesByManager(DEFAULT_SELECTION + "ORDER BY " + order, managerId), paging);
            }

            @Override
            public List<Employee> getByDepartment(String department, Paging paging, String order) {
                return employeePaging(getEmployeesByDepartment(DEFAULT_SELECTION + "ORDER BY " + order, department), paging);
            }
        };
    }
}

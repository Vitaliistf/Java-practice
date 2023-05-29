package com.epam.rd.autotasks.springemployeecatalog.sevices;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMapperFactory {

    private void initEmployeeListWithManagerMap(ResultSet resultSet, List<Employee> employeeList, Map<Long, Long> managerMap) {
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("EMPLOYEE.ID");
                String firstName = resultSet.getString("FIRSTNAME");
                String lastName = resultSet.getString("LASTNAME");
                String middleName = resultSet.getString("MIDDLENAME");
                Position position = Position.valueOf(resultSet.getString("POSITION"));
                LocalDate hired = resultSet.getDate("HIREDATE").toLocalDate();
                BigDecimal salary = resultSet.getBigDecimal("SALARY");
                long departmentId = resultSet.getLong("DEPARTMENT.ID");
                String name = resultSet.getString("NAME");
                String location = resultSet.getString("LOCATION");

                Long managerId = resultSet.getLong("MANAGER");
                managerMap.put(id, managerId);

                employeeList.add(new Employee(
                        id,
                        new FullName(firstName, lastName, middleName),
                        position,
                        hired,
                        salary,
                        null,
                         departmentId != 0 ? new Department(departmentId, name, location) : null
                ));
            }
        } catch (Exception exception) {
            throw new UnsupportedOperationException();
        }
    }

    private void toChainEmployees(List<Employee> resultEmployeeList, List<Employee> employeeList, Map<Long, Long> managerMap) {
        for (Employee employee : employeeList) {
            resultEmployeeList.add(new Employee(
                    employee.getId(),
                    employee.getFullName(),
                    employee.getPosition(),
                    employee.getHired(),
                    employee.getSalary(),
                    employeeList.stream()
                            .filter(manager -> managerMap.get(employee.getId()).equals(manager.getId()))
                            .findAny()
                            .orElse(null),
                    employee.getDepartment()
            ));
        }
    }

    public ListMapper<List<Employee>> employeesListMapper(Boolean withChaining) {
        if (withChaining) {
            return resultSet -> {
                Map<Long, Long> managerMap = new HashMap<>();
                List<Employee> temporaryEmployeeList = new ArrayList<>();
                List<Employee> resultEmployeeList = new ArrayList<>();
                initEmployeeListWithManagerMap(resultSet, temporaryEmployeeList, managerMap);

                while (true) {
                    toChainEmployees(resultEmployeeList, temporaryEmployeeList, managerMap);
                    if (temporaryEmployeeList.equals(resultEmployeeList)) break;
                    temporaryEmployeeList.clear();
                    temporaryEmployeeList.addAll(resultEmployeeList);
                    resultEmployeeList.clear();
                }

                return resultEmployeeList;
            };
        }
        else {
            return resultSet -> {
                Map<Long, Long> managerMap = new HashMap<>();
                List<Employee> temporaryEmployeeList = new ArrayList<>();
                List<Employee> resultEmployeeList = new ArrayList<>();
                initEmployeeListWithManagerMap(resultSet, temporaryEmployeeList, managerMap);
                toChainEmployees(resultEmployeeList, temporaryEmployeeList, managerMap);
                return resultEmployeeList;
            };
        }
    }
}

package com.epam.rd.autotasks.springemployeecatalog.sevices;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    Employee getById(Long employeeId);

    Employee getByIdWithFullChain(Long employeeId);

    List<Employee> getAllSortBy(Paging paging, String order);

    List<Employee> getByManager(Long managerId, Paging paging, String order);

    List<Employee> getByDepartment(String department, Paging paging, String order);

}

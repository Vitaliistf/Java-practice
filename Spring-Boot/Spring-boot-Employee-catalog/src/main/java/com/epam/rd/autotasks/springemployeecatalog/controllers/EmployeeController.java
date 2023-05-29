package com.epam.rd.autotasks.springemployeecatalog.controllers;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.sevices.Paging;
import com.epam.rd.autotasks.springemployeecatalog.sevices.ServiceFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @GetMapping()
    public List<Employee> getAllEmployees(@RequestParam(value = "page", required = false) String page,
                                          @RequestParam(value = "size", required = false) String size,
                                          @RequestParam(value = "sort", required = false) String sort) {
        if (page == null) {
            return ServiceFactory.employeeService().getAll();
        }
        Paging paging = new Paging(Integer.parseInt(page), Integer.parseInt(size));
        if(sort.equals("hired")) {
            sort = "hiredate";
        }
        return ServiceFactory.employeeService().getAllSortBy(paging, sort);
    }

    @GetMapping("/{employee_id}")
    public Employee getEmployeeById(@PathVariable Long employee_id,
                                    @RequestParam(value = "full_chain", defaultValue = "false") Boolean full_chain) {
        if (full_chain) {
            return ServiceFactory.employeeService().getByIdWithFullChain(employee_id);
        } else {
            return ServiceFactory.employeeService().getById(employee_id);
        }
    }

    @GetMapping("/by_manager/{manager_id}")
    public List<Employee> getEmployeesByManager(@PathVariable Long manager_id,
                                                @RequestParam("page") String page,
                                                @RequestParam("size") String size,
                                                @RequestParam("sort") String sort) {
        Paging paging = new Paging(Integer.parseInt(page), Integer.parseInt(size));
        if(sort.equals("hired")) {
            sort = "hiredate";
        }
        return ServiceFactory.employeeService().getByManager(manager_id, paging, sort);

    }

    @GetMapping("/by_department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department,
                                                   @RequestParam("page") String page,
                                                   @RequestParam("size") String size,
                                                   @RequestParam("sort") String sort) {
        Paging paging = new Paging(Integer.parseInt(page), Integer.parseInt(size));

        if(sort.equals("hired")) {
            sort = "hiredate";
        }
        return ServiceFactory.employeeService().getByDepartment(department, paging, sort);
    }
}

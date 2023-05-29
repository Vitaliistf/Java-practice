package com.epam.rd.autotasks.springemployeecatalog.sevices;

import java.sql.ResultSet;

public interface ListMapper<T> {
    T mapList(ResultSet resultSet);
}

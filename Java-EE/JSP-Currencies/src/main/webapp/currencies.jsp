<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="currencies" class="com.epam.rd.jsp.currencies.CurrenciesOfCurrentTestCase" scope="request"/>

<html>
<head>
    <title>Currencies</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<h1>Currencies</h1>
<ul>
<%
    for(String entry : currencies.getCurrencies()) {
%>
    <li> <%= entry %> </li>
    <%
        }
    %>
</ul>
</body>
</html>
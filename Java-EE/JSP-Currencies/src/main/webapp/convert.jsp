<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.math.BigDecimal" %>

<jsp:useBean id="currencies" class="com.epam.rd.jsp.currencies.CurrenciesOfCurrentTestCase" scope="request"/>
<html>
<head>
    <title>Convert</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<%
    BigDecimal amount = new BigDecimal(request.getParameter("amount"));
    String from = request.getParameter("from");
    String to = request.getParameter("to");
%>
<h1>Converting <%= from %> to <%= to %></h1>

<%
    String format = "%s %s = %s %s";
%>

<%= String.format(format, amount, from, currencies.convert(amount, from, to).toString(), to) %>

</body>
</html>
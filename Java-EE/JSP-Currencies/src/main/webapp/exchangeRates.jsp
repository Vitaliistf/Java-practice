<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="java.math.BigDecimal" %>

<jsp:useBean id="currencies" class="com.epam.rd.jsp.currencies.CurrenciesOfCurrentTestCase" scope="request"/>
<html>
<head>
    <title>Exchange rates</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<% String currency = request.getParameter("from"); %>
<h1>Exchange Rates for <%= currency %></h1>

<%
    Set<Map.Entry<String, BigDecimal>> entrySet = currencies.getExchangeRates(currency).entrySet();
    int i = 1;
    String format = "%d %s = %s %s";
    for(Map.Entry<String, BigDecimal> entry : entrySet) {
        if(!entry.getKey().equals(currency)) {
%>
        <%= String.format(format, i++, currency, entry.getValue().toString(), entry.getKey()) %>
<%
        }
    }
%>

</body>
</html>
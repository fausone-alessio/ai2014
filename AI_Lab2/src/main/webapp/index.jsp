<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<s:url id="contextUrl" action="" namespace="" />   
	<h1>Prenotazione sale</h1>
	<a href="<s:property value="#contextUrl"/>/login.action">Login</a><br/>
	<a href="<s:property value="#contextUrl"/>/calendar.action">Consulta Calendario</a>
</body>
</html>

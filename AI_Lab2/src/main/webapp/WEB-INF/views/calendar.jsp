<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<p>Benvenuto <%= session.getAttribute("username") %> [<%= session.getAttribute("ruolo") %>] </p>
	
	<table border="1">
	<% Map<Integer, Map<Date, String>> slots = (Map<Integer, Map<Date, String>>) request.getAttribute("slots");
		out.println("<tr><td>Orari</td>");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for(Map.Entry<Date, String> day : slots.get(0).entrySet()) {
			out.println("<td>"+df.format(day.getKey())+"</td>");
		}
		out.println("</tr>");
		Calendar cal = Calendar.getInstance();
		cal.clear();
		df = new SimpleDateFormat("HH:mm");
		for(Map.Entry<Integer,Map<Date, String>> hour : slots.entrySet()) {
			cal.set(Calendar.HOUR_OF_DAY,hour.getKey());
			out.println("<tr><td>"+df.format(cal.getTime())+"</td>");
			for(Map.Entry<Date,String> day : hour.getValue().entrySet()) {
				out.println("<td>"+day.getValue()+"</td>");
			}
			out.println("<\tr>");
		}
	%>
	</table>
	
	<s:form action="calendar" method="POST"> 
	
		<s:select key="room"
		list="#{'1':'S1', '2':'S2', '3':'S3', '4':'S4'}" 
		name="room" 
		value="3" />
		
		<s:select key="settimana"
		list="#{'1':'Settimana 1', '2':'Settimana 2', '3':'Settimana 3', '4':'Settimana 4'}" 
		name="settimana" 
		value="1" />
		
		<s:submit />
	</s:form>
	
</body>
</html>
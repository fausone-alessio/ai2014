<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>

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
	
	<s:url id="contextUrl" action="" namespace="" />
	<a href="<s:property value="#contextUrl"/>/calendar.action?week=<%= request.getAttribute("prevWeek") %>&year=<%= request.getAttribute("prevYear") %>">&lt;&lt;Settimana precedente</a> --- 
	<a href="<s:property value="#contextUrl"/>/calendar.action?week=<%= request.getAttribute("nextWeek") %>&year=<%= request.getAttribute("nextYear") %>">&gt;&gt;Settimana successiva</a><br/>
	
	<table border="1">
	<%! @SuppressWarnings("unchecked") %>
	<%  Map<Date, String> slots = (Map<Date, String>) request.getAttribute("slots");
	int days_in_week = 7;
	Iterator <Date> itPR = slots.keySet().iterator();
	SimpleDateFormat sdfPR = new SimpleDateFormat ("dd/MM/yyyy");
	
	out.println("<tr>");
	for (int cc = 0; cc < days_in_week + 1; cc++) {
		if (cc == 0) out.println("<td></td>");
		else out.println("<td>"+sdfPR.format(itPR.next())+"</td>");
	}
	out.println("</tr>");
	
	int cc = 0;
	String riga = "";
	Iterator <Date> itTB = slots.keySet().iterator();
	Iterator <String> itsTB = slots.values().iterator();
	SimpleDateFormat sdfPC = new SimpleDateFormat ("HH:mm");
	SimpleDateFormat sdfTB = new SimpleDateFormat ("'day='dd'&month='MM'&year='yyyy'&startTime='HH'&room=1'");
	while (itTB.hasNext()) {
		Date data = itTB.next();
		String prenotante = itsTB.next();
		
		if ((cc % (days_in_week + 1)) == 0) {
			riga = "<td>" + sdfPC.format(data) + "</td>";
			cc++;
		}
		if (prenotante != null && !prenotante.isEmpty())
			riga = riga + "<td><a href=prenotazione?" + sdfTB.format(data) + ">[P]</a></td>";
		else
			riga = riga + "<td><a href=annullamento?" + sdfTB.format(data) + ">[P]</a></td>";
		cc++;
		if ((cc % (days_in_week + 1)) == 0) {
			out.println("<tr>" + riga + "</tr>");
		}
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
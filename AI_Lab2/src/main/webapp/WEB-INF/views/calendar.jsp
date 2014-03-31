<%@page import="java.util.Date"%> <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%> <%@page import="java.util.Map"%> 
<%@page import="java.util.Iterator"%> <%@page import="java.util.Set"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calendario prenotazioni</title>
</head>
<body>
	<p>Benvenuto <%= session.getAttribute("username") %> [<%= session.getAttribute("ruolo") %>] </p>
	
	<%! @SuppressWarnings("unchecked") %>
	<%  Map<Date, String> slots = (Map<Date, String>) request.getAttribute("slots");
	int days_in_week = 7;
	Iterator <Date> itPR = slots.keySet().iterator();
	SimpleDateFormat sdfPR = new SimpleDateFormat ("dd/MM/yyyy");
	out.println("<table border=\"1\">");
	out.println("\t<tr>");
	for (int cc = 0; cc < days_in_week + 1; cc++) {
		if (cc == 0) out.println("\t\t<td></td>");
		else out.println("\t\t<td>"+sdfPR.format(itPR.next())+"</td>");
	}
	out.println("\t</tr>");
	
	int cc = 0;
	String riga = "";
	Iterator <Date> itTB = slots.keySet().iterator();
	Iterator <String> itsTB = slots.values().iterator();
	SimpleDateFormat sdfPC = new SimpleDateFormat ("HH:mm");
	SimpleDateFormat sdfTB = new SimpleDateFormat ("'day='dd'&amp;month='MM'&amp;year='yyyy'&amp;startTime='HH'&amp;room=1'");
	while (itTB.hasNext()) {
		Date data = itTB.next();
		String prenotante = itsTB.next();
		
		if ((cc % (days_in_week + 1)) == 0) {
			riga = "\n<td>" + sdfPC.format(data) + "</td>";
			cc++;
		}
		
		if (prenotante != null && !prenotante.isEmpty()) {
			if (prenotante.equals(session.getAttribute("username")))
				riga = riga + "\n<td><a href=annullamento?" + sdfTB.format(data) + ">[A]</a></td>";
			else
				riga = riga + "\n<td>[X]</td>";		
		}
		else
			riga = riga + "\n<td><a href=\"prenotazione?" + sdfTB.format(data) + "\">[P]</a></td>";
		cc++;
		if ((cc % (days_in_week + 1)) == 0) {
			out.println("<tr>" + riga + "</tr>");
		}
	}
	out.println("</table>");
	%>
		
	<s:form action="calendar" method="POST"> 
	
		<s:select key="room"
		list="#{'0':'S1', '1':'S2', '2':'S3', '3':'S4'}" 
		name="room" 
		value="3" />
		
		<s:select key="week"
		list="#{'1':'Settimana 1', '2':'Settimana 2', '3':'Settimana 3', '4':'Settimana 4'}" 
		name="week" 
		value="1" />
		
		<s:submit />
	</s:form>
	
</body>
</html>
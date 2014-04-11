<%@page import="java.util.Date"%> <%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%> <%@page import="java.util.Map"%> 
<%@page import="java.util.Iterator"%> <%@page import="java.util.Set"%>
<%@page import="java.util.Locale"%>
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
	<s:url id="contextUrl" action="" namespace="" />
	<p>
		<s:if test="#session['username'] != null">
			Benvenuto <%= session.getAttribute("username") %> [<%= session.getAttribute("ruolo") %>],
			<a href="<s:property value="#contextUrl"/>/logout.action">Logout</a>
		</s:if>
		<s:else>
	    	<a href="<s:property value="#contextUrl"/>/login.action">Login</a> per poter effettuare prenotazioni
		</s:else>
	</p>
	
	Prenotazioni della sala S<s:property value="room+1"/>
		
	<s:form action="calendar" method="POST"> 
	
		<% // TODO Qui non si può segliere il value in base alla property room? %>
		<s:select key="room"
		list="#{'0':'S1', '1':'S2', '2':'S3', '3':'S4'}" 
		name="room" 
		value="room" />
		
		<s:textfield key="day" />
		<% // TODO Mettere una bella select %>
		<s:textfield key="month" />
		<s:textfield key="year" />
		
		<s:submit />
		
	</s:form>
	<br>
	Scorrimento rapido
	<a href="<s:property 
				value="#contextUrl"/>
					/calendar.action?
					day=<s:property value="day"/>
					&month=<s:property value="month"/>
					&year=<s:property value="year"/>
					&room=<s:property value="room"/>
					&jump=prev">
						&#9668;&#9668; - 7 gg.
	</a>
	&#9679;
	<a href="<s:property 
				value="#contextUrl"/>
					/calendar.action?
					day=<s:property value="day"/>
					&month=<s:property value="month"/>
					&year=<s:property value="year"/>
					&room=<s:property value="room"/>
					&jump=next">
						+ 7 gg. &#9658;&#9658; 
	</a>
	<%! @SuppressWarnings("unchecked") %>
	<%  Map<Date, String> slots = (Map<Date, String>) request.getAttribute("slots");
	int days_in_week = 7;
	Iterator <Date> itPR = slots.keySet().iterator();
	SimpleDateFormat sdfPR = new SimpleDateFormat ("EEE dd/MM/yyyy", Locale.ITALY);
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
	int r = (Integer) request.getAttribute("room");
	SimpleDateFormat sdfTB = new SimpleDateFormat ("'day='dd'&amp;month='MM'&amp;year='yyyy'&amp;startTime='HH'&amp;room="+r+"'");
	boolean loggedIn = session.getAttribute("username") != null;
	while (itTB.hasNext()) {
		Date data = itTB.next();
		String prenotante = itsTB.next();
		
		if ((cc % (days_in_week + 1)) == 0) {
			riga = "\n<td>" + sdfPC.format(data) + "</td>";
			cc++;
		}
		
		if (prenotante != null && !prenotante.isEmpty()) {
			if (loggedIn && prenotante.equals(session.getAttribute("username")))
				riga = riga + "\n<td class=\"occupata\"><a href=annullamento?" + sdfTB.format(data) + ">[Annulla]</a></td>";
			else
				riga = riga + "\n<td class=\"occupata\">[Occupata]</td>";		
		}
		else
			if (loggedIn) {
				riga = riga + "\n<td class=\"libera\"><a href=\"prenotazione?" + sdfTB.format(data) + "\">[Prenota]</a></td>";
			} else {
				riga = riga + "\n<td class=\"libera\">[Libera]</td>";		
			}
		cc++;
		if ((cc % (days_in_week + 1)) == 0) {
			out.println("<tr>" + riga + "</tr>");
		}
	}
	out.println("</table>");
	%>
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<%-- if (!session.getAttribute("username").toString().equals("")) { --%>
		<h3>Rinnova la password</h3>
		E' passato più di un mese dall'ultima volta che hai cambiato la password 
		dell'account <i><%= session.getAttribute("username") %></i>.<br>
		Scegline una nuova.<br><br>
		<s:form action="resetPassword.action" method="POST">
		    <s:password key="password1" />
		    <s:password key="password2" />
		    <s:submit/>
		</s:form>
	<%-- } else { --%>
	<%-- pageContext.forward("index.jsp"); --%>
	<%-- } --%>
	
</body>
</html>
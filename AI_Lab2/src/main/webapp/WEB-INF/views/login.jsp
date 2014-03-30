<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<h3>Login</h3>
	Effettua il login per continuare
	
	<s:if test="hasActionErrors()"> 
		<s:actionerror />
	</s:if>
	
	<s:form action="login" method="POST">
		<s:textfield key="username" />
		<s:password key="password" />
		<s:submit />
	</s:form>
</body>
</html>
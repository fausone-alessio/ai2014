<html>
<head>
<title>
Download file
</title>
</head>
<body>
<form method="POST" action="${pageContext.request.contextPath}/download">
Filename<input type="text" name="filename"><br/>
<img src="captcha/captcha.png"><br>
Captcha<input type="text" name="captchastring"/><br/>
<input type="submit" value="Scarica il file selezionato">
</form>
</body>
</html>
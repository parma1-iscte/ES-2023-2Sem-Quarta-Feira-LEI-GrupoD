<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Horario.*"%>
<html>
<head>
	<title>Gestor de Horários</title>
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Carregar Horário</h1>
	<%
		Horario horario = (Horario) request.getSession().getAttribute("horario");
		if(horario != null)
			request.getSession().removeAttribute("horario");
	%>
	<form action="processar_horario.jsp" method="post">
		<label>Webcal:</label><input type="text" name="path"/>
		<br>
		<input type="submit" value="Carregar Horário">
	</form>
</body>
</html>

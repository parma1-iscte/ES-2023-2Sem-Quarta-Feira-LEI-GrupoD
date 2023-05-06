<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Horario.*"%>
<html>
<head>
	<title>Gestor de Horários</title>
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<%
		Horario horario = (Horario) request.getSession().getAttribute("horario");
		if(horario != null)
			request.getSession().removeAttribute("horario");
	%>
	<form action="processar_horario.jsp" method="post">
		<h1>Carregar Horário</h1>
		<br />
		<label>Caminho do ficheiro:</label>
		<input type="text" name="path"/>
		<br>
		<input type="submit" value="Carregar Horário">
	</form>
</body>
</html>

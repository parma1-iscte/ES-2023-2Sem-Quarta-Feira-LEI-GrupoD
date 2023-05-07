<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Horario.*"%>
<html>
<head>
	<title>Gestor de Horários</title>
	<link rel="stylesheet" href="style.css">
</head>
<body>
	
	<form action="guardar_horario.jsp" method="post">
		<h1>Guardar Horário</h1>
		<br />
		<label>Caminho do ficheiro:</label><input type="text" name="path"/>
		<br>
		<input type="submit" value="Guardar Horário">
	</form>
</body>
</html>

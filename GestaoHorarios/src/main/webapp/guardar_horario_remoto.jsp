<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Horario.*"%>
<html>
<head>
	<title>Gestor de Horários</title>
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Guardar Horário</h1>
	<form action="guardar_horario.jsp" method="post">
		<label>URL do ficheiro:</label><input type="text" name="path"/>
		<br>
		<label>Username:</label><input type="text" name="username"/>
		<br>
		<label>Password:</label><input type="password" name="password"/>
		<br>
		<input type="submit" value="Guardar Horário">
	</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Horario.*"%>
<html>
<head>
  <title>Gestor de Horários</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
	<form>
		<h1>Carregar Horário</h1>
		<br />
		<% 
			Horario horario = (Horario) request.getSession().getAttribute("horario");
			if(horario != null)
				request.getSession().removeAttribute("horario");
			String errorMessage = (String) request.getSession().getAttribute("error_message");
			if(errorMessage != null){
				out.println("<script>alert('" + errorMessage + "');</script>");
				request.getSession().removeAttribute("error_message");
			}
		%>
		<input type="submit" value="Ficheiro Local" formaction="carregar_horario_local.jsp" />
		<input type="submit" value="Ficheiro Remoto" formaction="carregar_horario_remoto.jsp" />
		<input type="submit" value="Fénix" formaction="carregar_horario_fenix.jsp" />
	</form>

	
</body>
</html>

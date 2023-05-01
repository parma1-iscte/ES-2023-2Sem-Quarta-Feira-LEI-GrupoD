<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Horario.*"%>
<html>
<head>
  <title>Gestor de Horários</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Horário Carregado</h1>
	<% 
		String errorMessage = (String) request.getSession().getAttribute("error_message");
		if(errorMessage != null){
			out.println("<script>alert('" + errorMessage + "');</script>");
			request.getSession().removeAttribute("error_message");
		}
		Horario horario = (Horario) request.getSession().getAttribute("horario");
		if(horario == null){
			String path = request.getParameter("path");
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			if (path == null || path.trim().isEmpty()) {
	    		request.getSession().setAttribute("error_message", "Localização de ficheiro não preenchida.");
	    		response.sendRedirect("index.jsp");
	    		return;
			}
			try{
				horario = Horario.getHorarioFromFile(path, user, password);
				request.getSession().setAttribute("horario", horario);
			} catch(Exception e){
				request.getSession().setAttribute("error_message", "Falha na leitura do ficheiro");
	    		response.sendRedirect("index.jsp");
	    		return;
			}
		}
	%>
	<form action="visualizar_horario.jsp">
		<input type="submit" value="Visualizar Horário">
	</form>
	<form action="guardar_horario_local.jsp">
		<input type="submit" value="Guardar Horário Localmente">
	</form>
	<form action="guardar_horario_remoto.jsp">
		<input type="submit" value="Guardar Horário Remotamente">
	</form>
	<form action="selecionar_ucs.jsp">
		<input type="submit" value="Criar Novo Horário - Lista de Ucs">
	</form>
	<form action="index.jsp">
		<input type="submit" value="Voltar Página Inicial">
	</form>
</body>
</html>

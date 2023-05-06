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
		if(horario != null){
			String path = request.getParameter("path");
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			if (path == null || path.trim().isEmpty()) {
	    		request.getSession().setAttribute("error_message", "Localização de ficheiro não preenchida.");
	    		response.sendRedirect("processar_horario.jsp");
	    		return;
			}
			try{
				horario.saveHorarioInFile(path, user, password);
			} catch(Exception e){
				request.getSession().setAttribute("error_message", "Problema a guardar o ficheiro.");
	    		response.sendRedirect("processar_horario.jsp");
	    		return;
			}
		}else{
			request.getSession().setAttribute("error_message", "Problema na Sessão.");
    		response.sendRedirect("index.jsp");
    		return;
		}
	%>
	<form>
		<h1>Horário Guardado com Sucesso</h1>
		<br />
		<input type="submit" value="Voltar Página Horário" formaction:"processar_horario.jsp" />
		<input type="submit" value="Voltar Página Inicial" formaction:"index.jsp" />
	</form>
</body>
</html>

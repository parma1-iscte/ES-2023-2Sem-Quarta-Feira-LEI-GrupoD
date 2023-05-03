<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="Horario.*,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestor de Horários</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Selecione as Unidades Curriculares</h1>
	<% 
		String errorMessage = (String) request.getSession().getAttribute("error_message");
		if(errorMessage != null){
			out.println("<script>alert('" + errorMessage + "');</script>");
			request.getSession().removeAttribute("error_message");
		}
		Horario horario = (Horario) request.getSession().getAttribute("horario");
		if(horario == null){
			request.getSession().setAttribute("error_message", "Falha na Sessão");
    		response.sendRedirect("index.jsp");
    		return;
		}
		List<String> ucs = horario.getUcList();
		if(ucs.size() == 0){
			request.getSession().setAttribute("error_message", "Horário carregdo sem Ucs");
    		response.sendRedirect("processar_horario.jsp");
    		return;
		}
	%>
	<form action="processar_horario_ucs.jsp" method="post">
		<% for (int i = 0; i < ucs.size(); i++) { %>
			<input type="checkbox" name="uc" id="uc_<%=i%>" value="<%=ucs.get(i)%>">
			<label for="uc_<%=i%>"><%=ucs.get(i)%></label><br>
		<% } %>
		<input type="submit" value="Enviar">
	</form>
</body>
</html>
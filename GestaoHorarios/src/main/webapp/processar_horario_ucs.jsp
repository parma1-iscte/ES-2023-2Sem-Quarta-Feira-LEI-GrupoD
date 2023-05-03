<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="Horario.*, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestor de Horários</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<% 
		Horario horario = (Horario) request.getSession().getAttribute("horario");
		if(horario == null){
			request.getSession().setAttribute("error_message", "Falha na Sessão");
    		response.sendRedirect("index.jsp");
    		return;
		}
		
		String[] ucIndices = request.getParameterValues("uc");
	    if (ucIndices != null) {
	        List<String> ucsSelecionadas = Arrays.asList(ucIndices);
	        for(String s : ucsSelecionadas)
	        	out.println(s);
	        Horario horarioUcs = horario.getHorarioFromUcList(ucsSelecionadas);
	        request.getSession().removeAttribute("horario");
	        request.getSession().setAttribute("horario",horarioUcs);
	        response.sendRedirect("processar_horario.jsp");
	        return;
	    } else {
	    	request.getSession().setAttribute("error_message", "Nenhuma UC foi escolhida");
    		response.sendRedirect("selecionar_ucs.jsp");
	    }
	%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Horario.*, java.util.*, com.google.gson.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Gestor de Horários</title>
<link rel="stylesheet" href="style.css">
<meta charset='utf-8' />
<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.9.0/fullcalendar.min.css' />
<script src='https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.9.0/fullcalendar.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.9.0/locale/pt-br.js'></script>

</head>
<body>
    <%
        List<Map<String, Object>> eventos = new ArrayList<>();
        Horario horario = (Horario) request.getSession().getAttribute("horario");
        if (horario == null) {
            out.println("<script>alert('Erro');</script>");
        } else if (horario.getHorario() == null) {
            out.println("<script>alert('Horário vazio');</script>");
        } else {
            List<Aula> aulas = horario.getHorario();
            for(Aula aula : aulas){
                String uc = aula.getUc();
                String inicio = aula.getDataHoraInicioFormatada();
                String fim = aula.getDataHoraFimFormatada();
                String sala = aula.getSala();
                String cor;
                if(aula.isSobrelotada())
                	cor = "#dc3545";
                else
                	cor= "#007bff";
                
                Map<String, Object> evento = new HashMap<>();
                
                evento.put("title", uc);
                evento.put("start", inicio);
                evento.put("end", fim);
                evento.put("location",sala);
                evento.put("color",cor);

                eventos.add(evento);
            }
        }
    %>
    <script type="text/javascript">
    $(document).ready(function() {
        var eventos = <%= new Gson().toJson(eventos) %>;
        $('#calendario').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'agendaDay,agendaWeek,month'
            },
            defaultView: 'agendaWeek',
            slotDuration: '00:30:00', // duração das slots (30 minutos)
            minTime: '08:00:00', // hora de início (8:00)
            maxTime: '18:00:00', // hora de fim (18:00)
            weekends: true, // oculta sábados e domingos
            events: eventos,
            locale: 'pt-br',
            contentHeight: 'auto',
            eventRender: function(event, element) {
                element.find('.fc-title').append("<br/>Sala: " + event.location);
            },
            
        });
    });


    </script>
    <div id='calendario'></div>
    <br/>
   <div id='legend'>
  		<div style="display: inline-block; width: 20px; height: 20px; background-color: red; margin-right: 5px;"></div>
  		<span class="legend-label" style="display: inline-block">Aulas sobrelotadas</span> 
  		<br/>
  		<div style="display: inline-block; width: 20px; height: 20px; background-color: blue; margin-right: 5px;"></div>
  		<span class="legend-label" style="display: inline-block">Aulas não sobrelotadas</span>
	</div>
   
    
    
</body>
</html>

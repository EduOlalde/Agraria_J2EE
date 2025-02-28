package com.ecofield.controladorAgricultor;

import com.ecofield.dao.TrabajoDAO;
import com.ecofield.dao.TrabajoSolicitadoDAO;
import com.ecofield.modelos.Trabajo;
import com.ecofield.modelos.TrabajoSolicitado;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ListarTrabajosAgricultor")
public class ListarTrabajosAgricultorServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("conexion");
        if (conn == null) {
            session.setAttribute("mensaje", "Error de conexión con la base de datos.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        
        // Obtener el id del agricultor desde la sesión (por ejemplo, user_id)
        Integer idUsuario = (Integer) session.getAttribute("user_id");
        if (idUsuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // Recuperar filtros desde la petición (si existen)
        Integer filtroTipoTrabajo = null;
        String filtroTipoTrabajoStr = request.getParameter("tipo_trabajo_agri_lista");
        if (filtroTipoTrabajoStr != null && !filtroTipoTrabajoStr.trim().isEmpty()) {
            filtroTipoTrabajo = Integer.valueOf(filtroTipoTrabajoStr);
        }
        String filtroFechaInicio = request.getParameter("fecha_inicio");
        String filtroFechaFin = request.getParameter("fecha_fin");
        
        // Obtener trabajos solicitados (en revisión y rechazados) para el agricultor
        TrabajoSolicitadoDAO tsDAO = new TrabajoSolicitadoDAO((com.mysql.jdbc.Connection) conn);
        List<TrabajoSolicitado> enRevision = tsDAO.obtenerTrabajosSolicitadosPorPropietarioYEstado(idUsuario, "En revision");
        List<TrabajoSolicitado> rechazados = tsDAO.obtenerTrabajosSolicitadosPorPropietarioYEstado(idUsuario, "Rechazado");
        
        // Obtener trabajos aprobados para el agricultor
        TrabajoDAO tDAO = new TrabajoDAO(conn);
        List<Trabajo> trabajosAprobados = tDAO.obtenerTrabajosAprobadosPorPropietario(idUsuario, filtroTipoTrabajo, filtroFechaInicio, filtroFechaFin);
        
        // Asignar atributos a la request
        request.setAttribute("enRevision", enRevision);
        request.setAttribute("rechazados", rechazados);
        request.setAttribute("trabajosAprobados", trabajosAprobados);
        
        // Enviar la petición al DashboardServlet para que integre esta sección en el dashboard
        request.getRequestDispatcher("dashboard").forward(request, response);
    }
}

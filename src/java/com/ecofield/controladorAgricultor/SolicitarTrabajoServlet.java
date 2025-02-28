package com.ecofield.controladorAgricultor;

import com.ecofield.dao.TrabajoSolicitadoDAO;
import com.ecofield.modelos.TrabajoSolicitado;
import java.io.IOException;
import java.util.Date;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SolicitarTrabajo")
public class SolicitarTrabajoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperar los parámetros enviados en el formulario
        int parcelaId = Integer.parseInt(request.getParameter("parcela_id"));
        int tipoTrabajo = Integer.parseInt(request.getParameter("tipo_trabajo_agri_solicita"));
        
        // Obtener la sesión
        HttpSession session = request.getSession();
        Integer idUsuario = (Integer) session.getAttribute("user_id");
        if (idUsuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // Obtener la conexión desde la sesión
        Connection conn = (Connection) session.getAttribute("conexion");
        if (conn == null) {
            session.setAttribute("mensaje", "Error de conexión con la base de datos.");
            response.sendRedirect(request.getContextPath() + "/DashboardServlet");
            return;
        }
        
        // Crear el objeto TrabajoSolicitado con la fecha actual y estado "En revision"
        TrabajoSolicitado solicitud = new TrabajoSolicitado(
            0, // idSolicitud: 0 o se asigna automáticamente en la BD
            parcelaId,
            idUsuario,
            tipoTrabajo,
            "En revision", // Estado inicial
            new Date()
        );
        
        // Crear el DAO y procesar la solicitud
        try {
            TrabajoSolicitadoDAO dao = new TrabajoSolicitadoDAO((com.mysql.jdbc.Connection) conn);
            boolean exito = dao.crearSolicitud(solicitud);
            
            if (exito) {
                session.setAttribute("mensaje", "Solicitud de trabajo enviada correctamente.");
            } else {
                session.setAttribute("mensaje", "Error al enviar la solicitud.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensaje", "Error interno al procesar la solicitud.");
        }
        
        // Redirigir al DashboardServlet para que se recargue el dashboard con los datos actualizados
        response.sendRedirect(request.getContextPath() + "/DashboardServlet");
    }
}

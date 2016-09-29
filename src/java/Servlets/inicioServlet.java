package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ivo
 */
@WebServlet(name = "inicioServlet", urlPatterns = {"/inicio"})
public class inicioServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            
           response.setContentType("text/html;charset=UTF-8");
            //coneccion a la base de datos.
            Connection conn = Utilidades.Conexion.getConnection();
            //crea el SQL y hace un prepares statement
            String sql = "SELECT * FROM clientes";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            //en una lista enlazada almacena todos los resultados encontrados
            List <HashMap<String, Object>> resultado = new LinkedList();
            
                while(rs.next()){
                    HashMap row = new HashMap();
                    row.put("id", rs.getInt("id"));
                    row.put("nombre", rs.getString("nombre"));
                    row.put("apellido", rs.getString("apellido"));
                    row.put("fecha_nac", rs.getDate("fechnac"));
                    row.put("activo", rs.getInt("activo"));
                    resultado.add(row);
                }
            
            //Inicializa en el atributo "resultado" los registros almacenados por la consulta SQL y lo pone a dispocision al jsp
            request.setAttribute("resultado", resultado);
            
            //finaliza la coneccion a la base de datos y el prepareStatment
            pstmt.close();
            conn.close();
            
            //Dispara el objeto request y response a la direccion asignada
            request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
        } 
        
         catch (SQLException ex) {
            Logger.getLogger(inicioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(inicioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
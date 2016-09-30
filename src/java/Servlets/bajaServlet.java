package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ivo
 */
@WebServlet(name = "bajaServlet", urlPatterns = {"/baja"})
public class bajaServlet extends HttpServlet {

    

   //Si viene por GET, le pasamos el cliente seleccionado desde el index y le
    //Pasamos al JSP la informacion.
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            //Validamos la sesion
            HttpSession sesion = request.getSession(false);
                if (sesion == null)
                {
                 System.err.println("No ah iniciado sesion");
                 request.getRequestDispatcher("index.jsp").forward(request, response);
                 response.sendRedirect("/Robo/inicio");
                 }
            
            //Validamos persmiso
                if(Integer.valueOf(sesion.getAttribute("permiso").toString()) != 2){
                 System.err.println("Usted no tiene permiso para esta operacion");
                 request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
                 response.sendRedirect("/Robo/inicio");         
                }
            
            //Se recibe la id del cliente seleccionado desde el index
            Integer id = Integer.valueOf(request.getParameter("id"));
            
            //coneccion a la base de datos.
            Connection conn = Utilidades.Conexion.getConnection();
            
            //Se genera la consulta SQL para buscar a una persona con una ID recibida y se genera el prepare statment
            //y se indica los parametros (solo uno que es el id)
            String sql;
            sql = "SELECT * FROM clientes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            //Se almacena los resultados en una variable
            ResultSet rs = pstmt.executeQuery();        
            
            //Si se encontro al cliente solicitado se almacena en un nuevo objeto tipo Cliente.
            if (rs.next()) {
                Cliente cliente = new Cliente();
                
                cliente.setNombre(rs.getString("nombre"));
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setFechnac(rs.getDate("fechnac"));
                cliente.setNacionalidad(new Nacionalidad(rs.getInt("nacionalidad_id"), conn));
               
                //Se pione a dispocicion el ID de la persona que se pondra como un campo oculto
                request.setAttribute("cliente_id", rs.getInt("id"));
                
                //Se pone el objeto cliente a dispocicion del jsp
                request.setAttribute("cliente", cliente);
            }
                   
            //Se cierran las conecciones
            pstmt.close();
            conn.close();
            
            //redireccion a la vista del jsp
            request.getRequestDispatcher("WEB-INF/jsp/baja.jsp").forward(request, response);
            
        } catch (NamingException ex) { 
            Logger.getLogger(modificarServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(modificarServlet.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    

    //si viene una peticion por post con un ID este se elimina
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try {
            response.setContentType("text/html;charset=UTF-8");
            
            //Validamos la sesion
            HttpSession sesion = request.getSession(false);
                if (sesion == null)
                {
                 System.err.println("No ah iniciado sesion");
                 request.getRequestDispatcher("index.jsp").forward(request, response);
                 response.sendRedirect("/Robo/inicio");
                 }
            
                //Validamos persmiso
                if(Integer.valueOf(sesion.getAttribute("permiso").toString()) != 1){
                 System.err.println("Usted no tiene permiso para esta operacion");
                 request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
                 response.sendRedirect("/Robo/inicio");         
                }
            
            //Se genera una coneccion a la BBDD
            Connection conn = Utilidades.Conexion.getConnection();
            
            //Se recibe el ID desde baja.jsp
            Integer id = Integer.valueOf(request.getParameter("id"));
            
            //Se genera la consulta SQL con el prepare statment y la asignacion
            //del parametro ID
            String sql = "DELETE FROM clientes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            //Se ejecuta el DELETE
            pstmt.executeUpdate();

            //Se cierran las conecicones
            pstmt.close();  
            conn.close();
            
            //Se redirecciona al index
            response.sendRedirect("/Robo/inicio");
            
        } catch (NamingException ex) {
            Logger.getLogger(bajaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(bajaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet. Si pasa por aca, es que desde el index por el metodo GET no pasa"); //To change body of generated methods, choose Tools | Templates.
    }

}

package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(name = "modificarServlet", urlPatterns = {"/modificar"})
public class modificarServlet extends HttpServlet {

    //Si vienen datos de una persona junto a un id valido, los datos de ese id se veran modificados
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    processRequest(request, response);
            try {
            response.setContentType("text/html;charset=UTF-8");
            
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
                
                //Se pone el objeto cliente a dispocicion del jsp
                request.setAttribute("cliente", cliente);
            }
            
            
            //Creo el objeto Nacionalidades con la lista de lacionalidades y lo pongo a dispocicion del jsp
            List<Nacionalidad> nacionalidades = Nacionalidad.all(conn);
            request.setAttribute("nacionalidades", nacionalidades);
            
            //Se cierran las conecciones
            pstmt.close();
            conn.close();
            
            //redireccion a la vista del jsp
            request.getRequestDispatcher("WEB-INF/jsp/modificar.jsp").forward(request, response);
            
        } catch (NamingException ex) { 
            Logger.getLogger(modificarServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(modificarServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet porque lo eliminie."); //To change body of generated methods, choose Tools | Templates.
    }

}

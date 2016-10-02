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
            Connection conn = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            //Validamos la sesion
            HttpSession sesion = request.getSession();
            
            try{
                if (sesion == null)
                {
                 String error = "Usted, no ah iniciado una sesion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("index.jsp").forward(request, response);
                 }
            }catch(IOException e){
                
            }
            
            //Validamos persmiso
            
            try{
                if(Integer.valueOf(sesion.getAttribute("permiso").toString()) != 2){
                 String error = "Usted No tiene permiso para esta Operacion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);        
                }
            }catch(IOException e){
            
            }
            
            //Se recibe la id del cliente seleccionado desde el index
            Integer id = Integer.valueOf(request.getParameter("id"));
            
            //coneccion a la base de datos.
            conn = Utilidades.Conexion.getConnection();
            
            //Se genera la consulta SQL para buscar a una persona con una ID recibida y se genera el prepare statment
            //y se indica los parametros (solo uno que es el id)
            String sql;
            sql = "SELECT * FROM clientes WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            //Se almacena los resultados en una variable
            ResultSet rs = pstmt.executeQuery();        
            
            try{
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
            }else{
                 String error = "ID seleccionada invalida o no encontrada";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);  
            
            }}catch(IOException e){}
                   
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
        finally{
                try {
                    if(conn != null && !(conn.isClosed())){
                        conn.close();
                    }  } catch (SQLException ex) {
                    Logger.getLogger(logServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
         
         }
    }
    

    //si viene una peticion por post con un ID este se elimina
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Connection conn = null;
            try {
            response.setContentType("text/html;charset=UTF-8");
            
            //Validamos la sesion
            HttpSession sesion = request.getSession();
            
            try{
                if (sesion == null)
                {
                 String error = "Usted, no ah iniciado una sesion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("index.jsp").forward(request, response);
                 }
            }catch(IOException e){
            } 
               
            try{
                //Validamos persmiso
                if(Integer.valueOf(sesion.getAttribute("permiso").toString()) != 1){
                 String error = "Usted No tiene permiso para esta operacion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);           
                }
            }catch(IOException e){}
            //Se genera una coneccion a la BBDD
             conn = Utilidades.Conexion.getConnection();
            
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
            finally{
                try {
                    if(conn != null && !(conn.isClosed())){
                        conn.close();
                    }  } catch (SQLException ex) {
                    Logger.getLogger(logServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
         
         }
    }

    
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet. Si pasa por aca, es que desde el index por el metodo GET no pasa"); //To change body of generated methods, choose Tools | Templates.
    }

}

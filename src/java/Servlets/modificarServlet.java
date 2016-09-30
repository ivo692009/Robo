package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.servlet.http.HttpSession;

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
            }catch(IOException e){}
            
                try{
                //Validamos persmiso
                if(Integer.valueOf(sesion.getAttribute("permiso").toString()) != 3){
                 String error = "Usted No tiene permiso para esta operacion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);           
                }
                }catch(IOException e){}
                
            //Recivimos los parametros que vienen por POST desde el formulario de modificar.jsp
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String fecha_nacimiento = request.getParameter("fechnac");
            Integer nacionalidad = Integer.valueOf(request.getParameter("nacionalidad"));
            Integer id = Integer.valueOf(request.getParameter("id"));
            
            //Generamos un una variable para poder validar el campo RADIO desde el formulario
            Integer activo = null;         
            if ("1".equals(request.getParameter("activo"))) {
                activo = 1;
            }
            
            //Se genera una variable df que sea de tipo fecha y una nueva variable para transformar
            //lo que llego del formulario a una fecha con formato valido.
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fechnac = null;
            try {
                fechnac = df.parse(fecha_nacimiento);
            } catch (Exception e) {
                Logger.getLogger(altaServlet.class.getName()).log(Level.SEVERE, null, e);
            }
            
            
            //Se genera una coneccion a la base de datos y se genera el UPDATE con los datos
            //Recividos desde el formulario de modificar.jsp
            conn = Utilidades.Conexion.getConnection();  
            String sql = "UPDATE clientes SET nombre = ?, apellido = ?, fechnac = ?, activo = ?,nacionalidad_id = ? "
                        + "WHERE id = ?";  
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setDate(3, new java.sql.Date(fechnac.getTime()));
            pstmt.setInt(4, nacionalidad);
            pstmt.setInt(5, activo);
            pstmt.setInt(6, id);
            
            //Se ejecuta el SQL con el INSERT
            pstmt.execute();
            
            //Se cierran conecciones
            pstmt.close();
            conn.close();
            
            //Redireccionamos al index
            response.sendRedirect("/Robo/inicio");
            
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
    
    
    @Override
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
                }catch(IOException e){}
            
                //Validamos persmiso
                try{
                if(Integer.valueOf(sesion.getAttribute("permiso").toString()) != 3){
                 String error = "Usted No tiene permiso para esta operacion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);           
                }
                }catch(IOException e){}
            
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
            
            
            //Una linkedList para las nacionalidades enconcontradas
            List <HashMap<String, Object>> nacionalidad = new LinkedList();        
                while(rs.next()){
                    HashMap row = new HashMap();
                    row.put("id", rs.getInt("id"));
                    row.put("nacionalidad", rs.getString("nacionalidad"));
                    nacionalidad.add(row);
                }
            //Se pone a dispocicion las nacionalidades encontradas.
            request.setAttribute("nacionalidad", nacionalidad);
             
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
            finally{
                try {
                    if(conn != null && !(conn.isClosed())){
                        conn.close();
                    }  } catch (SQLException ex) {
                    Logger.getLogger(logServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
         
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

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
@WebServlet(name = "altaServlet", urlPatterns = {"/alta"})
public class altaServlet extends HttpServlet {
    
    //Viene desde el index y se le otorga la lista de nacionalidades y se lo envia a alta.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             Connection conn = null;
         try {
            response.setContentType("text/html;charset=UTF-8");
            
            //Validamos la sesion
            HttpSession sesion = request.getSession(false);

                if (sesion == null)
                {
                 String error = "Usted, no ah iniciado una sesion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("index.jsp").forward(request, response);
                 }
            
                //Validamos persmiso
                if(Integer.valueOf(sesion.getAttribute("permiso").toString()) != 1){
                 String error = "Usted, no ah iniciado una sesion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);       
                }
            
            //Inicio coneccinon a la BBDD
            conn = Utilidades.Conexion.getConnection();
            
            //consulta para pedir las nacionalidades, con el prepares statment y almacenado.
            String sql = "SELECT * FROM nacionalidades";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            //Una linkedList para las nacionalidades enconcontradas
            List <HashMap<String, Object>> nacionalidad = new LinkedList();        
                while(rs.next()){
                    HashMap row = new HashMap();
                    row.put("id", rs.getInt("id"));
                    row.put("descripcion", rs.getString("descripcion"));
                    nacionalidad.add(row);
                }
            //Se pone a dispocicion las nacionalidades encontradas.
            request.setAttribute("nacionalidad", nacionalidad);
            
            //Enviamos a alta jsp.
            request.getRequestDispatcher("WEB-INF/jsp/alta.jsp").forward(request, response);
            
        } catch (NamingException ex) {
            Logger.getLogger(altaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(altaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    

    //Si viene por POST a este servlet, se da de alta a un nuevo cliente.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Connection conn = null;
        try { 
            response.setContentType("text/html;charset=UTF-8");
            
            //Validamos la sesion
            HttpSession sesion = request.getSession(false);
                if (sesion == null)
                {
                 String error = "Usted, no ah iniciado una sesion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("index.jsp").forward(request, response);
                 }
            
                //Validamos persmiso
                if(Integer.valueOf(sesion.getAttribute("permiso").toString()) != 1){
                 String error = "Usted, no ah iniciado una sesion";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);           
                }
            
            //Recivimos los parametros que vienen por POST desde el formulario de alta.jsp
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String fecha_nacimiento = request.getParameter("fechnac");
            Integer nacionalidad = Integer.valueOf(request.getParameter("nacionalidad"));
            
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
            
            
            //Se genera una coneccion a la base de datos y se genera el INSERT con los datos
            //Recividos desde el formulario de alta.jsp
            conn = Utilidades.Conexion.getConnection();  
            String sql = "INSERT INTO clientes (nombre,apellido,fechnac,nacionalidad_id,activo) "
                       + "VALUES(?, ?, ?, ?, ?)";  
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setDate(3, new java.sql.Date(fechnac.getTime()));
            pstmt.setInt(4, nacionalidad);
            pstmt.setInt(5, activo);
            
            //Se ejecuta el SQL con el INSERT
            pstmt.execute();
            
            //Se cierran conecciones
            pstmt.close();
            conn.close();
            
            //Redireccionamos al index
            response.sendRedirect("/Robo/inicio");
            
        } catch (NamingException ex) {
            Logger.getLogger(altaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(altaServlet.class.getName()).log(Level.SEVERE, null, ex);
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

}

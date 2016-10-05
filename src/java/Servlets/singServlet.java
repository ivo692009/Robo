package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
@WebServlet(name = "singServlet", urlPatterns = {"/sing"})
public class singServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.sendRedirect("/Robo/inicio");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try { 
            response.setContentType("text/html;charset=UTF-8");
            
            //Recivimos los parametros que vienen por POST desde el formulario de sing.jsp
            String usu = request.getParameter("username");
            String con1 = request.getParameter("password1");
            String con2 = request.getParameter("password2");
            Integer permiso = Integer.valueOf(request.getParameter("permiso"));

            try{
            if(!con1.equals(con2) || (permiso <= 0 || permiso >=4)){
                 String error = "Las contraseñas no coinciden o el rango del permiso invalido";
                 request.setAttribute("error", error);
                 request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            else{
            //Se genera una coneccion a la base de datos y se genera el INSERT con los datos
            //Recividos desde el formulario de alta.jsp
            conn = Utilidades.Conexion.getConnection();  
            String sql = "INSERT INTO ussers (usu,con,permiso) "
                       + "VALUES(?, ?, ?)";  
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, usu);
            pstmt.setString(2, con1);
            pstmt.setInt(3, permiso);
            
            //Se ejecuta el SQL con el INSERT
            pstmt.execute();
            
            //Se cierran conecciones
            pstmt.close();
            conn.close();
            
            //Enviamos un mensaje de que la cuenta fue creada al index.jsp
            String error = "La Cuenta ah sido creada";
            request.setAttribute("error", error);
               
                 
            //Redireccionamos al index
            request.getRequestDispatcher("index.jsp").forward(request, response);   
            }}
            catch(IOException e){}
            
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

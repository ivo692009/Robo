package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "logServlet", urlPatterns = {"/log"})
public class logServlet extends HttpServlet {
 

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            Connection conn = null;
         try{
                //Iniciamos sesion en una variable sesion. Recivimos los parametros.
                HttpSession sesion = request.getSession();
                String usu, con;
                usu = request.getParameter("user");
                con = request.getParameter("password");
                
                
                //Buscamos usuarios
                conn = Utilidades.Conexion.getConnection();
                String sql;
                sql = "SELECT * FROM ussers WHERE usu = ? AND con = ? ";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, usu);
                pstmt.setString(2, con);

                //Se almacena los resultados en una variable
                ResultSet rs = pstmt.executeQuery(); 
                
                //Creamos una variable tipo LOG y almacenamos datos
                Log log = new Log();
                
                if (rs.next()) {
                log.setUsu(rs.getString("usu"));
                log.setPass(rs.getString("con"));
                log.setPermiso(rs.getInt("permiso"));
                
                 }

                    if(log.getUsu().equals(usu) && log.getPass().equals(con)){
                        
                        //iniciamos la variable de sesion con el nombre del log valido.
                        sesion.setAttribute("usuario", log.getUsu());
                        sesion.setAttribute("permiso", log.getPermiso());
                        
                        //Se pone el objeto log a dispocicion del jsp
                        request.setAttribute("log", log);
                        //redirijo a página con información de login exitoso
                        response.sendRedirect("/Robo/inicio");
                    }else{
                        
                        //Usuario o contraseña no valida
                        String error = "Usuario o contraseña invalido";
                        request.setAttribute("error", error);
                        
                        //redireccion a la vista del jsp
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        
                        }
                 } catch (SQLException ex) {
            Logger.getLogger(logServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(logServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

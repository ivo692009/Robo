package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            //Creo el objeto Nacionalidades con la lista de lacionalidades y lo pongo a dispocicion del jsp
            List<Nacionalidad> nacionalidades = Nacionalidad.all(conn);
            request.setAttribute("nacionalidades", nacionalidades);
            
           //redireccion a la vista del jsp
           request.getRequestDispatcher("WEB-INF/jsp/modificar.jsp").forward(request, response);
        } finally {
            out.close();
        }
    }
    
    //Si vienen datos de una persona junto a un id valido, los datos de ese id se veran modificados
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

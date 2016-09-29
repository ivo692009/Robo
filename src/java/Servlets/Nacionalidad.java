package Servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class Nacionalidad {
    
    private Integer id;
    private String nacionalidad;

    public static List<Nacionalidad> all(Connection conn) {
        
        //Genera una lista enlazada de objetos que sera de tipo Nacionalidad
        List<Nacionalidad> ns = new LinkedList<>();
        
        String sql = "SELECT * FROM nacionalidades";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql); 
            //Almacena la respuesta encontrada
            ResultSet rs = ps.executeQuery();
            //Si encontro algun resultado.
            while (rs.next()) {                
                //Almacena cada uno de los resultados encontrados en objetos tipo Nacionalidad
                Nacionalidad n = new Nacionalidad(); 
                n.setId(rs.getInt("id"));
                n.setNacionalidad(rs.getString("nacionalidad"));  
                //Añade a la lista el objeto
                ns.add(n);
            }    
        } catch (Exception e) {
            
        }
        
        return ns;
    }
    
    //Devuelve un registro por un id determinado
    public Nacionalidad(Integer id, Connection conn) {
        
        String sql = "SELECT * FROM nacionalidades WHERE id = ?";
        
        try {
            //genera el prepare statmet y le hace indicacion al parametro id
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            //almacena en una variable el resultado si existe
            ResultSet rs = ps.executeQuery();
            //Si encontro algo...
            if (rs.next()) {                
                //Almacena en una variable tipo Nacionalidad la respuesta encontrada.
                Nacionalidad n = new Nacionalidad();
                
                this.id = rs.getInt("id");
                this.nacionalidad = rs.getString("nacionalidad");
            }
            
            
        } catch (Exception e) {
            
        }
        
    }
    
    public Nacionalidad() {
        this.nacionalidad = "Argentina";
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}

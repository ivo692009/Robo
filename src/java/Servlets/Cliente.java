package Servlets;

import java.util.Date;


//Se crea una clase cliente para poder almacenar en un objeto para devolver como respuuesta
//Con sus getters y setters
public class Cliente {
    
    private Integer id;
    private String nombre;
    private String apellido;
    private Date fechnac;
    private Nacionalidad nacionalidad;
    private Boolean activo;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechnac() {
        return fechnac;
    }

    public void setFechnac(Date fechnac) {
        this.fechnac = fechnac;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}

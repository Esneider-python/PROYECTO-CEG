package Modelo;

public class Sede {
    private int id;
    private String nombre;
    private Colegio colegio;
    private Usuario usuarioRegistra;

    public Sede(int id, String nombre, Colegio colegio, Usuario usuarioRegistra) {
        this.id = id;
        this.nombre = nombre;
        this.colegio = colegio;
        this.usuarioRegistra = usuarioRegistra;
    }

    public Sede(String nombre, Colegio colegio, Usuario usuarioRegistra) {
        this.nombre = nombre;
        this.colegio = colegio;
        this.usuarioRegistra = usuarioRegistra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Colegio getColegio() {
        return colegio;
    }

    public void setColegio(Colegio colegio) {
        this.colegio = colegio;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
}

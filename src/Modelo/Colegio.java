package Modelo;

public class Colegio {
    private int id;
    private String nombre;
    private Usuario usuarioRegistra;

    public Colegio(int id, String nombre, Usuario usuarioRegistra) {
        this.id = id;
        this.nombre = nombre;
        this.usuarioRegistra = usuarioRegistra;
    }

    public Colegio(String nombre, Usuario usuarioRegistra) {
        this.nombre = nombre;
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

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
}

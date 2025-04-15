package Modelo;

public class Bloque {
    private int id;
    private Sede sede;
    private Usuario usuarioRegistra;

    public Bloque(int id, Sede sede, Usuario usuarioRegistra) {
        this.id = id;
        this.sede = sede;
        this.usuarioRegistra = usuarioRegistra;
    }

    public Bloque(Sede sede, Usuario usuarioRegistra) {
        this.sede = sede;
        this.usuarioRegistra = usuarioRegistra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
}

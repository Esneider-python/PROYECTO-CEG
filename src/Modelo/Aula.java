package Modelo;

public class Aula {
    private int id;
    private Piso piso;
    private Usuario usuarioRegistra;

    public Aula(int id, Piso piso, Usuario usuarioRegistra) {
        this.id = id;
        this.piso = piso;
        this.usuarioRegistra = usuarioRegistra;
    }

    public Aula(Piso piso, Usuario usuarioRegistra) {
        this.piso = piso;
        this.usuarioRegistra = usuarioRegistra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Piso getPiso() {
        return piso;
    }

    public void setPiso(Piso piso) {
        this.piso = piso;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
}

package Modelo;

public class Piso {
    private int id;
    private int numeroPiso;
    private Bloque bloque;
    private Usuario usuarioRegistra;

    public Piso(int id, int numeroPiso, Bloque bloque, Usuario usuarioRegistra) {
        this.id = id;
        this.numeroPiso = numeroPiso;
        this.bloque = bloque;
        this.usuarioRegistra = usuarioRegistra;
    }

    public Piso(int numeroPiso, Bloque bloque, Usuario usuarioRegistra) {
        this.numeroPiso = numeroPiso;
        this.bloque = bloque;
        this.usuarioRegistra = usuarioRegistra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
}
